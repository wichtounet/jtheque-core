package org.jtheque.core.managers.schema;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * A reader for HSQL Script file and log files.
 *
 * @author Baptiste Wicht
 */
final class HSQLFileReader {
    /**
     * Utility class, cannot be instantiated.
     */
    private HSQLFileReader() {
        super();
    }

    /**
     * Read the file and get off all the inserts.
     *
     * @param file The file to parse.
     * @return The List of all the inserts of the file.
     */
    public static Collection<Insert> readFile(File file) {
        Collection<Insert> inserts = new ArrayList<Insert>(100);

        FileChannel in = null;
        try {
            in = new FileInputStream(file).getChannel();

            Scanner scanner = new Scanner(in);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.startsWith("INSERT INTO")) {
                    inserts.add(readInsert(line));
                }
            }
        } catch (FileNotFoundException e) {
            Managers.getManager(ILoggingManager.class).getLogger(HSQLFileReader.class).error(e);
        } finally {
            FileUtils.close(in);
        }

        return inserts;
    }

    /**
     * Read the insert.
     *
     * @param line The line to read the insert from.
     * @return The read Insert.
     */
    private static Insert readInsert(String line) {
        Insert insert = new Insert();

        insert.setTable(line.substring(12, line.indexOf(" VALUES(")));

        insert.setValues(extractValues(line));

        return insert;
    }

    /**
     * Extract The values from the line.
     *
     * @param line The line to read.
     * @return A collection containing all the values.
     */
    private static Collection<String> extractValues(String line) {
        String valuesStr = line.substring(line.indexOf('(') + 1, line.lastIndexOf(')') + 1);

        valuesStr = valuesStr.replace(",'',", ",NULL,");

        return parseStrValues(valuesStr);
    }

    /**
     * Parse the values string to a Collection of values.
     *
     * @param valuesStr The String values.
     * @return A collection containing all the values.
     */
    private static Collection<String> parseStrValues(String valuesStr) {
        Collection<String> values = new ArrayList<String>(10);

        StringBuilder current = new StringBuilder(100);

        boolean surroundStart = false;
        char last = ' ';

        for (char c : valuesStr.toCharArray()) {
            if (isEndOfValue(surroundStart, c)) {
                values.add(StringUtils.removeUnicode(current.toString()));

                current.setLength(0);
            } else if (isEscapedChar(last, c)) {
                current.append('\'');
                surroundStart ^= true;
            } else if (c == '\'') {
                surroundStart ^= true;
            } else {
                current.append(c);
            }

            last = c;
        }

        return values;
    }

    /**
     * Indicate if the two chars form an escaped char or not.
     *
     * @param last The last char.
     * @param c    The current char.
     * @return true if the two chars form an escaped char else false.
     */
    private static boolean isEscapedChar(char last, char c) {
        return last == '\'' && c == '\'';
    }

    /**
     * Indicate if the char c indicate the end of the current value or not.
     *
     * @param surroundStart Indicate the surround started or not.
     * @param c             The current char.
     * @return true if this is the end of the value else false.
     */
    private static boolean isEndOfValue(boolean surroundStart, char c) {
        return !surroundStart && (c == ',' || c == ')');
    }
}