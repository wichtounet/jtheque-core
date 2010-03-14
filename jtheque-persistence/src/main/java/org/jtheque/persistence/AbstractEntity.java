package org.jtheque.persistence;

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

import org.jtheque.persistence.able.Entity;
import org.jtheque.persistence.context.TemporaryContext;

/**
 * Represents a persisted object of JTheque.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractEntity implements Entity {
    private int id;

    private final TemporaryContext temporaryContext;

    protected static final int HASHCODE_PRIME = 31;

    /**
     * Construct a new Entity.
     */
    public AbstractEntity() {
        super();

        temporaryContext = new TemporaryContext();
    }

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
    public abstract boolean equals(Object object);

    @Override
    public abstract int hashCode();

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
}