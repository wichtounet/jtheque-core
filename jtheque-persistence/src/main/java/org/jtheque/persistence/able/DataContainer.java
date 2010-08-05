package org.jtheque.persistence.able;

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
 * A data container. It seems a class who can give access to a list of Entity.
 *
 * @author Baptiste Wicht
 * @param <T> The type of data this container can provide.
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