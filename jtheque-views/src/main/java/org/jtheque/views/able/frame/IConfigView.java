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

import org.jtheque.ui.able.IView;
import org.jtheque.views.able.components.ConfigTabComponent;

/**
 * A config view specification.
 *
 * @author Baptiste Wicht
 */
public interface IConfigView extends IView {
    /**
     * Return the selected config panel.
     *
     * @return The selected config panel.
     */
    ConfigTabComponent getSelectedPanelConfig();
}