package org.jtheque.events.able;

import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.annotations.ThreadSafe;

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
 * A simple factory to create Event. All the created events are immutable.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public final class Events {
    /**
     * Utility class, not instantiable.
     */
    private Events() {
        throw new AssertionError();
    }

    /**
     * Construct a new Event of level INFO. 
     *
     * @param source   The source of the event.
     * @param titleKey The internationalization key of the title.
     * @param log      The event log
     *
     * @return The created event.
     */
    public static Event newInfoEvent(String source, String titleKey, String log) {
        return newEvent(EventLevel.INFO, new Date(), source, titleKey, null, log);
    }

    /**
     * Construct a new Event.
     *
     * @param level    The level of the event.
     * @param source   The source of the event.
     * @param titleKey The internationalization key of the title.
     * @param log      The event log
     *
     * @return The created event.
     */
    public static Event newEvent(EventLevel level, String source, String titleKey, String log) {
        return newEvent(level, new Date(), source, titleKey, null, log);
    }

    /**
     * Construct a new Event.
     *
     * @param level    The level of the event.
     * @param date     The date of the event.
     * @param source   The source of the event.
     * @param titleKey The internationalization key of the title.
     * @param log      The event log
     *
     * @return The created event.
     */
    public static Event newEvent(EventLevel level, Date date, String source, String titleKey, String log) {
        return newEvent(level, date, source, titleKey, null, log);
    }

    /**
     * Construct a new Event.
     *
     * @param level      The level of the event.
     * @param date       The date of the event.
     * @param source     The source of the event.
     * @param titleKey   The internationalization key of the title.
     * @param detailsKey The internationalization key of the details.
     * @param log        The event log
     *
     * @return The created event.
     */
    public static Event newEvent(EventLevel level, Date date, String source, String titleKey, String detailsKey, String log) {
        return new SimpleEventImpl(level, date, source, titleKey, detailsKey, log);
    }

    /**
     * An event.
     *
     * @author Baptiste Wicht
     */
    @Immutable
    private static final class SimpleEventImpl implements Event {
        private final EventLevel level;
        private final Date date;
        private final String source;
        private final String titleKey;
        private final String detailsKey;
        private final String log;

        /**
         * Construct a new Event.
         *
         * @param level      The level of the event.
         * @param date       The date of the event.
         * @param source     The source of the event.
         * @param titleKey   The internationalization key of the title.
         * @param detailsKey The internationalization key of the details.
         * @param log        The event log.
         */
        private SimpleEventImpl(EventLevel level, Date date, String source, String titleKey, String detailsKey, String log) {
            super();

            this.level = level;
            this.log = log;
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
        public String getDetailsKey() {
            return detailsKey;
        }

        @Override
        public EventLevel getLevel() {
            return level;
        }

        @Override
        public Date getDate() {
            return new Date(date.getTime());
        }

        @Override
        public String getSource() {
            return source;
        }

        @Override
        public String getTitleKey() {
            return titleKey;
        }
    }
}