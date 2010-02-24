package org.jtheque.core.managers.collection;

import org.jtheque.core.managers.AbstractManager;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.schema.ISchemaManager;

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

public class CollectionManager extends AbstractManager implements ICollectionManager {
    @Override
    public void preInit() {
        Managers.getManager(ISchemaManager.class).registerSchema("", new CollectionSchema());
    }
}