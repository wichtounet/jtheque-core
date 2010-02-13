package org.jtheque.core.managers.view.impl.components.panel;

import org.jtheque.core.managers.update.Updatable;
import org.jtheque.core.managers.view.able.components.IUpdatablesPanelView;
import org.jtheque.core.managers.view.impl.actions.module.UpdateUpdatableAction;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyBackgroundPanel;
import org.jtheque.core.managers.view.impl.components.model.UpdatableListModel;
import org.jtheque.core.managers.view.impl.components.renderers.UpdatableListRenderer;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.core.utils.ui.builders.FilthyPanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.utils.ui.GridBagUtils;

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
    public UpdatablesPanel(){
        super();

        PanelBuilder builder = new FilthyPanelBuilder(this);

        updatablesList = builder.addScrolledList(new UpdatableListModel(), new UpdatableListRenderer(),
                builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));
        updatablesList.setBorder(Borders.EMPTY_BORDER);

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START), new UpdateUpdatableAction());
    }

    @Override
    public Updatable getSelectedUpdatable() {
        return (Updatable) updatablesList.getSelectedValue();
    }
}