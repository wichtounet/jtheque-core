package org.jtheque.messages.impl;

import org.jtheque.messages.able.IMessage;
import org.jtheque.utils.bean.IntDate;

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
 * A builder to create IMessage.
 *
 * @author Baptiste Wicht
 */
public final class Messages {
    /**
     * Utility class, not instantiable. 
     */
    private Messages() {
        throw new AssertionError();
    }

    /**
     * Create a new message at the date of today.
     *
     * @param id The id of the message.
     *
     * @return A new message with the given properties.
     */
    public static IMessage newEmptyTodayMessage(int id) {
        return new Message(id, "", "", IntDate.today(), "");
    }

    /**
     * Create a new message.
     *
     * @param id      The id of the message.
     * @param title   The title of the message.
     * @param message The message.
     * @param date    The date of the message.
     * @param source  The source of the message.
     *
     * @return A new message with the given properties.
     */
    public static Message newMessage(int id, String title, String message, IntDate date, String source) {
        return new Message(id, title, message, date, source);
    }
}