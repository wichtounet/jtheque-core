package org.jtheque.persistence.able;

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
 * A query mapper. Namely, a query mapper create SQL queries from Entity to insert them on database or update them.
 *
 * @author Baptiste Wicht
 * @see org.jtheque.persistence.able.Query
 * @see org.jtheque.persistence.able.Entity
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