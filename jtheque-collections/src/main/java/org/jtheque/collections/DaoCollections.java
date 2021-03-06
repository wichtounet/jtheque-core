package org.jtheque.collections;

import org.jtheque.persistence.Dao;
import org.jtheque.utils.annotations.ThreadSafe;

import java.util.Collection;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * A DAO for collections specification.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public interface DaoCollections extends Dao<DataCollection> {
    String TABLE = "T_COLLECTIONS";

    /**
     * Return the collections.
     *
     * @return All the collections.
     */
    Collection<DataCollection> getCollections();

    /**
     * Return the collection with the specific ID.
     *
     * @param id The searched ID.
     *
     * @return The corresponding collection.
     */
    DataCollection getCollection(int id);

    /**
     * Return the collection of the specified name.
     *
     * @param name The name of the collection.
     *
     * @return The <code>Collection</code> with the specified name or <code>null</code> if there is no collection with
     *         this name.
     */
    DataCollection getCollection(String name);

    /**
     * Return the current collection.
     *
     * @return The current collection.
     */
    DataCollection getCurrentCollection();

    /**
     * Set the current collection.
     *
     * @param collection The current collection to set.
     */
    void setCurrentCollection(DataCollection collection);

    /**
     * Return the collection with the given temporary id.
     *
     * @param id The id to search in the temporary id of the collections.
     *
     * @return The collection with the given temporary id or null if there is no collection with the specified temporary
     *         id.
     */
    DataCollection getCollectionByTemporaryId(int id);

    /**
     * Test if a collection exists or not.
     *
     * @param collection The name of collection to test.
     *
     * @return true if the collection exists else false.
     */
    boolean exists(String collection);
}