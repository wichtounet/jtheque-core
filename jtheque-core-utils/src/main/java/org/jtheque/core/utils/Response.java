package org.jtheque.core.utils;

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
 * A generic response.
 *
 * @author Baptiste Wicht
 */
public final class Response {
    private final boolean ok;
    private final String key;
    private String[] replaces;

    /**
     * Construct a new Response.
     *
     * @param ok A boolean tag indicating if the response is ok else false.
     */
    public Response(boolean ok) {
        super();

        this.ok = ok;
        key = "";
    }

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
     * Return the i18n key of the response.
     *
     * @return The i18n key of the response.
     */
    public String getKey() {
        return key;
    }

    /**
     * Return all the i18n replaces of the response.
     *
     * @return The i18n replaces of the response.
     */
    public String[] getReplaces() {
        return replaces;
    }
}