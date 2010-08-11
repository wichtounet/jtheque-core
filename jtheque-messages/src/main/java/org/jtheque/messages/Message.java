package org.jtheque.messages;

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

import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.bean.IntDate;

/**
 * A message specification. A message must be immutable. 
 */
@Immutable
public interface Message extends Comparable<Message> {
    /**
     * Return the Id of the message.
     *
     * @return The id of the message.
     */
    int getId();

    /**
     * Return the title of the message.
     *
     * @return The title of the message.
     */
    String getTitle();

    /**
     * Return the text of the message.
     *
     * @return The text of the message.
     */
    String getMessage();

    /**
     * Return the date of the message.
     *
     * @return The date of the message.
     */
    IntDate getDate();

    /**
     * Return the source of the message.
     *
     * @return The source of the message.
     */
    String getSource();
}
