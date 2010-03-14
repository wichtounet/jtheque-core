package org.jtheque.persistence.context;

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
 * A temporary context. It's a context for a data who's temporary and not stored somewhere.
 *
 * @author Baptiste Wicht
 */
public class TemporaryContext {
    private int id;

    /**
     * Return the id of the context.
     *
     * @return The id.
     */
    public final int getId() {
        return id;
    }

    /**
     * Set the id of the context.
     *
     * @param id The new id to set.
     */
    public final void setId(int id) {
        this.id = id;
    }
}