package org.jtheque.events.able;

import java.util.Collection;
import java.util.Set;

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
 * An event manager specification.
 *
 * @author Baptiste Wicht
 */
public interface IEventService {
    String CORE_EVENT_LOG = "JTheque Core";

    /**
     * Return all the logs.
     *
     * @return A Set containing all the logs.
     */
    Set<String> getEventLogs();

    /**
     * Return all the events from a log.
     *
     * @param log The log to get the events from.
     *
     * @return A List containing all the events of the log.
     */
    Collection<IEvent> getEvents(String log);

    /**
     * Add an event to a log.
     *
     * @param log   The log to add the event to.
     * @param event The event to add.
     */
    void addEvent(String log, IEvent event);
}