package org.jtheque.core.managers.persistence;

import java.util.Arrays;

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
 * A query for Spring JDBC. It seems a couple of query and parameters.
 *
 * @author Baptiste Wicht
 */
public final class Query {
    private final String sqlQuery;
    private final Object[] parameters;

    /**
     * Construct a new Query.
     *
     * @param sqlQuery   The SQL Query.
     * @param parameters The parameters of the query.
     */
    public Query(String sqlQuery, Object[] parameters) {
        super();

        this.sqlQuery = sqlQuery;
        this.parameters = Arrays.copyOf(parameters, parameters.length);
    }

    /**
     * Return the SQL Query.
     *
     * @return The SQL Query.
     */
    public String getSqlQuery() {
        return sqlQuery;
    }

    /**
     * Return all the parameters of the query.
     *
     * @return The query parameters.
     */
    public Object[] getParameters() {
        return Arrays.copyOf(parameters, parameters.length);
    }
}
