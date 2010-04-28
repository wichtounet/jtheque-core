package org.jtheque.views.able.components;

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

import org.jtheque.errors.JThequeError;

import javax.swing.JComponent;
import java.util.Collection;

/**
 * A config panel.
 *
 * @author Baptiste Wicht
 */
public interface ConfigTabComponent {
    /**
     * Return the title of the config tab.
     *
     * @return The title of the tab.
     */
    String getTitleKey();

    /**
     * Apply all the changes.
     */
    void apply();

    /**
     * Cancel all the changes.
     */
    void cancel();

    /**
     * Validate the tab component.
     *
     * @param errors The error's list.
     */
    void validate(Collection<JThequeError> errors);

    /**
     * Return the implementation of the config tab.
     *
     * @return The implementation of the config tab.
     */
    JComponent getComponent();
}