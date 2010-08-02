package org.jtheque.errors.able;

import java.util.EventListener;

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
 * An error listener. This listener listen to new errors.
 *
 * @author Baptiste Wicht
 */
public interface ErrorListener extends EventListener {
    /**
     * A new error has occurred in the application.
     *
     * @param error The new error.
     */
    void errorOccurred(Error error);
}