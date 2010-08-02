package org.jtheque.persistence.able;

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
 * A persistence manager. It seems the manager who's responsible for the persistence of the work entities. This manager
 * provide methods to manage entities and to open secondary connection.
 *
 * @author Baptiste Wicht
 */
public interface PersistenceService {
    /**
     * Clear the database.
     */
    void clearDatabase();
}