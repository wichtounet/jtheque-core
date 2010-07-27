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

import org.jtheque.messages.able.IMessage;
import org.jtheque.ui.able.IModel;

/**
 * @author Baptiste Wicht
 */
public interface IMessageModel extends IModel {
    /**
     * Return the next message to display.
     *
     * @return The next message to display.
     */
    IMessage getNextMessage();

    /**
     * Return the current message.
     *
     * @return The current message.
     */
    IMessage getCurrentMessage();

    /**
     * Return the previous message.
     *
     * @return The previous message.
     */
    IMessage getPreviousMessage();

    /**
     * Indicate if the model is currently displaying the default message or a real message.
     *
     * @return true if the current message is the default message else false.
     */
    boolean isDefaultMessage();
}
