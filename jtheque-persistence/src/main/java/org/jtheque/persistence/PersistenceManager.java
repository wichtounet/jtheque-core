package org.jtheque.persistence;

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

import org.jtheque.persistence.able.DataContainer;
import org.jtheque.persistence.able.DataContainerProvider;
import org.jtheque.persistence.able.Entity;
import org.jtheque.persistence.able.IPersistenceManager;

/**
 * A persistence manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class PersistenceManager implements IPersistenceManager {
    @Override
    public void clearDatabase() {
        for (DataContainer<? extends Entity> dao : DataContainerProvider.getInstance().getAllContainers()) {
            dao.clearAll();
        }
    }
}