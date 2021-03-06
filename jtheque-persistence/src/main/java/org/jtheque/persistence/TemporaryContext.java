package org.jtheque.persistence;

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
 * A temporary context for an entity. A temporary context is used during recovering or importing entities to temporary
 * store the old id to make the links between entities.
 *
 * @author Baptiste Wicht
 */
public interface TemporaryContext {
    /**
     * Return the id of the context.
     *
     * @return The id.
     */
    int getId();

    /**
     * Set the id of the context.
     *
     * @param id The new id to set.
     */
    void setId(int id);
}
