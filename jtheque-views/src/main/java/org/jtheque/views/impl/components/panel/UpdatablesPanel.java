package org.jtheque.views.impl.components.panel;

import org.jtheque.ui.utils.builded.OSGIFilthyBuildedPanel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.components.Borders;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.update.able.Updatable;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.able.components.IUpdatablesPanelView;
import org.jtheque.views.impl.actions.module.UpdateUpdatableAction;
import org.jtheque.views.impl.components.renderers.UpdatableListRenderer;
import org.jtheque.views.impl.models.UpdatableListModel;

import javax.swing.JList;

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
 * An updatable panel view implementation.
 *
 * @author Baptiste Wicht
 */
public final class UpdatablesPanel extends OSGIFilthyBuildedPanel implements IUpdatablesPanelView {
    private JList updatablesList;

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        IUpdateService updateService = getService(IUpdateService.class);

        updatablesList = builder.addScrolledList(new UpdatableListModel(updateService), new UpdatableListRenderer(updateService),
                builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));
        updatablesList.setBorder(Borders.EMPTY_BORDER);

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START), new UpdateUpdatableAction());
    }

    @Override
    public Updatable getSelectedUpdatable() {
        return (Updatable) updatablesList.getSelectedValue();
    }
}