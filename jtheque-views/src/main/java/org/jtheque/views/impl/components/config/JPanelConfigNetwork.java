package org.jtheque.views.impl.components.config;

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

import org.jtheque.core.able.Core;
import org.jtheque.core.able.CoreConfiguration;
import org.jtheque.ui.able.components.TextField;
import org.jtheque.ui.able.components.filthy.Filthy;
import org.jtheque.ui.able.constraints.Constraint;
import org.jtheque.ui.able.constraints.Constraints;
import org.jtheque.ui.utils.builded.OSGIFilthyBuildedPanel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.able.components.ConfigTabComponent;
import org.jtheque.views.able.config.NetworkConfigView;
import org.jtheque.views.impl.actions.config.CheckProxyAction;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import java.util.Map;

/**
 * A configuration panel for the network.
 *
 * @author Baptiste Wicht
 */
public final class JPanelConfigNetwork extends OSGIFilthyBuildedPanel implements NetworkConfigView, ConfigTabComponent {
    private JCheckBox boxProxy;
    private TextField fieldAddress;
    private TextField fieldPort;

    @Override
    protected void buildView(I18nPanelBuilder parent) {
        I18nPanelBuilder builder = parent.addPanel(parent.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START));
        builder.setI18nTitleBorder("config.network.proxy.title");

        boxProxy = builder.addI18nCheckBox("config.network.proxy.check",
                parent.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 2, 1));
        boxProxy.addActionListener(new CheckProxyAction(this));

        builder.addI18nLabel("config.network.proxy.address", parent.gbcSet(0, 1));
        fieldAddress = builder.add(Filthy.newTextField(10), parent.gbcSet(1, 1, GridBagUtils.HORIZONTAL));

        builder.addI18nLabel("config.network.proxy.port", parent.gbcSet(0, 2));
        fieldPort = builder.add(Filthy.newTextField(10), parent.gbcSet(1, 2, GridBagUtils.HORIZONTAL));

        fillAllFields();
    }

    /**
     * Fill all the fields with the current informations.
     */
    private void fillAllFields() {
        CoreConfiguration config = getService(Core.class).getConfiguration();

        boxProxy.setSelected(config.hasAProxy());
        fieldPort.setText(config.getProxyPort());
        fieldAddress.setText(config.getProxyAddress());
    }

    @Override
    public String getTitleKey() {
        return "config.view.tab.network";
    }

    @Override
    public void apply() {
        CoreConfiguration config = getService(Core.class).getConfiguration();

        config.setHasAProxy(boxProxy.isSelected());
        config.setProxyPort(fieldPort.getText());
        config.setProxyAddress(fieldAddress.getText());
    }

    @Override
    public void cancel() {
        fillAllFields();
    }

    @Override
    public JComponent getFieldPort() {
        return fieldPort;
    }

    @Override
    public JComponent getFieldAddress() {
        return fieldAddress;
    }

    @Override
    public JCheckBox getBoxProxy() {
        return boxProxy;
    }

    @Override
    public Map<Object, Constraint> getConstraints() {
        Map<Object, Constraint> constraints = CollectionUtils.newHashMap(1);

        constraints.put(fieldAddress, Constraints.notNull("config.network.proxy.address"));
        constraints.put(fieldPort, Constraints.notNull("config.network.proxy.port"));

        return constraints;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }
}