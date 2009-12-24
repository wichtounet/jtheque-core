package org.jtheque.core.managers.view.impl.components.model;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.event.EventLog;
import org.jtheque.core.managers.event.IEventManager;
import org.jtheque.core.managers.language.ILanguageManager;

import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
    private final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Managers.getManager(ILanguageManager.class).getCurrentLocale());
    private final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss,SSS", Managers.getManager(ILanguageManager.class).getCurrentLocale());

    private String log;

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
    private List<EventLog> events;

    /**
     * Construct a new <code>FilmsToBuyTableModel</code>.
     */
    public EventsTableModel() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        setLog(Managers.getManager(IEventManager.class).getLogs().iterator().next());
    }

    /**
     * Return the event log at the index.
     *
     * @param row The index of the event to get.
     * @return The event level at the position or null if there is no event at this position.
     */
    public EventLog getValueAt(int row) {
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
        EventLog eventLog = events.get(rowIndex);

        if (eventLog != null) {
            switch (columnIndex) {
                case Columns.LEVEL:
                    return Managers.getManager(ILanguageManager.class).getMessage(eventLog.getLevel().getKey());
                case Columns.DATE:
                    return dateFormat.format(eventLog.getDate());
                case Columns.TIME:
                    return timeFormat.format(eventLog.getDate());
                case Columns.SOURCE:
                    return eventLog.getSource();
                case Columns.TITLE:
                    return Managers.getManager(ILanguageManager.class).getMessage(eventLog.getTitleKey());
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
            events = new ArrayList<EventLog>(Managers.getManager(IEventManager.class).getEventLogs(log));

            fireTableDataChanged();

            this.log = log;
        }
    }
}