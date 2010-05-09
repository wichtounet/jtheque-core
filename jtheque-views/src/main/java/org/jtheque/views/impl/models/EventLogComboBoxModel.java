package org.jtheque.views.impl.models;

import org.jtheque.events.IEventService;
import org.jtheque.utils.collections.CollectionUtils;

import javax.swing.DefaultComboBoxModel;
import java.util.List;

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
 * A combo box model to displays the event logs.
 *
 * @author Baptiste Wicht
 */
public final class EventLogComboBoxModel extends DefaultComboBoxModel {
    private final List<String> logs;

    /**
     * Construct a new EventLogComboBoxModel.
     */
    public EventLogComboBoxModel(IEventService eventService) {
        super();

        logs = CollectionUtils.copyOf(eventService.getEventLogs());
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