package org.jtheque.core.managers.persistence;

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

import org.jdesktop.swingx.event.WeakEventListenerList;
import org.jtheque.core.managers.persistence.able.Dao;
import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.core.managers.persistence.able.Entity;
import org.jtheque.core.managers.persistence.context.IDaoPersistenceContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import javax.annotation.Resource;

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
    public String getTable(){
        return table;
    }

    /**
     * Return the query mapper for the DAO.
     *
     * @return The query mapper for the DAO.
     */
    protected abstract QueryMapper getQueryMapper();

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
