package org.jtheque.core.managers.view.able.components;

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

import javax.swing.JComponent;

/**
 * A main component. This kind of component can be added to the main view. If there is only one main
 * component, it will be displayed normally else it will displayed inside a tabbed pane.
 *
 * @author Baptiste Wicht
 */
public interface MainComponent {
    /**
     * Return the component implement.
     *
     * @return The component.
     */
    JComponent getComponent();

    /**
     * Return the position of the tab component. This method is not used if there is only one main component.
     *
     * @return An Integer representing the position of the tab component.
     */
    Integer getPosition();

    /**
     * Return the title key of the tab component. This method is not used if there is only one main component.
     *
     * @return The internationalization key.
     */
    String getTitleKey();
}