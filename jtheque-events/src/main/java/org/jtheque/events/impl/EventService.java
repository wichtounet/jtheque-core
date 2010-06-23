package org.jtheque.events.impl;

import org.jtheque.core.utils.SystemProperty;
import org.jtheque.events.able.EventLevel;
import org.jtheque.events.able.IEvent;
import org.jtheque.events.able.IEventService;
import org.jtheque.events.utils.Event;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.xml.utils.IXMLReader;
import org.jtheque.xml.utils.IXMLWriter;
import org.jtheque.xml.utils.XML;
import org.jtheque.xml.utils.XMLException;

import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import javax.annotation.PreDestroy;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
 * An event manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class EventService implements IEventService {
    private final Map<String, Collection<IEvent>> logs = new HashMap<String, Collection<IEvent>>(10);

    /**
     * Construct a new EventService.
     */
    public EventService() {
        super();

        importFromXML();
    }

    @Override
    public Set<String> getEventLogs() {
        return logs.keySet();
    }

    @Override
    public Collection<IEvent> getEvents(String log) {
        return logs.get(log);
    }

    @Override
    public void addEvent(String log, IEvent event) {
        if (!logs.containsKey(log)) {
            logs.put(log, new ArrayList<IEvent>(25));
        }

        event.setLog(log);

        logs.get(log).add(event);
    }

    /**
     * Save the XML before stop modules.
     */
    @PreDestroy
    public void release() {
        saveXML();
    }

    /**
     * Import from XML.
     */
    private void importFromXML() {
        File f = new File(SystemProperty.USER_DIR.get(), "/logs.xml");

        if (!f.exists()) {
            createEmptyEventFile(f);
        }

        IXMLReader<Node> reader = XML.newJavaFactory().newReader();

        try {
            reader.openFile(f);

            for (Object currentNode : reader.getNodes("log", reader.getRootElement())) {
                String name = reader.readString("@name", currentNode);

                Collection<Node> elements = reader.getNodes("event", currentNode);

                logs.put(name, new ArrayList<IEvent>(elements.size()));

                for (Node element : elements) {
                    Event log = readLog(reader, name, element);

                    logs.get(name).add(log);
                }
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
        IXMLWriter<Node> writer = XML.newJavaFactory().newWriter("logs");

        writer.write(f.getAbsolutePath());
    }

    /**
     * Read the log from the XML.
     *
     * @param reader  The reader to use.
     * @param name    The name of the log.
     * @param element The element to read the log from.
     *
     * @return The Event.
     *
     * @throws XMLException If an error occurs during the xml reading.
     */
    private static Event readLog(IXMLReader<Node> reader, String name, Object element) throws XMLException {
        Event log = new Event(
                EventLevel.get(reader.readInt("level", element)),
                new Date(reader.readLong("date", element)),
                reader.readString("source", element),
                reader.readString("title", element));

        log.setDetailsKey(reader.readString("details", element));
        log.setLog(name);

        return log;
    }

    /**
     * Save the events to XML.
     */
    private void saveXML() {
        IXMLWriter<Node> writer = XML.newJavaFactory().newWriter("logs");

        for (Map.Entry<String, Collection<IEvent>> entry : logs.entrySet()) {
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
     * @param logs   The logs to write.
     */
    private static void writeEvents(IXMLWriter<Node> writer, Iterable<IEvent> logs) {
        for (IEvent log : logs) {
            writer.add("event");

            writer.addOnly("level", Integer.toString(log.getLevel().intValue()));
            writer.addOnly("date", Long.toString(log.getDate().getTime()));
            writer.addOnly("source", log.getSource());
            writer.addOnly("title", log.getTitleKey());

            writer.switchToParent();
        }
    }
}