package org.jtheque.views.impl.windows;

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

import org.jtheque.core.utils.SimplePropertiesCache;
import org.jtheque.errors.JThequeError;
import org.jtheque.i18n.ILanguageService;
import org.jtheque.ui.able.IModel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.windows.dialogs.SwingFilthyBuildedDialogView;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.able.IViews;
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

    @Override
    protected void initView(){
        setTitleKey("config.view.title");
        setResizable(false);
    }

    @Override
    protected void buildView(I18nPanelBuilder builder){
        ILanguageService languageService = getService(ILanguageService.class);

        tab = new LayerTabbedPane(languageService);

        for (ConfigTabComponent component : getService(IViews.class).getConfigTabComponents()) {
            tab.addLayeredTab(languageService.getMessage(component.getTitleKey()), component.getComponent());
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

            tab.addLayeredTab(getService(ILanguageService.class).getMessage(component.getTitleKey()), component.getComponent());
        } else if("add".equals(message)){
            ConfigTabComponent component = (ConfigTabComponent)value;

            tab.removeTabAt(tab.indexOfTab(getService(ILanguageService.class).getMessage(component.getTitleKey())));
        }
    }

    @Override
    protected void validate(Collection<JThequeError> errors){
        getSelectedPanelConfig().validate(errors);
    }
}