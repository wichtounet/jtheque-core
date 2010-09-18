package org.jtheque.events.impl;

import org.jtheque.events.Event;
import org.jtheque.events.EventLevel;
import org.jtheque.events.EventService;
import org.jtheque.events.Events;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.SystemProperty;
import org.jtheque.utils.ThreadUtils;
import org.jtheque.utils.annotations.GuardedInternally;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.xml.utils.XML;
import org.jtheque.xml.utils.XMLException;
import org.jtheque.xml.utils.XMLOverReader;
import org.jtheque.xml.utils.XMLWriter;

import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

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
 * An event manager implementation.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public final class EventServiceImpl implements EventService {
    @GuardedInternally
    private final Map<String, Collection<Event>> logs = CollectionUtils.newConcurrentMap(5);

    /**
     * Construct a new LogEventServiceImpl.
     */
    public EventServiceImpl() {
        super();

        logs.put(CORE_EVENT_LOG, CollectionUtils.<Event>newSet(50));
    }

    @Override
    public Collection<String> getEventLogs() {
        return CollectionUtils.protect(logs.keySet());
    }

    @Override
    public Collection<Event> getEvents(String log) {
        return CollectionUtils.protect(logs.get(log));
    }

    @Override
    public void addEvent(Event event) {
        synchronized (this) {
            if (!logs.containsKey(event.getLog())) {
                logs.put(event.getLog(), CollectionUtils.<Event>newList(25));
            }

            logs.get(event.getLog()).add(event);
        }
    }

    /**
     * Import from XML. The import is made asynchronously to avoid making the application pause during launching. 
     */
    @PostConstruct
    public void importFromXML() {
        final File f = new File(SystemProperty.USER_DIR.get(), "/logs.xml");

        if (f.exists()) {
            ThreadUtils.inNewThread(new Runnable() {
                @Override
                public void run() {
                    readEvents(f);
                }
            });
        } else {
            createEmptyEventFile(f);
        }
    }

    /**
     * Read all the events from the file.
     *
     * @param f The file to read events from.
     */
    private void readEvents(File f) {
        XMLOverReader reader = XML.newJavaFactory().newOverReader();

        try {
            reader.openFile(f);

            while (reader.next("log")) {
                String name = reader.readString("@name");

                Collection<Event> events = CollectionUtils.newList(50);

                while (reader.next("event")) {
                    events.add(Events.newEvent(
                            EventLevel.get(reader.readInt("@level")),
                            new Date(reader.readLong("@date")),
                            reader.readString("@source"),
                            reader.readString("@title"),
                            reader.readString("@details"),
                            name));
                }

                logs.put(name, events);
            }
        } catch (XMLException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        } finally {
            FileUtils.close(reader);
        }
    }

    /**
     * Create an empty event file.
     *
     * @param f The file.
     */
    private static void createEmptyEventFile(File f) {
        XMLWriter<Node> writer = XML.newJavaFactory().newWriter("logs");

        writer.write(f.getAbsolutePath());
    }

    /**
     * Save the events to XML.
     */
    @PreDestroy
    public void saveXML() {
        XMLWriter<Node> writer = XML.newJavaFactory().newWriter("logs");

        for (Map.Entry<String, Collection<Event>> entry : logs.entrySet()) {
            writer.add("log");

            writer.addAttribute("name", entry.getKey());

            writeEvents(writer, entry.getValue());

            writer.switchToParent();
        }

        writer.write(SystemProperty.USER_DIR.get() + "/logs.xml");
    }

    /**
     * Write the events to the XML file.
     *
     * @param writer The writer to use.
     * @param events The logs to write.
     */
    private static void writeEvents(XMLWriter<Node> writer, Iterable<Event> events) {
        for (Event event : events) {
            writer.add("event");

            writer.addAttribute("level", Integer.toString(event.getLevel().intValue()));
            writer.addAttribute("date", Long.toString(event.getDate().getTime()));
            writer.addAttribute("source", event.getSource());
            writer.addAttribute("title", event.getTitleKey());

            if (StringUtils.isNotEmpty(event.getDetailsKey())) {
                writer.addAttribute("details", event.getDetailsKey());
            }

            writer.switchToParent();
        }
    }
}