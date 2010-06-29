package org.jtheque.views.impl;

import org.jtheque.core.able.ICore;
import org.jtheque.core.utils.SimplePropertiesCache;
import org.jtheque.features.able.IFeatureService;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.ISplashService;
import org.jtheque.views.able.panel.ISplashView;
import org.jtheque.views.able.windows.IMainView;
import org.jtheque.views.impl.components.menu.CoreMenu;
import org.jtheque.views.impl.components.panel.SplashScreenPane;
import org.jtheque.views.impl.windows.MainView;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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

/**
 * A manager for the splash. The splash is not in Spring to make it appears before spring context loading.
 *
 * @author Baptiste Wicht
 */
public final class SplashService implements ISplashService, ApplicationContextAware {
    private ISplashView splashScreenPane;
    private boolean mainDisplayed;

    private MainView mainView;
    private final ICore core;
    private ApplicationContext applicationContext;

    /**
     * Construct a new SplashService.
     *
     * @param core The core.
     */
    public SplashService(ICore core) {
        super();

        this.core = core;
    }

    @Override
    public void initViews() {
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                mainView = (MainView) applicationContext.getBean(IMainView.class);

                SimplePropertiesCache.put("mainView", mainView);

                splashScreenPane = new SplashScreenPane(core);
            }
        });
    }

    @Override
    public void displaySplashScreen() {
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                displayMainViewIfNecessary();

                mainView.getContent().setUI(splashScreenPane.getImpl());

                mainView.refresh();

                splashScreenPane.appearsAndAnimate();
            }
        });
    }

    @Override
    public void closeSplashScreen() {
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                splashScreenPane.disappear();
                mainView.getContent().setUI(null);
            }
        });
    }

    /**
     * Display the main view if necessary.
     */
    private void displayMainViewIfNecessary() {
        if (!mainDisplayed) {
            mainDisplayed = true;
            mainView.display();
        }
    }

    @Override
    public void fillMainView() {
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                mainView.fill();

                applicationContext.getBean(IFeatureService.class).addMenu("", applicationContext.getBean(CoreMenu.class));

                mainView.refresh();
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}