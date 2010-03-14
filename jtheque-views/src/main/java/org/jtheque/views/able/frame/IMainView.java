package org.jtheque.views.able.frame;

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

import org.jtheque.ui.IView;
import org.jtheque.views.impl.components.panel.JThequeStateBar;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import java.awt.Component;

/**
 * A main view specification.
 *
 * @author Baptiste Wicht
 */
public interface IMainView extends IView {
    /**
     * Return the principal tabbed pane. This tabbed pane is used to display the different principal data of
     * JTheque.
     *
     * @return The tabbed pane who display the principal data.
     */
    JTabbedPane getTabbedPane();

    void build();

    /**
     * Build the entire view with the final content.
     */
    void fill();

    /**
     * Set the glass pane of the view.
     *
     * @param glassPane The glass pane.
     */
    void setGlassPane(Component glassPane);

    /**
     * Return the height of the view.
     *
     * @return The height of the view.
     */
    int getHeight();

    /**
     * Start the waiting process.
     */
    void startWait();

    /**
     * Stop the waiting process.
     */
    void stopWait();

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

    JThequeStateBar getStateBar();
}