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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.able.IConfigView;
import org.jtheque.core.managers.view.able.ILicenceView;
import org.jtheque.core.managers.view.able.IMainView;
import org.jtheque.core.managers.view.able.IMessageView;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.components.TabComponent;
import org.jtheque.core.managers.view.able.update.IModuleView;

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

    @Override
    public void setSelectedView(TabComponent component) {
        getMainView().setSelectedComponent(component.getComponent());
    }

    @Override
    public TabComponent getSelectedView() {
        TabComponent selected = null;

        JComponent component = getMainView().getSelectedComponent();

        for (TabComponent tab : Managers.getManager(IViewManager.class).getTabComponents()) {
            if (tab.getComponent().equals(component)) {
                selected = tab;
                break;
            }
        }

        return selected;
    }

    @Override
    public IMainView getMainView() {
        return SplashManager.getInstance().getMainView();
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
}