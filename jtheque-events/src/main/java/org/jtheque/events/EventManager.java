package org.jtheque.events;

import org.jdom.Element;
import org.jtheque.core.ICore;
import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.io.XMLException;
import org.jtheque.io.XMLReader;
import org.jtheque.io.XMLWriter;
import org.jtheque.logging.ILoggingManager;
import org.jtheque.utils.io.FileUtils;
import org.osgi.framework.BundleContext;
import org.springframework.osgi.context.BundleContextAware;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
 * An event manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class EventManager implements IEventManager, BundleContextAware {
    private final Map<String, Collection<EventLog>> logs = new HashMap<String, Collection<EventLog>>(10);

    private BundleContext bundleContext;

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public Set<String> getLogs() {
        return logs.keySet();
    }

    @Override
    public Collection<EventLog> getEventLogs(String log) {
        return logs.get(log);
    }

    @Override
    public void addEventLog(String log, EventLog event) {
        if (!logs.containsKey(log)) {
            logs.put(log, new ArrayList<EventLog>(25));
        }

        event.setLog(log);

        logs.get(log).add(event);
    }

    /**
     * Import from XML.
     */
    public void importFromXML() {
        File f = new File(OSGiUtils.getService(bundleContext, ICore.class).getFolders().getApplicationFolder(), "/core/logs.xml");

        if (!f.exists()) {
            createEmptyEventFile(f);
        }

        XMLReader reader = new XMLReader();

        try {
            reader.openFile(f);

            for (Object currentNode : reader.getNodes("log", reader.getRootElement())) {
                String name = reader.readString("@name", currentNode);

                Collection<Element> elements = reader.getNodes("event", currentNode);

                logs.put(name, new ArrayList<EventLog>(elements.size()));

                for (Element element : elements) {
                    EventLog log = readLog(reader, name, element);

                    logs.get(name).add(log);
                }
            }
        } catch (XMLException e) {
            OSGiUtils.getService(bundleContext, ILoggingManager.class).getLogger(getClass()).error(e);
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
        XMLWriter writer = new XMLWriter("logs");

        writer.write(f.getAbsolutePath());
    }

    /**
     * Read the log from the XML.
     *
     * @param reader  The reader to use.
     * @param name    The name of the log.
     * @param element The element to read the log from.
     * @return The EventLog.
     * @throws XMLException If an error occurs during the xml reading.
     */
    private static EventLog readLog(XMLReader reader, String name, Object element) throws XMLException {
        EventLog log = new EventLog(
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
    public void saveXML() {
        XMLWriter writer = new XMLWriter("logs");

        for (Map.Entry<String, Collection<EventLog>> entry : logs.entrySet()) {
            writer.add("log");

            writer.addAttribute("name", entry.getKey());

            writeEvents(writer, entry.getValue());

            writer.switchToParent();
        }

        writer.write(OSGiUtils.getService(bundleContext, ICore.class).getFolders().getApplicationFolder().getAbsolutePath() + "/core/logs.xml");
    }

    /**
     * Write the events to the XML file.
     *
     * @param writer The writer to use.
     * @param logs   The logs to write.
     */
    private static void writeEvents(XMLWriter writer, Iterable<EventLog> logs) {
        for (EventLog log : logs) {
            writer.add("event");

            writer.addOnly("level", Integer.toString(log.getLevel().intValue()));
            writer.addOnly("date", Long.toString(log.getDate().getTime()));
            writer.addOnly("source", log.getSource());
            writer.addOnly("title", log.getTitleKey());

            writer.switchToParent();
        }
    }
}