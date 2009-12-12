package org.jtheque.core.utils;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;

/**
 * A generic response.
 *
 * @author Baptiste Wicht
 */
public final class Response {
    private final boolean ok;
    private final String key;
    private String[] replaces;

    /**
     * Construct a response with a response true or false and a message key.
     *
     * @param ok  The real response.
     * @param key The message key.
     */
    public Response(boolean ok, String key) {
        super();

        this.key = key;
        this.ok = ok;
    }

    /**
     * Construct a response with a response true or false, a message key and some replaces for the message.
     *
     * @param ok       The real response.
     * @param key      The message key.
     * @param replaces The message replaces.
     */
    public Response(boolean ok, String key, String[] replaces) {
        super();

        this.key = key;
        this.ok = ok;
        this.replaces = replaces.clone();
    }

    /**
     * Indicate if the response is true or false.
     *
     * @return true if the response is ok else false.
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * Return internationalized the message.
     *
     * @return The internationalized message.
     */
    public String getMessage() {
        return replaces == null ?
                Managers.getManager(ILanguageManager.class).getMessage(key) :
                Managers.getManager(ILanguageManager.class).getMessage(key, replaces);
    }
}