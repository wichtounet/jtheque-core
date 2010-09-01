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

import org.jtheque.core.Core;
import org.jtheque.core.utils.SwingSpringProxy;
import org.jtheque.states.StateService;
import org.jtheque.utils.SimplePropertiesCache;
import org.jtheque.images.ImageService;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.ViewService;
import org.jtheque.views.WindowConfiguration;
import org.jtheque.views.panel.CollectionView;
import org.jtheque.views.windows.AboutView;
import org.jtheque.views.windows.MainView;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.awt.GraphicsEnvironment;
import java.awt.Window;

/**
 * A view manager implementation.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public final class ViewServiceImpl implements ViewService, ApplicationContextAware {
    private final WindowsConfiguration configuration;

    private SwingSpringProxy<CollectionView> collectionPane;
    private SwingSpringProxy<AboutView> aboutPane;

    /**
     * Construct a new ViewServiceImpl.
     *
     * @param stateService The state service.
     * @param core         The core.
     * @param imageService The resource service.
     */
    public ViewServiceImpl(StateService stateService, Core core, ImageService imageService) {
        super();

        configuration = stateService.getState(new WindowsConfiguration(core, this));

        ViewsResources.registerResources(imageService);
    }

    @Override
    public void displayAboutView() {
        SwingUtils.inEdt(new Runnable(){
            @Override
            public void run() {
                SimplePropertiesCache.get("mainView", MainView.class).setGlassPane(aboutPane.get().getImpl());

                aboutPane.get().appear();
            }
        });
    }

    @Override
    public void displayCollectionView() {
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                SimplePropertiesCache.get("mainView", MainView.class).setGlassPane(collectionPane.get().getImpl());

                collectionPane.get().appear();
            }
        });
    }

    @Override
    public void closeCollectionView() {
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                SimplePropertiesCache.get("mainView", MainView.class).setGlassPane(null);
            }
        });
    }

    @Override
    public void saveState(Window window, String name) {
        configuration.update(name, window);
    }

    @Override
    public void configureView(Window view, String name, int defaultWidth, int defaultHeight) {
        configuration.configure(name, view, defaultWidth, defaultHeight);
    }

    @Override
    public void setSize(Window view, int defaultWidth, int defaultHeight) {
        view.setSize(defaultWidth, defaultHeight);
        SwingUtils.centerFrame(view);
    }

    @Override
    public void fill(WindowConfiguration configuration, Window view) {
        configuration.setWidth(view.getWidth());
        configuration.setHeight(view.getHeight());
        configuration.setPositionX(view.getLocation().x);
        configuration.setPositionY(view.getLocation().y);
    }

    @Override
    public void configure(WindowConfiguration configuration, Window view) {
        view.setSize(configuration.getWidth(), configuration.getHeight());

        if (configuration.getPositionX() == -1 || configuration.getPositionY() == -1) {
            SwingUtils.centerFrame(view);
        } else {
            if (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().contains(
                    configuration.getPositionX(), configuration.getPositionY())) {
                view.setLocation(configuration.getPositionX(), configuration.getPositionY());
            } else {
                SwingUtils.centerFrame(view);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        collectionPane = new SwingSpringProxy<CollectionView>(CollectionView.class, applicationContext);
        aboutPane = new SwingSpringProxy<AboutView>(AboutView.class, applicationContext);
    }
}