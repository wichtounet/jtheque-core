package org.jtheque.views.impl.models;

import org.jtheque.events.IEventService;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.views.ViewsServices;

import javax.swing.DefaultComboBoxModel;
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
 * A combo box model to displays the event logs.
 *
 * @author Baptiste Wicht
 */
public final class LogComboBoxModel extends DefaultComboBoxModel {
    private final List<String> logs;

    /**
     * Construct a new LogComboBoxModel.
     */
    public LogComboBoxModel() {
        super();

        logs = CollectionUtils.copyOf(ViewsServices.get(IEventService.class).getLogs());
    }

    @Override
    public Object getElementAt(int index) {
        return logs.get(index);
    }

    @Override
    public int getSize() {
        return logs.size();
    }

}