package org.jtheque.views.impl.models;

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

import org.jtheque.messages.Message;
import org.jtheque.ui.Model;

/**
 * A simple model for the messages. 
 *
 * @author Baptiste Wicht
 */
public interface IMessageModel extends Model {
    /**
     * Return the next message to display.
     *
     * @return The next message to display.
     */
    Message getNextMessage();

    /**
     * Return the current message.
     *
     * @return The current message.
     */
    Message getCurrentMessage();

    /**
     * Return the previous message.
     *
     * @return The previous message.
     */
    Message getPreviousMessage();

    /**
     * Indicate if the model has a next element or not.
     *
     * @return {@code true} if the model has a next element otherwise {@code false}.
     */
    boolean hasNext();

    /**
     * Indicate if the model has a previous element or not.
     *
     * @return {@code true} if the model has a previous element otherwise {@code false}.
     */
    boolean hasPrevious();
}
