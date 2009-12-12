package org.jtheque.core.utils.file.jt.able;

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

import org.jtheque.utils.io.FileException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * A file reader for JTheque File.
 *
 * @author Baptiste Wicht
 */
public interface IJTFileReader {
    /**
     * Read a JT File from a File object.
     *
     * @param file The file to read from.
     * @return The JT File.
     * @throws FileException Throws when an error occurs during the reading.
     */
    JTFile readFile(File file) throws FileException;

    /**
     * Read a JT File from a file path.
     *
     * @param path The path to the file to read from.
     * @return The JT File.
     * @throws FileException Throws when an error occurs during the reading.
     */
    JTFile readFile(String path) throws FileException;

    /**
     * Read a JT File from a stream.
     *
     * @param stream The stream to read from.
     * @return The JT File.
     * @throws FileException Throws when an error occurs during the reading.
     */
    JTFile readFile(FileInputStream stream) throws FileException;

    /**
     * Read a JT File from a stream.
     *
     * @param stream The stream to read from.
     * @return The JT File.
     * @throws FileException Throws when an error occurs during the reading.
     */
    JTFile readFile(BufferedInputStream stream) throws FileException;
}