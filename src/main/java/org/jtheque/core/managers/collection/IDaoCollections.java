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

import org.jtheque.core.managers.persistence.able.Dao;

/**
 * A DAO for collections specification.
 *
 * @author Baptiste Wicht
 */
public interface IDaoCollections extends Dao<Collection> {
	String TABLE = "T_COLLECTIONS";

	/**
	 * Return the collections.
	 *
	 * @return All the collections.
	 */
	java.util.Collection<Collection> getCollections();

	/**
	 * Return the collection with the specific ID.
	 *
	 * @param id The searched ID.
	 *
	 * @return The corresponding collection.
	 */
	Collection getCollection(int id);

	/**
	 * Return the collection of the specified name.
	 *
	 * @param name The name of the collection.
	 *
	 * @return The <code>Collection</code> with the specified name or <code>null</code> if there is no collection with this name.
	 */
	Collection getCollection(String name);

	/**
	 * Return the current collection.
	 *
	 * @return The current collection.
	 */
	Collection getCurrentCollection();

	/**
	 * Set the current collection.
	 *
	 * @param collection The current collection to set.
	 */
	void setCurrentCollection(Collection collection);

    Collection getCollectionByTemporaryId(int id);

    boolean exists(String collection);
}