package org.jtheque.views.impl.models;

import org.jtheque.events.Event;
import org.jtheque.events.IEventService;
import org.jtheque.i18n.ILanguageService;

import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private List<Event> events;

    /**
     * Construct a new <code>FilmsToBuyTableModel</code>.
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
     * @return The event level at the position or null if there is no event at this position.
     */
    public Event getValueAt(int row) {
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
        Event event = events.get(rowIndex);

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
            events = new ArrayList<Event>(eventService.getEvents(log));

            fireTableDataChanged();

            this.log = log;
        }
    }
}