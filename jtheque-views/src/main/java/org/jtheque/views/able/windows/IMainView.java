package org.jtheque.views.able.windows;

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

import org.jtheque.ui.able.IWindowView;
import org.jtheque.views.impl.components.panel.JThequeStateBar;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import java.awt.Component;

/**
 * A main view specification.
 *
 * @author Baptiste Wicht
 */
public interface IMainView extends IWindowView {
    /**
     * Return the principal tabbed pane. This tabbed pane is used to display the different principal data of JTheque.
     *
     * @return The tabbed pane who display the principal data.
     */
    JTabbedPane getTabbedPane();

    /**
     * Set the glass pane of the view.
     *
     * @param glassPane The glass pane.
     */
    void setGlassPane(Component glassPane);

    /**
     * Set the selected component.
     *
     * @param component The selected component.
     */
    void setSelectedComponent(Object component);

    /**
     * Return the selected component.
     *
     * @return The selected component.
     */
    JComponent getSelectedComponent();

    /**
     * Return the state bar of the view.
     *
     * @return The statebar of the view.
     */
    JThequeStateBar getStateBar();
}