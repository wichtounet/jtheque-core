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

import org.jtheque.persistence.Entity;
import org.jtheque.persistence.TemporaryContext;
import org.jtheque.utils.annotations.GuardedInternally;
import org.jtheque.utils.annotations.ThreadSafe;

/**
 * Represents a persisted object of JTheque.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public abstract class AbstractEntity implements Entity {
    private volatile int id;

    @GuardedInternally
    private final TemporaryContext temporaryContext = new DefaultTemporaryContext();

    @Override
    public TemporaryContext getTemporaryContext() {
        return temporaryContext;
    }

    @Override
    public final int getId() {
        return id;
    }

    @Override
    public final void setId(int id) {
        this.id = id;
    }

    @Override
    public final int compareTo(Entity object) {
        return getDisplayableText().compareTo(object.getDisplayableText());
    }

    @Override
    public final boolean isSaved() {
        return id != 0;
    }

    @Override
    public void saveToMemento() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void restoreMemento() {
        throw new UnsupportedOperationException();
    }

    @Override
    public abstract boolean equals(Object object);

    @Override
    public abstract int hashCode();
}