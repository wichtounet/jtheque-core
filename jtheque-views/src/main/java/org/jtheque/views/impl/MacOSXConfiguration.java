package org.jtheque.views.impl;

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
import org.jtheque.core.ICore;
import org.jtheque.views.able.IViewService;
import org.jtheque.views.able.IViews;

/**
 * Configuration to improve the compatibility with mac.
 *
 * @author Baptiste Wicht
 */
public final class MacOSXConfiguration {
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
    public static void configureForMac(IViewService viewService, ICore core, IViews views) {
        APPLICATION.addApplicationListener(new MacApplicationAdapter(viewService, core, views));
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
        private final IViewService viewService;
        private final IViews views;
        private final ICore core;

        private MacApplicationAdapter(IViewService viewService, ICore core, IViews views) {
            super();

            this.viewService = viewService;
            this.core = core;
            this.views = views;
        }

        @Override
        public void handleQuit(ApplicationEvent ev) {
            core.getLifeCycle().exit();
        }

        @Override
        public void handleAbout(ApplicationEvent ev) {
            viewService.displayAboutView();

            ev.setHandled(true);
        }

        @Override
        public void handlePreferences(ApplicationEvent ev) {
            views.getConfigView().display();

            ev.setHandled(true);
        }
    }
}