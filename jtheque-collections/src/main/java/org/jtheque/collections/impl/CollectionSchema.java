package org.jtheque.collections.impl;

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

import org.jtheque.collections.DaoCollections;
import org.jtheque.schemas.DefaultSchema;
import org.jtheque.utils.bean.Version;

/**
 * A collection schema. This schema describe the database part to store the collection.
 *
 * @author Baptiste Wicht
 */
public final class CollectionSchema extends DefaultSchema {
    /**
     * Construct a new CollectionSchema.
     */
    public CollectionSchema() {
        super(Version.get("1.0"), "jtheque-collection-schema");
    }

    @Override
    public void install() {
        updateTable(DaoCollections.TABLE,
                "CREATE TABLE IF NOT EXISTS {} (ID INT IDENTITY PRIMARY KEY, TITLE VARCHAR(150) NOT NULL UNIQUE, PROTECTED BOOLEAN, PASSWORD VARCHAR(150))");
        updateTable(DaoCollections.TABLE, "CREATE INDEX IF NOT EXISTS COLLECTIONS_IDX ON {} (ID)");
    }

    @Override
    public void update(Version from) {
        //First version, no updates possible
    }
}