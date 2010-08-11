package org.jtheque.collections;

import org.jtheque.persistence.DataContainer;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.bean.Response;

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
 * A service for the collections functions.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public interface CollectionsService extends DataContainer<DataCollection> {
    String DATA_TYPE = "Collections";

    /**
     * Plug a collection. This method must only make the choose collection process and return the result of the choose.
     * This method must not expensive cost process launch. All the module loading treatments must be made in the
     * plugCollection() method.
     *
     * @param collection The collection to use.
     * @param password   The entered password.
     * @param create     Indicate if we must create the collection or only select it.
     *
     * @return true if the collection choose process has been good processed.
     */
    Response chooseCollection(String collection, String password, boolean create);

    /**
     * Add a collection listener.
     *
     * @param listener The listener to add.
     */
    void addCollectionListener(CollectionListener listener);

    /**
     * Remove the given collection listener.
     *
     * @param listener The listener to remove.
     */
    void removeCollectionListener(CollectionListener listener);
}