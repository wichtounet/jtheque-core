package org.jtheque.schemas.able;

import org.jtheque.utils.annotations.ThreadSafe;

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
 * A Schema manager. It seems a manager who manage the schemas of the database.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public interface SchemaService {
    /**
     * Register a schema.
     *
     * @param moduleId The module id.
     * @param schema   The schema to add.
     *
     * @see Schema
     */
    void registerSchema(String moduleId, Schema schema);
}