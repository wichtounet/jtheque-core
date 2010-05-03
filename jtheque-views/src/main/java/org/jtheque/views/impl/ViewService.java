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

import org.jtheque.core.ICore;
import org.jtheque.core.utils.SimplePropertiesCache;
import org.jtheque.resources.IResourceService;
import org.jtheque.spring.utils.SwingSpringProxy;
import org.jtheque.states.IStateService;
import org.jtheque.ui.able.IView;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.IViewService;
import org.jtheque.views.able.windows.IAboutView;
import org.jtheque.views.able.panel.ICollectionView;
import org.jtheque.views.able.windows.IMainView;
import org.springframework.beans.BeansException;
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
    public void fill(WindowConfiguration configuration, IView view) {
        Component window = (Component) view;

        configuration.setWidth(window.getWidth());
        configuration.setHeight(window.getHeight());
        configuration.setPositionX(window.getLocation().x);
        configuration.setPositionY(window.getLocation().y);
    }

    @Override
    public void configure(WindowConfiguration configuration, IView view) {
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
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        collectionPane = new SwingSpringProxy<ICollectionView>(ICollectionView.class, applicationContext);
        aboutPane = new SwingSpringProxy<IAboutView>(IAboutView.class, applicationContext);
    }
}