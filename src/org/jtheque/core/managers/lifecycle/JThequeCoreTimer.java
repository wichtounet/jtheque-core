package org.jtheque.core.managers.lifecycle;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.log.ILoggingManager;

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
 * A timer for JTheque Core.
 *
 * @author Baptiste Wicht
 */
public final class JThequeCoreTimer {
    private static long startTime;

    /**
     * This class is not instanciable, this is an utility.
     */
    private JThequeCoreTimer() {
        super();
    }

    /**
     * Start the timer.
     */
    public static void start() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Stop the timer.
     */
    public static void stop() {
        long time = System.currentTimeMillis() - startTime;

        Managers.getManager(ILoggingManager.class).getLogger(JThequeCoreTimer.class).debug("JTheque start took {} ms", time);
    }
}