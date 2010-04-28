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
import org.jtheque.ui.able.IUIUtils;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * A controller for the main view.
 *
 * @author Baptiste Wicht
 */
public final class MainController extends WindowAdapter implements ChangeListener {
    private final ICore core;
    private final IUIUtils uiUtils;

    public MainController(ICore core, IUIUtils uiUtils) {
        super();

        this.core = core;
        this.uiUtils = uiUtils;
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        JTabbedPane tabbedPane = (JTabbedPane) event.getSource();

        int index = tabbedPane.getSelectedIndex();

        if (index == -1) {
            core.getLifeCycle().setCurrentFunction("");
        } else {
            core.getLifeCycle().setCurrentFunction(tabbedPane.getTitleAt(index));
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Object[] applicationName = {core.getApplication().getName()};
        
        boolean yes = uiUtils.askI18nUserForConfirmation(
                "dialogs.confirm.exit", applicationName,
                "dialogs.confirm.exit.title",applicationName);

        if (yes) {
            core.getLifeCycle().exit();
        }
    }
}