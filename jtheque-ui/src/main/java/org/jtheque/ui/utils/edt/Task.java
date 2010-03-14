package org.jtheque.ui.utils.edt;

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
 * A task.
 *
 * @author Baptiste Wicht
 */
public abstract class Task<T> extends SimpleTask {
    private T result;

    /**
     * Set the result of the task.
     *
     * @param result The result of the task.
     */
    public final void setResult(T result) {
        this.result = result;
    }

    /**
     * Return the result of the task.
     *
     * @return The result of the task.
     */
    public final T getResult() {
        return result;
    }
}