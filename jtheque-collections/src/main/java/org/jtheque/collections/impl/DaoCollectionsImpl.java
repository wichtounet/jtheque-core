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

import org.jtheque.collections.able.DaoCollections;
import org.jtheque.collections.able.DataCollection;
import org.jtheque.persistence.able.Entity;
import org.jtheque.persistence.able.QueryMapper;
import org.jtheque.persistence.utils.CachedJDBCDao;
import org.jtheque.persistence.utils.EntityUtils;
import org.jtheque.persistence.utils.Query;
import org.jtheque.utils.annotations.ThreadSafe;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * A Data Access Object implementation for collections.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public final class DaoCollectionsImpl extends CachedJDBCDao<DataCollection> implements DaoCollections {
    private final RowMapper<DataCollection> rowMapper = new CollectionRowMapper();
    private final QueryMapper queryMapper = new CollectionQueryMapper();

    /**
     * The current collection.
     */
    private volatile DataCollection currentCollection;

    /**
     * Construct a new DaoCollectionsImpl.
     */
    public DaoCollectionsImpl() {
        super(TABLE);
    }

    @Override
    public Collection<DataCollection> getCollections() {
        return getAll();
    }

    @Override
    public DataCollection getCollection(int id) {
        return get(id);
    }

    @Override
    public DataCollection getCollectionByTemporaryId(int id) {
        return EntityUtils.getByTemporaryId(getAll(), id);
    }

    @Override
    public boolean exists(String collection) {
        return getCollection(collection) != null;
    }

    @Override
    public boolean exists(DataCollection collection) {
        return exists(collection.getTitle());
    }

    @Override
    public DataCollection getCollection(String name) {
        for (DataCollection collection : getAll()) {
            if (name.equals(collection.getTitle())) {
                return collection;
            }
        }

        return null;
    }

    @Override
    protected RowMapper<DataCollection> getRowMapper() {
        return rowMapper;
    }

    @Override
    protected QueryMapper getQueryMapper() {
        return queryMapper;
    }

    @Override
    protected void loadCache() {
        defaultFillCache();
    }

    @Override
    public DataCollection getCurrentCollection() {
        return currentCollection;
    }

    @Override
    public void setCurrentCollection(DataCollection collection) {
        currentCollection = collection;
    }

    @Override
    public DataCollection create() {
        return new DataCollectionImpl();
    }

    /**
     * A row mapper to map resultset to collection.
     *
     * @author Baptiste Wicht
     */
    private final class CollectionRowMapper implements ParameterizedRowMapper<DataCollection> {
        @Override
        public DataCollection mapRow(ResultSet rs, int i) throws SQLException {
            DataCollection collection = create();

            collection.setId(rs.getInt("ID"));
            collection.setTitle(rs.getString("TITLE"));
            collection.setPassword(rs.getString("PASSWORD"));
            collection.setProtection(rs.getBoolean("PROTECTED"));

            return collection;
        }
    }

    /**
     * A query mapper to map collection to sql query.
     *
     * @author Baptiste Wicht
     */
    private static final class CollectionQueryMapper implements QueryMapper {
        @Override
        public Query constructInsertQuery(Entity entity) {
            String query = "INSERT INTO " + TABLE + " (TITLE, PASSWORD, PROTECTED) VALUES(?, ?, ?)";

            return new Query(query, fillArray((DataCollection) entity, false));
        }

        @Override
        public Query constructUpdateQuery(Entity entity) {
            String query = "UPDATE " + TABLE + " SET TITLE = ?, PASSWORD = ?, PROTECTED = ? WHERE ID = ?";

            return new Query(query, fillArray((DataCollection) entity, true));
        }

        /**
         * Fill the array with the informations of the collection.
         *
         * @param collection The collection to use to fill the array.
         * @param id         Indicate if we must add the id to the array.
         *
         * @return The filled array.
         */
        private static Object[] fillArray(DataCollection collection, boolean id) {
            Object[] values = new Object[3 + (id ? 1 : 0)];

            values[0] = collection.getTitle();
            values[1] = collection.getPassword();
            values[2] = collection.isProtection();

            if (id) {
                values[3] = collection.getId();
            }

            return values;
        }
    }
}