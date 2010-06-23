package org.jtheque.persistence.utils;

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

import org.jtheque.core.utils.WeakEventListenerList;
import org.jtheque.persistence.able.Dao;
import org.jtheque.persistence.able.DataListener;
import org.jtheque.persistence.able.Entity;
import org.jtheque.persistence.able.IDaoPersistenceContext;
import org.jtheque.persistence.able.QueryMapper;

import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;

/**
 * An abstract Dao using Spring JDBC.
 *
 * @author Baptiste Wicht
 * @param <T> The type of data contained in this DAO.
 */
public abstract class AbstractDao<T extends Entity> implements Dao<T> {
    @Resource
    private IDaoPersistenceContext persistenceContext;

    private final String table;

    private final WeakEventListenerList listenerList = new WeakEventListenerList();

    /**
     * Construct a new AbstractDao.
     *
     * @param table The table of this dao.
     */
    public AbstractDao(String table) {
        super();

        this.table = table;
    }

    @Override
    public final void addDataListener(DataListener listener) {
        listenerList.add(DataListener.class, listener);
    }

    @Override
    public final void removeDataListener(DataListener listener) {
        listenerList.remove(DataListener.class, listener);
    }

    @Override
    public String getTable() {
        return table;
    }

    /**
     * Return the query mapper for the DAO.
     *
     * @return The query mapper for the DAO.
     */
    protected abstract QueryMapper getQueryMapper();

    /**
     * Return the row mapper of this DAO.
     *
     * @return The RowMapper of this DAO.
     */
    protected abstract RowMapper<T> getRowMapper();

    /**
     * Return the persistence context for this dao.
     *
     * @return The persistence context for this dao.
     */
    protected IDaoPersistenceContext getContext() {
        return persistenceContext;
    }

    /**
     * Avert the listeners that the data have changed.
     */
    protected final void fireDataChanged() {
        DataListener[] listeners = listenerList.getListeners(DataListener.class);

        for (DataListener listener : listeners) {
            listener.dataChanged();
        }
    }
}