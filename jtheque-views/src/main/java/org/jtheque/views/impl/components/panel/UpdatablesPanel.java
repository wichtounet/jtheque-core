package org.jtheque.views.impl.components.panel;

import org.jtheque.ui.utils.components.Borders;
import org.jtheque.ui.utils.builders.FilthyPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.ui.utils.filthy.FilthyBackgroundPanel;
import org.jtheque.update.IUpdateService;
import org.jtheque.update.Updatable;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.able.components.IUpdatablesPanelView;
import org.jtheque.views.impl.actions.module.UpdateUpdatableAction;
import org.jtheque.views.impl.components.renderers.UpdatableListRenderer;
import org.jtheque.views.impl.models.UpdatableListModel;

import javax.swing.JList;

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
 * An updatable panel view implementation.
 *
 * @author Baptiste Wicht
 */
public final class UpdatablesPanel extends FilthyBackgroundPanel implements IUpdatablesPanelView {
    private final JList updatablesList;

    /**
     * Construct a new UpdatablesPanel. 
     */
    public UpdatablesPanel(IUpdateService updateService){
        super();

        PanelBuilder builder = new FilthyPanelBuilder(this);

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