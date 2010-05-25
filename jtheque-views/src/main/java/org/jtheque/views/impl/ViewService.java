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
import org.jtheque.resources.able.IResourceService;
import org.jtheque.spring.utils.SwingSpringProxy;
import org.jtheque.states.able.IStateService;
import org.jtheque.ui.able.IView;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.IViewService;
import org.jtheque.views.able.IWindowConfiguration;
import org.jtheque.views.able.windows.IAboutView;
import org.jtheque.views.able.panel.ICollectionView;
import org.jtheque.views.able.windows.IMainView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.awt.Component;
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
     * @param core The core.
     * @param resourceService The resource service. 
     */
    public ViewService(IStateService stateService, ICore core, IResourceService resourceService) {
        super();

        configuration = stateService.getState(new WindowsConfiguration(core, this));

        ViewsResources.registerResources(resourceService);
    }

    @Override
    public void displayAboutView() {
        SimplePropertiesCache.<IMainView>get("mainView").setGlassPane(aboutPane.get().getImpl());

        aboutPane.get().appear();
    }

    @Override
    public void displayCollectionView() {
        SimplePropertiesCache.<IMainView>get("mainView").setGlassPane(collectionPane.get().getImpl());

        collectionPane.get().appear();
    }

    @Override
    public void saveState(IView window, String name) {
        if (configuration != null) {
            configuration.update(name, window);
        }
    }

    @Override
    public void configureView(IView view, String name, int defaultWidth, int defaultHeight) {
        configuration.configure(name, view, defaultWidth, defaultHeight);
    }

    @Override
    public void setSize(IView view, int defaultWidth, int defaultHeight) {
        ((Component) view).setSize(defaultWidth, defaultHeight);
        SwingUtils.centerFrame((Window) view);
    }

    @Override
    public void fill(IWindowConfiguration configuration, IView view) {
        Component window = (Component) view;

        configuration.setWidth(window.getWidth());
        configuration.setHeight(window.getHeight());
        configuration.setPositionX(window.getLocation().x);
        configuration.setPositionY(window.getLocation().y);
    }

    @Override
    public void configure(IWindowConfiguration configuration, IView view) {
        Window window = (Window) view;

        window.setSize(configuration.getWidth(), configuration.getHeight());

        if (configuration.getPositionX() == -1 || configuration.getPositionY() == -1) {
            SwingUtils.centerFrame(window);
        } else {
            if (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().contains(
                    configuration.getPositionX(), configuration.getPositionY())) {
                window.setLocation(configuration.getPositionX(), configuration.getPositionY());
            } else {
                SwingUtils.centerFrame(window);
            }
        }
    }

    @Override
    public ICollectionView getCollectionView() {
        return collectionPane.get();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        collectionPane = new SwingSpringProxy<ICollectionView>(ICollectionView.class, applicationContext);
        aboutPane = new SwingSpringProxy<IAboutView>(IAboutView.class, applicationContext);
    }
}