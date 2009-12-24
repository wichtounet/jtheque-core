package org.jtheque.core.managers.log;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.utils.SystemProperty;

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
 * @author Baptiste Wicht
 */
public final class Log4JConfigurator {
    private static final RollingFileAppender FILE_APPENDER = new RollingFileAppender();

    private static final String MAX_FILE_SIZE = "500KB";

    /**
     * This is an utility class, not instanciable.
     */
    private Log4JConfigurator() {
        super();
    }

    /**
     * Configure Log4J.
     */
    public static void configure() {
        System.setProperty("log4j.defaultInitOverride", "true");

        PatternLayout layout = new PatternLayout();
        layout.setConversionPattern("%d{ABSOLUTE} %5p %c{1}:%L - %m%n");

        addConsoleAppender(layout);

        addFileAppender(FILE_APPENDER, Managers.getCore().getFiles().getLogsFile().getAbsolutePath(), layout);

        String level = "ERROR";

        if (Managers.getCore().getApplication().getProperty("jtheque.log") != null) {
            level = Managers.getCore().getApplication().getProperty("jtheque.log");
        } else if (SystemProperty.JTHEQUE_LOG.get() != null) {
            level = SystemProperty.JTHEQUE_LOG.get();
        }

        Logger.getRootLogger().setLevel(Level.toLevel(level));
    }

    /**
     * Add a console appender.
     *
     * @param layout The layout of the appender.
     */
    private static void addConsoleAppender(Layout layout) {
        ConsoleAppender console = new ConsoleAppender();
        console.setTarget("System.out");
        console.setLayout(layout);

        console.activateOptions();

        Logger.getRootLogger().addAppender(console);
    }

    /**
     * Add a file appender.
     *
     * @param file     The file appender.
     * @param filePath The file path.
     * @param layout   The layout of the appender.
     */
    private static void addFileAppender(RollingFileAppender file, String filePath, Layout layout) {
        file.setAppend(true);
        file.setBufferedIO(true);
        file.setFile(filePath);
        file.setLayout(layout);
        file.setMaxBackupIndex(10);
        file.setMaxFileSize(MAX_FILE_SIZE);

        file.activateOptions();

        Logger.getRootLogger().addAppender(file);
    }

    /**
     * Close Log4J.
     */
    public static void close() {
        removeAndCloseAppender(FILE_APPENDER);
    }

    /**
     * Remove and close an appender.
     *
     * @param appender The appender to close and remove.
     */
    private static void removeAndCloseAppender(Appender appender) {
        if (appender != null) {
            appender.close();

            Logger.getRootLogger().removeAppender(appender);
        }
    }
}