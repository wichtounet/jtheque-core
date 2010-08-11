package org.jtheque.views.impl.components.panel;

import org.jtheque.resources.Resource;
import org.jtheque.resources.ResourceService;
import org.jtheque.ui.utils.builded.OSGIFilthyBuildedPanel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.components.Borders;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.components.ResourcePanelView;
import org.jtheque.views.impl.components.renderers.ResourceListRenderer;
import org.jtheque.views.impl.models.ResourceListModel;

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
 * An resources panel view implementation.
 *
 * @author Baptiste Wicht
 */
public final class ResourcesPanel extends OSGIFilthyBuildedPanel implements ResourcePanelView {
    private JList resourcesList;

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        ResourceService resourceService = getService(ResourceService.class);

        resourcesList = builder.addScrolledList(new ResourceListModel(resourceService), new ResourceListRenderer(),
                builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));
        resourcesList.setBorder(Borders.EMPTY_BORDER);

        //builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START), new UpdateUpdatableAction());
    }

    @Override
    public Resource getSelectedResource() {
        return (Resource) resourcesList.getSelectedValue();
    }
}