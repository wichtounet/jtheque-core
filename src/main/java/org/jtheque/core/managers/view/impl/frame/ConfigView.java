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
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingFilthyBuildedDialogView;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.utils.ui.GridBagUtils;

import java.util.Collection;

/**
 * A view for the configuration.
 *
 * @author Baptiste Wicht
 */
public final class ConfigView extends SwingFilthyBuildedDialogView<IModel> implements IConfigView {
    private LayerTabbedPane tab;

    /**
     * Construct the ConfigView. 
     */
    public ConfigView(){
        super();

        build();
    }

    @Override
    protected void initView(){
        setTitleKey("config.view.title");
        setResizable(false);
    }

    @Override
    protected void buildView(I18nPanelBuilder builder){
        tab = new LayerTabbedPane();

        for (ConfigTabComponent component : getManager().getConfigTabComponents()) {
            tab.addLayeredTab(component.getTitle(), component.getComponent());
        }

        builder.add(tab, builder.gbcSet(0, 0, GridBagUtils.BOTH));

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL),
                new ApplyChangesAndCloseAction(), new ApplyChangesAction(), new CancelChangesAction());
    }

    @Override
    public ConfigTabComponent getSelectedPanelConfig() {
        return (ConfigTabComponent) tab.getSelectedComponent();
    }

    @Override
    public void sendMessage(String message, Object value) {
        if("remove".equals(message)){
            ConfigTabComponent component = (ConfigTabComponent)value;

            tab.addLayeredTab(component.getTitle(), component.getComponent());
        } else if("add".equals(message)){
            ConfigTabComponent component = (ConfigTabComponent)value;

            tab.removeTabAt(tab.indexOfTab(component.getTitle()));
        }
    }

    @Override
    protected void validate(Collection<JThequeError> errors){
        getSelectedPanelConfig().validate(errors);
    }
}