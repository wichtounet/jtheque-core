package org.jtheque.core.managers.view.able.config;

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

import javax.swing.JCheckBox;

/**
 * A config view for others specification.
 *
 * @author Baptiste Wicht
 */
public interface IOthersConfigView {
    /**
     * Return the checkbox indicating if we must delete logs or not.
     *
     * @return boxDeleteLogs The check box indicating if we must delete logs or not.
     */
    JCheckBox getBoxDeleteLogs();
}
