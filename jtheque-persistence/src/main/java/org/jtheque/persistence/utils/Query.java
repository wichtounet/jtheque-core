package org.jtheque.persistence.utils;

import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.collections.ArrayUtils;

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
 * A query for Spring JDBC. It seems a couple of query and parameters.
 *
 * @author Baptiste Wicht
 */
@Immutable
public final class Query implements org.jtheque.persistence.Query {
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
        this.parameters = ArrayUtils.copyOf(parameters);
    }

    @Override
    public String getSQLQuery() {
        return sqlQuery;
    }

    @Override
    public Object[] getParameters() {
        return ArrayUtils.copyOf(parameters);
    }
}