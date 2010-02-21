package org.jtheque.core.managers.collection;

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

import org.jtheque.core.managers.persistence.able.DataContainer;

/**
 * A service for the collections functions.
 *
 * @author Baptiste Wicht
 */
public interface ICollectionsService extends DataContainer<Collection> {
	String DATA_TYPE = "Collections";

    /**
     * Plug a collection. This method must only make the choose collection process and return the
     * result of the choose. This method must not expensive cost process launch. All the
     * module loading treatments must be made in the plugCollection() method.
     *
     * @param collection The collection to use.
     * @param password   The entered password.
     * @param create     Indicate if we must create the collection or only select it.
     * @return true if the collection choose process has been good processed.
     */
    boolean chooseCollection(String collection, String password, boolean create);
}