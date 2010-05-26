package org.jtheque.views.impl;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.apple.eawt.Application;
import com.apple.eawt.ApplicationAdapter;
import com.apple.eawt.ApplicationEvent;
import org.jtheque.core.able.ICore;
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
     *
     * @param viewService The view service.
     * @param core The core.
     * @param views The views.
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

        /**
         * Construct a new MacApplicationAdapter.
         *
         * @param viewService The view service.
         * @param core The core.
         * @param views The views. 
         */
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