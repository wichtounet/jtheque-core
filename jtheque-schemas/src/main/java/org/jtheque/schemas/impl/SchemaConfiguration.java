package org.jtheque.schemas.impl;

import org.jtheque.states.State;
import org.jtheque.states.utils.AbstractConcurrentState;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.bean.Version;

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
 * The configuration of the schemas.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
@State(id = "jtheque-schema-configuration")
public final class SchemaConfiguration extends AbstractConcurrentState {
    /**
     * Return the version of the schema.
     *
     * @param name The name of the schema.
     *
     * @return The version of the schema.
     */
    public synchronized Version getVersion(String name) {
        String property = getProperty(name + "-version", "null");

        if ("null".equals(property)) {
            return null;
        }

        return Version.get(property);
    }

    /**
     * Set the version of the schema.
     *
     * @param name    The name of the schema.
     * @param version The version of the schema.
     */
    public void setVersion(String name, Version version) {
        setProperty(name + "-version", version.getVersion());
    }
}