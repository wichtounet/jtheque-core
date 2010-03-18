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

import org.jtheque.views.ViewsServices;
import org.jtheque.views.able.IViewManager;
import org.jtheque.views.able.Views;
import org.jtheque.views.able.components.MainComponent;
import org.jtheque.views.able.windows.*;
import org.jtheque.views.able.panel.IModuleView;
import org.jtheque.views.able.panel.IRepositoryView;

import javax.annotation.Resource;
import javax.swing.JComponent;

/**
 * A window manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class WindowManager implements Views {
    @Resource
    private ILicenceView licenceView;

    @Resource
    private IConfigView configView;

    @Resource
    private IModuleView moduleView;

    @Resource
    private IMessageView messageView;

    @Resource
    private ILogView logView;

    @Resource
    private IRepositoryView repositoryView;

    @Resource
    private IUpdateView updateView;

    private final IMainView mainView;

    public WindowManager(IMainView mainView) {
        super();

        this.mainView = mainView;
    }

    @Override
    public void setSelectedView(MainComponent component) {
        mainView.setSelectedComponent(component.getComponent());
    }

    @Override
    public MainComponent getSelectedView() {
        MainComponent selected = null;

        JComponent component = mainView.getSelectedComponent();

        for (MainComponent tab : ViewsServices.get(IViewManager.class).getMainComponents()) {
            if (tab.getComponent().equals(component)) {
                selected = tab;
                break;
            }
        }

        return selected;
    }

    @Override
    public IMainView getMainView() {
        return mainView;
    }

    @Override
    public ILicenceView getLicenceView() {
        return licenceView;
    }

    @Override
    public IConfigView getConfigView() {
        return configView;
    }

    @Override
    public IModuleView getModuleView() {
        return moduleView;
    }

    @Override
    public IMessageView getMessagesView() {
        return messageView;
    }

    @Override
    public ILogView getLogView() {
        return logView;
    }

    @Override
    public IRepositoryView getRepositoryView() {
        return repositoryView;
    }

    @Override
    public IUpdateView getUpdateView() {
        return updateView;
    }
}