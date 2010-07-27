package org.jtheque.messages.able;

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

import org.jtheque.utils.annotations.ThreadSafe;

import java.util.Collection;

/**
 * A message manager. This manager is responsible to get and to display message from the core, the application and the
 * modules websites.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public interface IMessageService {
    /**
     * Return all the messages.
     *
     * @return A List containing all the messages.
     */
    Collection<Message> getMessages();

    /**
     * Display the messages if needed.
     *
     * @return true if there is messages to display else false.
     */
    boolean isDisplayNeeded();

    /**
     * Return an empty message.
     *
     * @return An empty message.
     */
    Message getEmptyMessage();
}
