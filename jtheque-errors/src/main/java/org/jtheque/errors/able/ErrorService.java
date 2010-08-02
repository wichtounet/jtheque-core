package org.jtheque.errors.able;

import org.jtheque.utils.annotations.ThreadSafe;

import java.util.Collection;

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
 * An error service specification. It seems a manager who enable module to display some errors in a simple way.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public interface ErrorService {
    /**
     * Add an error.
     *
     * @param error A new error.
     */
    void addError(Error error);

    /**
     * Return all the errors occurred in this session.
     *
     * @return A List containing all the errors occurred in this session.
     */
    Collection<Error> getErrors();

    /**
     * Add an error listener to listen to new errors.
     *
     * @param listener The new listener.
     */
    void addErrorListener(ErrorListener listener);

    /**
     * Remove an error listener.
     *
     * @param listener The listener to remove.
     */
    void removeErrorListener(ErrorListener listener);
}