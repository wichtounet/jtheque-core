package org.jtheque.events.utils;

import org.jtheque.events.able.EventLevel;
import org.jtheque.events.able.IEvent;

import java.util.Date;

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
 * An event.
 *
 * @author Baptiste Wicht
 */
public final class Event implements IEvent {
    private final EventLevel level;
    private final Date date;
    private final String source;
    private final String titleKey;
    private final String detailsKey;
    private String log;

    /**
     * Construct a new Event.
     *
     * @param level      The level of the event.
     * @param date       The date of the event.
     * @param source     The source of the event.
     * @param titleKey   The internationalization key of the title.
     * @param detailsKey The internationalization key of the details.
     */
    private Event(EventLevel level, Date date, String source, String titleKey, String detailsKey) {
        super();

        this.level = level;
        this.date = new Date(date.getTime());
        this.source = source;
        this.titleKey = titleKey;
        this.detailsKey = detailsKey;
    }

    @Override
    public String getLog() {
        return log;
    }

    @Override
    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public String getDetailsKey() {
        return detailsKey;
    }

    @Override
    public EventLevel getLevel() {
        return level;
    }

    @Override
    public Date getDate() {
        return (Date) date.clone();
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String getTitleKey() {
        return titleKey;
    }

    /**
     * Construct a new Event.
     *
     * @param level    The level of the event.
     * @param source   The source of the event.
     * @param titleKey The internationalization key of the title.
     *
     * @return The created event.
     */
    public static Event newEvent(EventLevel level, String source, String titleKey) {
        return newEvent(level, new Date(), source, titleKey, null);
    }

    /**
     * Construct a new Event.
     *
     * @param level    The level of the event.
     * @param date     The date of the event.
     * @param source   The source of the event.
     * @param titleKey The internationalization key of the title.
     *
     * @return The created event.
     */
    public static Event newEvent(EventLevel level, Date date, String source, String titleKey) {
        return newEvent(level, date, source, titleKey, null);
    }

    /**
     * Construct a new Event.
     *
     * @param level      The level of the event.
     * @param date       The date of the event.
     * @param source     The source of the event.
     * @param titleKey   The internationalization key of the title.
     * @param detailsKey The internationalization key of the details.
     *
     * @return The created event.
     */
    public static Event newEvent(EventLevel level, Date date, String source, String titleKey, String detailsKey) {
        return new Event(level, date, source, titleKey, detailsKey);
    }
}