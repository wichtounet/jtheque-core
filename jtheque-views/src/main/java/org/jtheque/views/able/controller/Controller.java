package org.jtheque.views.able.controller;

import org.jtheque.ui.IView;


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
 * A UI controller.
 *
 * @author Baptiste Wicht
 */
public interface Controller {
    /**
     * Return the view of the controller.
     *
     * @return The view
     */
    IView getView();

    /**
     * Display the view associated with the controller.
     */
    void displayView();

    /**
     * Close the view associated with the controller.
     */
    void closeView();
}