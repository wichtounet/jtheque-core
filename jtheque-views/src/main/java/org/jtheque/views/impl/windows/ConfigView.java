package org.jtheque.views.impl.windows;

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

import org.jtheque.core.utils.SimplePropertiesCache;
import org.jtheque.errors.JThequeError;
import org.jtheque.ui.able.IModel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.windows.frames.SwingFilthyBuildedDialogView;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.ViewsServices;
import org.jtheque.views.able.IViewService;
import org.jtheque.views.able.components.ConfigTabComponent;
import org.jtheque.views.able.windows.IConfigView;
import org.jtheque.views.impl.actions.config.ApplyChangesAction;
import org.jtheque.views.impl.actions.config.ApplyChangesAndCloseAction;
import org.jtheque.views.impl.actions.config.CancelChangesAction;
import org.jtheque.views.impl.components.LayerTabbedPane;

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

        for (ConfigTabComponent component : ViewsServices.get(IViewService.class).getConfigTabComponents()) {
            tab.addLayeredTab(component.getTitle(), component.getComponent());
        }

        builder.add(tab, builder.gbcSet(0, 0, GridBagUtils.BOTH));

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL),
                new ApplyChangesAndCloseAction(), new ApplyChangesAction(), new CancelChangesAction());

        SimplePropertiesCache.put("config-view-loaded", "true");
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