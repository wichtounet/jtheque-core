package org.jtheque.events;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

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

public class EventsActivator implements BundleActivator {
    private EventManager manager;

    private ServiceRegistration registration;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        manager = new EventManager();

        manager.importFromXML();

        registration = bundleContext.registerService(IEventManager.class.getName(), manager, null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        manager.saveXML();

        registration.unregister();
    }
}