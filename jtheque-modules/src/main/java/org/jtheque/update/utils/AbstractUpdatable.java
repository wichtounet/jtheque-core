package org.jtheque.update.utils;

import org.jtheque.update.able.Updatable;
import org.jtheque.update.able.UpdateListener;
import org.jtheque.utils.bean.Version;

import org.jdesktop.swingx.event.WeakEventListenerList;

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