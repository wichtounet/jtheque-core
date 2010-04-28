package org.jtheque.views.impl.components.panel;

import org.jtheque.i18n.ILanguageService;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.components.Borders;
import org.jtheque.ui.utils.filthy.FilthyBuildedPanel;
import org.jtheque.ui.utils.filthy.IFilthyUtils;
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
public final class UpdatablesPanel extends FilthyBuildedPanel implements IUpdatablesPanelView {
    private JList updatablesList;

    private final IUpdateService updateService;

    /**
     * Construct a new UpdatablesPanel. 
     */
    public UpdatablesPanel(IUpdateService updateService, ILanguageService languageService, IFilthyUtils utils){
        super(utils, languageService);

        this.updateService = updateService;

        build();
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
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