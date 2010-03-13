package org.jtheque.messages;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.Collection;

/**
 * A message manager. This manager is responsible to get and to display message from the core, the application and the
 * modules websites.
 *
 * @author Baptiste Wicht
 */
public interface IMessageManager {
    /**
     * Return all the messages.
     *
     * @return A List containing all the messages.
     */
    Collection<Message> getMessages();

    /**
     * Load the messages from the different sources.
     */
    void loadMessages();

    /**
     * Display the messages if needed.
     */
    boolean isDisplayNeeded();
}
