package org.jtheque.update;

import org.jdesktop.swingx.event.WeakEventListenerList;
import org.jtheque.utils.bean.Version;

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
 * An abstract updatable.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractUpdatable implements Updatable {
    private final String key;
    private final String name;
    private Version version;

    private final WeakEventListenerList listenerList;

    /**
     * Construct a new AbstractUpdatable.
     *
     * @param name The name of the updatable.
     * @param key  The internationalization key.
     */
    protected AbstractUpdatable(String name, String key) {
        super();

        this.key = key;
        this.name = name;

        listenerList = new WeakEventListenerList();
    }

    @Override
    public final void addUpdateListener(UpdateListener listener) {
        listenerList.add(UpdateListener.class, listener);
    }

    @Override
    public final void removeUpdateListener(UpdateListener listener) {
        listenerList.remove(UpdateListener.class, listener);
    }

    @Override
    public final void setUpdated() {
        fireUpdated();
    }

    /**
     * Fire an updated event.
     */
    private void fireUpdated() {
        UpdateListener[] listeners = listenerList.getListeners(UpdateListener.class);

        for (UpdateListener l : listeners) {
            l.updated();
        }
    }

    @Override
    public final void setVersion(Version version) {
        this.version = version;
    }

    @Override
    public final Version getVersion() {
        return version;
    }

    @Override
    public final String getKey() {
        return key;
    }

    @Override
    public final String getName() {
        return name;
    }
}