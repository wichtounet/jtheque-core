package org.jtheque.core.managers.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.utils.SystemProperty;
import org.slf4j.LoggerFactory;

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
 * A configurator for the logging implementation.
 *
 * @author Baptiste Wicht
 */
public final class LoggerConfigurator {
    /**
     * This is an utility class, not instanciable.
     */
    private LoggerConfigurator() {
        super();
    }

    /**
     * Configure LogBack.
     */
    public static void configure() {
        Logger rootLogger = (Logger)LoggerFactory.getLogger("root");

        String level = "ERROR";

        if (Managers.getCore().getApplication().getProperty("jtheque.log") != null) {
            level = Managers.getCore().getApplication().getProperty("jtheque.log");
        } else if (SystemProperty.JTHEQUE_LOG.get() != null) {
            level = SystemProperty.JTHEQUE_LOG.get();
        }

        rootLogger.setLevel(Level.toLevel(level));
    }
}