package org.jtheque.ui.able;

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

/**
 * A window view.
 *
 * @author Baptiste Wicht
 */
public interface IWindowView extends IView {
    /**
     * Return the width of the window.
     *
     * @return The width of the window.
     */
    int getWidth();

    /**
     * Return the height of the window.
     *
     * @return The height of the window.
     */
    int getHeight();
}