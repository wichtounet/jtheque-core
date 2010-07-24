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

import org.jtheque.core.able.ICore;
import org.jtheque.core.utils.SimplePropertiesCache;
import org.jtheque.images.able.IImageService;
import org.jtheque.spring.utils.SwingSpringProxy;
import org.jtheque.states.able.IStateService;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.IViewService;
import org.jtheque.views.able.IWindowConfiguration;
import org.jtheque.views.able.panel.ICollectionView;
import org.jtheque.views.able.windows.IAboutView;
import org.jtheque.views.able.windows.IMainView;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.awt.GraphicsEnvironment;
import java.awt.Window;

/**
 * A view manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class ViewService implements IViewService, ApplicationContextAware {
    private final WindowsConfiguration configuration;

    private SwingSpringProxy<ICollectionView> collectionPane;
    private SwingSpringProxy<IAboutView> aboutPane;

    /**
     * Construct a new ViewService.
     *
     * @param stateService The state service.
     * @param core         The core.
     * @param imageService The resource service.
     */
    public ViewService(IStateService stateService, ICore core, IImageService imageService) {
        super();

        configuration = stateService.getState(new WindowsConfiguration(core, this));

        ViewsResources.registerResources(imageService);
    }

    @Override
    public void displayAboutView() {
        SimplePropertiesCache.get("mainView", IMainView.class).setGlassPane(aboutPane.get().getImpl());

        aboutPane.get().appear();
    }

    @Override
    public void displayCollectionView() {
        SimplePropertiesCache.get("mainView", IMainView.class).setGlassPane(collectionPane.get().getImpl());

        collectionPane.get().appear();
    }

    @Override
    public void closeCollectionView() {
        SimplePropertiesCache.get("mainView", IMainView.class).setGlassPane(null);
    }

    @Override
    public void saveState(Window window, String name) {
        if (configuration != null) {
            configuration.update(name, window);
        }
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
    public void fill(IWindowConfiguration configuration, Window view) {
        configuration.setWidth(view.getWidth());
        configuration.setHeight(view.getHeight());
        configuration.setPositionX(view.getLocation().x);
        configuration.setPositionY(view.getLocation().y);
    }

    @Override
    public void configure(IWindowConfiguration configuration, Window view) {
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
        collectionPane = new SwingSpringProxy<ICollectionView>(ICollectionView.class, applicationContext);
        aboutPane = new SwingSpringProxy<IAboutView>(IAboutView.class, applicationContext);
    }
}