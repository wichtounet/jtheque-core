package org.jtheque.core.able.lifecycle;

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

import java.util.EventListener;

/**
 * This listener enable an objet to be up to date with the current function of the application.
 *
 * @author Baptiste Wicht
 */
public interface FunctionListener extends EventListener {
    /**
     * Call when the function has been updated.
     *
     * @param event The function event.
     */
    void functionUpdated(FunctionEvent event);
}