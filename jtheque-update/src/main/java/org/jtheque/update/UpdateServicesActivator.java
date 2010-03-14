package org.jtheque.update;

import org.jtheque.ui.ViewsUtilsServices;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

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

public class UpdateServicesActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        UpdateServices.setContext(bundleContext);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        //Nothing to do
    }
}