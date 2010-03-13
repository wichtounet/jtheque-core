package org.jtheque.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.jtheque.core.utils.SystemProperty;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
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

public class LoggingActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        Logger rootLogger = (Logger) LoggerFactory.getLogger("root");

        String level = "ERROR";

        if (SystemProperty.JTHEQUE_LOG.get() != null) {
            level = SystemProperty.JTHEQUE_LOG.get();
        }

        rootLogger.setLevel(Level.toLevel(level));
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        //Nothing to do
    }
}