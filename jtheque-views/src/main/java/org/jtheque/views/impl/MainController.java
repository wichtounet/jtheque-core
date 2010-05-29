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

    /**
     * Construct a new MainController.
     *
     * @param core    The core.
     * @param uiUtils The UI Utils.
     */
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
                "dialogs.confirm.exit.title", applicationName);

        if (yes) {
            core.getLifeCycle().exit();
        }
    }
}