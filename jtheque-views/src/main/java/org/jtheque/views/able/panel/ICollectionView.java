package org.jtheque.views.able.panel;

import java.awt.Component;

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
 * A collection view specification.
 *
 * @author Baptiste Wicht
 */
public interface ICollectionView {
    /**
     * Set the error message to display on the view.
     *
     * @param message The error message.
     */
    void setErrorMessage(String message);

    /**
     * Return the entered collection.
     *
     * @return the entered collection.
     */
    String getCollection();

    /**
     * Return the entered password.
     *
     * @return The entered password.
     */
    String getPassword();

    /**
     * Make the view appear.
     */
    void appear();

    /**
     * Return the implementation of the view.
     *
     * @return The implementation of the view.
     */
    Component getImpl();
}