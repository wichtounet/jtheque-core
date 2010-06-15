package org.jtheque.persistence.able;

import org.jtheque.persistence.utils.Query;

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
 * A query mapper. It seems a constructors of SQL Queries.
 *
 * @author Baptiste Wicht
 */
public interface QueryMapper {
    /**
     * Construct an insert query for the entity.
     *
     * @param entity The entity to build the query for.
     *
     * @return The builded query.
     */
    Query constructInsertQuery(Entity entity);

    /**
     * Construct an org.jtheque.update query of the entity.
     *
     * @param entity The entity to build the query for.
     *
     * @return The builded query.
     */
    Query constructUpdateQuery(Entity entity);
}