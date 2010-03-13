package org.jtheque.core.lifecycle;

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

import java.util.EventObject;

/**
 * An event which indicates a change of the current function of JTheque.
 *
 * @author Baptiste Wicht
 */
public final class FunctionEvent extends EventObject {
    private final String function;

    /**
     * Construct a new function event.
     *
     * @param source   The source of the event.
     * @param function The new function.
     */
    public FunctionEvent(Object source, String function) {
        super(source);

        this.function = function;
    }

    /**
     * Return the new function.
     *
     * @return The new function.
     */
    public String getFunction() {
        return function;
    }
}