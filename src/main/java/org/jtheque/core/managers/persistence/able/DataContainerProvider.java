package org.jtheque.core.managers.persistence.able;

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