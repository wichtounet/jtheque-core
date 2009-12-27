package org.jtheque.core.managers.view.impl.frame;

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

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.able.IConfigView;
import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.core.managers.view.impl.actions.config.ApplyChangesAction;
import org.jtheque.core.managers.view.impl.actions.config.ApplyChangesAndCloseAction;
import org.jtheque.core.managers.view.impl.actions.config.CancelChangesAction;
import org.jtheque.core.managers.view.impl.components.LayerTabbedPane;
import org.jtheque.core.managers.view.impl.components.config.ConfigTabComponent;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingBuildedDialogView;
import org.jtheque.core.managers.view.listeners.ConfigTabEvent;
import org.jtheque.core.managers.view.listeners.ConfigTabListener;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.utils.ui.GridBagUtils;

import java.util.Collection;

/**
 * A view for the configuration.
 *
 * @author Baptiste Wicht
 */
public final class ConfigView extends SwingBuildedDialogView<IModel> implements ConfigTabListener, IConfigView {
    private LayerTabbedPane tab;

    public ConfigView(){
        super();

        build();
    }

    @Override
    protected void initView(){
        setTitleKey("config.view.title");
    }

    @Override
    protected void buildView(PanelBuilder builder){
        tab = new LayerTabbedPane();

        for (ConfigTabComponent component : getManager().getConfigTabComponents()) {
            tab.addLayeredTab(component.getTitle(), component.getComponent());
        }

        getManager().addConfigTabListener(this);

        builder.add(tab, builder.gbcSet(0, 0, GridBagUtils.BOTH));

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL),
                new ApplyChangesAndCloseAction(), new ApplyChangesAction(), new CancelChangesAction());
    }

    @Override
    public ConfigTabComponent getSelectedPanelConfig() {
        return (ConfigTabComponent) tab.getSelectedComponent();
    }

    @Override
    public void tabAdded(ConfigTabEvent event) {
        tab.addLayeredTab(event.getComponent().getTitle(), event.getComponent().getComponent());
    }

    @Override
    public void tabRemoved(ConfigTabEvent event) {
        tab.removeTabAt(tab.indexOfTab(event.getComponent().getTitle()));
    }

    @Override
    protected void validate(Collection<JThequeError> errors){
        getSelectedPanelConfig().validate(errors);
    }
}