package org.jtheque.update;

import org.jtheque.states.AbstractState;
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
 * A state for the updatables versions.
 *
 * @author Baptiste Wicht
 */
//Must be public for StateService
public final class UpdatableState extends AbstractState {
    /**
     * Return the version of the updatable.
     *
     * @param name The name of the updatable.
     * @return The version of the updatable.
     */
    public Version getVersion(String name) {
        String property = getProperty(name, "null");

        if ("null".equals(property)) {
            return null;
        }

        return new Version(property);
    }

    /**
     * Set the version of the updatable.
     *
     * @param name    The name of the updatable.
     * @param version The version of the updatable.
     */
    public void setVersion(String name, Version version) {
        setProperty(name, version.getVersion());
    }
}