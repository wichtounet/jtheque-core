package org.jtheque.core.managers.persistence.able;

import java.util.Collection;

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
 * A data container. It seems a class who can give access to a list of Entity.
 *
 * @author Baptiste Wicht
 * @param <T> The class of data this container can provide.
 */
public interface DataContainer<T extends Entity> {
    /**
     * Return the datas of the container.
     *
     * @return A List containing all the datas of the container.
     */
    Collection<T> getDatas();

    /**
     * Clear all the datas.
     */
    void clearAll();

    /**
     * Add a data listener to the container.
     *
     * @param listener The listener to add.
     */
    void addDataListener(DataListener listener);

    /**
     * Return the data type.
     *
     * @return The data type.
     */
    String getDataType();
}