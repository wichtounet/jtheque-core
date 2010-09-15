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

import org.jtheque.i18n.LanguageService;
import org.jtheque.ui.Model;
import org.jtheque.ui.components.LayerTabbedPane;
import org.jtheque.ui.constraints.Constraint;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.windows.dialogs.SwingFilthyBuildedDialogView;
import org.jtheque.utils.SimplePropertiesCache;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.Views;
import org.jtheque.views.components.ConfigTabComponent;

import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * A view for the configuration.
 *
 * @author Baptiste Wicht
 */
public final class ConfigView extends SwingFilthyBuildedDialogView<Model> implements org.jtheque.views.windows.ConfigView {
    private LayerTabbedPane tab;

    private final Set<ConfigTabComponent> components = CollectionUtils.newSet(5);

    @Override
    protected void initView() {
        setTitleKey("config.view.title");
        setResizable(false);
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        LanguageService languageService = getService(LanguageService.class);

        tab = new LayerTabbedPane(languageService);

        for (ConfigTabComponent component : getService(Views.class).getConfigTabComponents()) {
            tab.addLayeredTab(languageService.getMessage(component.getTitleKey()), component.getComponent());
            
            for (Map.Entry<Object, Constraint> constraint : component.getConstraints().entrySet()) {
                addConstraint(constraint.getKey(), constraint.getValue());
            }

            components.add(component);
        }

        builder.add(tab, builder.gbcSet(0, 0, GridBagUtils.BOTH));

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL),
                getAction("config.actions.ok"),
                getAction("config.actions.apply"),
                getAction("config.actions.cancel"));

        SimplePropertiesCache.put("config-view-loaded", true);
    }

    @Override
    public ConfigTabComponent getSelectedPanelConfig() {
        return (ConfigTabComponent) tab.getSelectedComponent();
    }

    @Override
    public void sendMessage(String message, Object value) {
        if(value == null){
            LoggerFactory.getLogger(getClass()).error("Null value was sent as message to config view");

            return;
        }

        if ("add".equals(message)) {
            ConfigTabComponent component = (ConfigTabComponent) value;

            if(components.contains(component)){
                return;
            }
            
            tab.addLayeredTab(getService(LanguageService.class).getMessage(component.getTitleKey()), component.getComponent());

            components.add(component);

            pack();
        } else if ("remove".equals(message)) {
            ConfigTabComponent component = (ConfigTabComponent) value;

            int index = tab.indexOfTab(getService(LanguageService.class).getMessage(component.getTitleKey()));

            if(index >= 0){
                tab.removeTabAt(index);
            }

            components.remove(component);

            pack();
        }
    }
}