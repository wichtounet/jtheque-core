package org.jtheque.events;

import java.util.Date;

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
 * An event log.
 *
 * @author Baptiste Wicht
 */
public final class EventLog {
    private final EventLevel level;
    private final Date date;
    private final String source;
    private final String titleKey;
    private String log;
    private String detailsKey;

    /**
     * Construct a new EventLog.
     *
     * @param level    The level of the event.
     * @param source   The source of the event.
     * @param titleKey The internationalization key of the title.
     */
    public EventLog(EventLevel level, String source, String titleKey) {
        this(level, new Date(), source, titleKey);
    }

    /**
     * Construct a new EventLog.
     *
     * @param level    The level of the event.
     * @param date     The date of the event.
     * @param source   The source of the event.
     * @param titleKey The internationalization key of the title.
     */
    public EventLog(EventLevel level, Date date, String source, String titleKey) {
        super();

        this.level = level;
        this.date = new Date(date.getTime());
        this.source = source;
        this.titleKey = titleKey;
    }

    /**
     * Return the log of the event.
     *
     * @return The log of the event.
     */
    public String getLog() {
        return log;
    }

    /**
     * Set the log of the event.
     *
     * @param log The log of the event.
     */
    public void setLog(String log) {
        this.log = log;
    }

    /**
     * Return the details internationalization key.
     *
     * @return The details internationalization key.
     */
    public String getDetailsKey() {
        return detailsKey;
    }

    /**
     * Set the details internationalization key.
     *
     * @param detailsKey The details internationalization key.
     */
    public void setDetailsKey(String detailsKey) {
        this.detailsKey = detailsKey;
    }

    /**
     * Return the level of the event.
     *
     * @return The level of the event.
     */
    public EventLevel getLevel() {
        return level;
    }

    /**
     * Return the date of the event.
     *
     * @return The date of the event.
     */
    public Date getDate() {
        return (Date) date.clone();
    }

    /**
     * Return the source of the event.
     *
     * @return The source of the event.
     */
    public String getSource() {
        return source;
    }

    /**
     * Return the title internationalization key.
     *
     * @return The title internationalization key.
     */
    public String getTitleKey() {
        return titleKey;
    }
}