package org.jtheque.views.able.windows;

import org.jtheque.ui.able.View;/*
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
 * A message view specification.
 *
 * @author Baptiste Wicht
 */
public interface MessageView extends View {
    /**
     * Display the next message.
     */
    void next();

    /**
     * Display the previous message.
     */
    void previous();
}
