package org.jtheque.views.impl.models;

import org.jtheque.events.able.IEvent;
import org.jtheque.events.able.IEventService;
import org.jtheque.i18n.able.ILanguageService;

import javax.swing.table.AbstractTableModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
 * A table model for the events.
 *
 * @author Baptiste Wicht
 */
public final class EventsTableModel extends AbstractTableModel {
    private final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    private final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss,SSS", Locale.getDefault());

    private String log;

    private final IEventService eventService;
    private final ILanguageService languageService;

    /**
     * The different columns of the films to buy table.
     *
     * @author Baptiste Wicht
     */
    private interface Columns {
        int LEVEL = 0;
        int DATE = 1;
        int TIME = 2;
        int SOURCE = 3;
        int TITLE = 4;
    }

    /**
     * Headers of the table.
     */
    private String[] headers;

    /**
     * The films to buy to display.
     */
    private List<IEvent> events;

    /**
     * Construct a new <code>FilmsToBuyTableModel</code>.
     *
     * @param eventService    The event service.
     * @param languageService The language service.
     */
    public EventsTableModel(IEventService eventService, ILanguageService languageService) {
        super();

        this.eventService = eventService;
        this.languageService = languageService;

        setLog(eventService.getEventLogs().iterator().next());
    }

    /**
     * Return the event log at the index.
     *
     * @param row The index of the event to get.
     *
     * @return The event level at the position or null if there is no event at this position.
     */
    public IEvent getValueAt(int row) {
        return events.get(row);
    }

    /**
     * Set the header of the table.
     *
     * @param headers The header of the table model
     */
    public void setHeaders(String[] headers) {
        this.headers = headers.clone();

        fireTableStructureChanged();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public int getRowCount() {
        return events.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        IEvent event = events.get(rowIndex);

        if (event != null) {
            switch (columnIndex) {
                case Columns.LEVEL:
                    return languageService.getMessage(event.getLevel().getKey());
                case Columns.DATE:
                    return dateFormat.format(event.getDate());
                case Columns.TIME:
                    return timeFormat.format(event.getDate());
                case Columns.SOURCE:
                    return event.getSource();
                case Columns.TITLE:
                    return languageService.getMessage(event.getTitleKey());
                default:
                    return "";
            }
        }

        return "";
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    /**
     * Set the event log to display.
     *
     * @param log The event log to display.
     */
    public void setLog(String log) {
        if (this.log == null || !this.log.equals(log)) {
            events = new ArrayList<IEvent>(eventService.getEvents(log));

            fireTableDataChanged();

            this.log = log;
        }
    }
}