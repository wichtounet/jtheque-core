package org.jtheque.events;

import org.jtheque.unit.AbstractJThequeTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "jtheque-events-test.xml")
public class EventServiceTest extends AbstractJThequeTest {
    @Resource
    private EventService eventService;

    @Test
    public void initOK() {
        assertNotNull(eventService);
    }

    @Test
    public void onlyOneLogByDefault() {
        assertEquals(1, eventService.getEventLogs().size());
        assertTrue(eventService.getEventLogs().contains(EventService.CORE_EVENT_LOG));
    }

    @Test
    public void noEventsByDefault() {
        assertEquals(0, eventService.getEvents(EventService.CORE_EVENT_LOG).size());
    }

    @Test
    public void addEventInDefaultLog() {
        Event event = Events.newEvent(EventLevel.INFO, "source", "key", EventService.CORE_EVENT_LOG);

        eventService.addEvent(event);

        assertEquals(1, eventService.getEventLogs().size());
        assertTrue(eventService.getEvents(EventService.CORE_EVENT_LOG).contains(event));
    }

    @Test
    public void addEventInNewLog() {
        String eventLog = "Super log of mine";
        Event event = Events.newEvent(EventLevel.INFO, "source", "key", eventLog);

        eventService.addEvent(event);

        assertEquals(2, eventService.getEventLogs().size());
        assertTrue(eventService.getEvents(eventLog).contains(event));
        assertTrue(eventService.getEventLogs().contains(eventLog));
    }
}