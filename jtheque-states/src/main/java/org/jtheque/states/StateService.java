package org.jtheque.states;

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
 * A state manager. It seems a manager who's responsible for the persistence of the states. This class is
 * unconditionally thread safe. 
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public interface StateService {
    /**
     * Return the state of the given instance. The class must be annotated with @State and must contains and a @Load and
     * a @Save method.
     *
     * @param state The state to get.
     * @param <T>   The type of state.
     *
     * @throws IllegalArgumentException If the class is not annotated with @State or doesn't contains an @Save and
     * an @Load method.
     * @throws NullPointerException If state is null. 
     *
     * @return The loaded state. The instance is the same but the state has been initialized.
     */
    <T> T getState(T state);
}