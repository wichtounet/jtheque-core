package org.jtheque.core.managers.view;

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

import com.apple.eawt.Application;
import com.apple.eawt.ApplicationAdapter;
import com.apple.eawt.ApplicationEvent;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.able.IViewManager;

/**
 * Configuration to improve the compatibility with mac.
 *
 * @author Baptiste Wicht
 */
final class MacOSXConfiguration {
    private static final Application APPLICATION = new Application();

    /**
     * Construct a new MacOSXConfiguration. This class isn't instanciable.
     */
    private MacOSXConfiguration() {
        super();
    }

    /**
     * Configure the application for Mac menus.
     */
    public static void configureForMac() {
        APPLICATION.addApplicationListener(new MacApplicationAdapter());
        APPLICATION.setEnabledAboutMenu(true);
        APPLICATION.setEnabledPreferencesMenu(true);
    }

    /**
     * An application adapter for MAC OS X. This application handles the event
     * of the MAC menu to the jtheque application.
     *
     * @author Baptiste Wicht
     */
    private static final class MacApplicationAdapter extends ApplicationAdapter {
        @Override
        public void handleQuit(ApplicationEvent ev) {
            Managers.getCore().getLifeCycleManager().exit();
        }

        @Override
        public void handleAbout(ApplicationEvent ev) {
            Managers.getManager(IViewManager.class).displayAboutView();

            ev.setHandled(true);
        }

        @Override
        public void handlePreferences(ApplicationEvent ev) {
            Managers.getManager(IViewManager.class).getViews().getConfigView().display();

            ev.setHandled(true);
        }
    }
}