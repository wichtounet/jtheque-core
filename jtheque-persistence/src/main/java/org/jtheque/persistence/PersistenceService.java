package org.jtheque.persistence;

import org.jtheque.utils.annotations.ThreadSafe;

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
 * A persistence service. This service is very simple, the only operation it provide is a method to clear the entire
 * database.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public interface PersistenceService {
    /**
     * Clear the database. Only the data are deleted, not the database, the tables and the indexes.
     */
    void clearDatabase();
}