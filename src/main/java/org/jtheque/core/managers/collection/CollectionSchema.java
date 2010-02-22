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

import org.jtheque.core.managers.schema.DefaultSchema;
import org.jtheque.utils.bean.Version;

public class CollectionSchema extends DefaultSchema {
    public CollectionSchema() {
        super(new Version("1.0"), "jtheque-collection-schema");
    }

    @Override
    public void install() {
        updateTable(IDaoCollections.TABLE,
                "CREATE TABLE IF NOT EXISTS {} (ID INT IDENTITY PRIMARY KEY, TITLE VARCHAR(150) NOT NULL UNIQUE, PROTECTED BOOLEAN, PASSWORD VARCHAR(150))");
	    updateTable(IDaoCollections.TABLE, "CREATE INDEX IF NOT EXISTS COLLECTIONS_IDX ON {} (ID)");
    }

    @Override
    public void update(Version from) {
        //First version, no updates possible
    }
}