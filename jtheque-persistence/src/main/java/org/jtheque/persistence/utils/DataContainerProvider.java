package org.jtheque.persistence.utils;

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

import org.jtheque.persistence.able.DataContainer;
import org.jtheque.persistence.able.Entity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A provider for dao. This class give access to all DAOs or to specific dao with different
 * parameters of search.
 *
 * @author Baptiste Wicht
 */
public final class DataContainerProvider {
    private final Collection<DataContainer<? extends Entity>> containers = new ArrayList<DataContainer<? extends Entity>>(10);

    private static final DataContainerProvider INSTANCE = new DataContainerProvider();

    /**
     * Add a container to manage.
     *
     * @param container The container to add.
     */
    public void addContainer(DataContainer<? extends Entity> container) {
        assert container != null : "Container can't be null";

        containers.add(container);
    }

    /**
     * Remove a container.
     *
     * @param container The container to remove.
     */
    public void removeContainer(DataContainer<? extends Entity> container) {
        containers.remove(container);
    }

    /**
     * Return the unique instance of the DaoProvider.
     *
     * @return The unique instance of the class.
     */
    public static DataContainerProvider getInstance() {
        return INSTANCE;
    }

    /**
     * Return all the DAOs.
     *
     * @return A List containing all the DAO.
     */
    public Iterable<DataContainer<? extends Entity>> getAllContainers() {
        return containers;
    }

    /**
     * Return the DAO for a specific data type.
     *
     * @param dataType The data type for which we want the DAO.
     * @return The dao or <code>null</code> if we don't find one.
     */
    public DataContainer<? extends Entity> getContainerForDataType(String dataType) {
        DataContainer<? extends Entity> container = null;

        for (DataContainer<? extends Entity> d : containers) {
            if (dataType.equals(d.getDataType())) {
                container = d;

                break;
            }
        }

        return container;
    }
}