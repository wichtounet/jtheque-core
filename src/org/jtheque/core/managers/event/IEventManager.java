package org.jtheque.core.managers.event;

import org.jtheque.core.managers.IManager;

import java.util.Collection;
import java.util.Set;

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

/**
 * An event manager specification.
 *
 * @author Baptiste Wicht
 */
public interface IEventManager extends IManager {
    /**
     * Return all the logs.
     *
     * @return A Set containing all the logs.
     */
    Set<String> getLogs();

    /**
     * Return all the events from a log.
     *
     * @param log The log to get the events from.
     * @return A List containing all the events of the log.
     */
    Collection<EventLog> getEventLogs(String log);

    /**
     * Add an event to a log.
     *
     * @param log   The log to add the event to.
     * @param event The event to add.
     */
    void addEventLog(String log, EventLog event);
}