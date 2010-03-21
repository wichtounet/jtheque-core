package org.jtheque.persistence.able;

import org.jtheque.persistence.Query;

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
     * @return The builded query.
     */
    Query constructInsertQuery(Entity entity);

    /**
     * Construct an org.jtheque.update query of the entity.
     *
     * @param entity The entity to build the query for.
     * @return The builded query.
     */
    Query constructUpdateQuery(Entity entity);
}