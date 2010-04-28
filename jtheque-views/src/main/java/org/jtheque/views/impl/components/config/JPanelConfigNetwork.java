package org.jtheque.views.impl.components.config;

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

import org.jtheque.core.CoreConfiguration;
import org.jtheque.core.ICore;
import org.jtheque.errors.JThequeError;
import org.jtheque.i18n.ILanguageService;
import org.jtheque.ui.utils.ValidationUtils;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.filthy.FilthyBuildedPanel;
import org.jtheque.ui.utils.filthy.IFilthyUtils;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.able.components.ConfigTabComponent;
import org.jtheque.views.able.config.INetworkConfigView;
import org.jtheque.views.impl.actions.config.CheckProxyAction;
import org.jtheque.views.impl.filthy.FilthyTextField;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import java.util.Collection;

/**
 * A configuration panel for the network.
 *
 * @author Baptiste Wicht
 */
public final class JPanelConfigNetwork extends FilthyBuildedPanel implements INetworkConfigView, ConfigTabComponent {
    private JCheckBox boxProxy;
    private FilthyTextField fieldAddress;
    private FilthyTextField fieldPort;

    private final ICore core;

    public JPanelConfigNetwork(IFilthyUtils filthyUtils, ILanguageService languageService, ICore core) {
        super(filthyUtils, languageService);

        this.core = core;

        build();
    }

    @Override
    protected void buildView(I18nPanelBuilder parent) {
        I18nPanelBuilder builder = parent.addPanel(parent.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START));
        builder.setI18nTitleBorder("config.network.proxy.title");

        boxProxy = builder.addI18nCheckBox("config.network.proxy.check",
                parent.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 2, 1));
        boxProxy.addActionListener(new CheckProxyAction());

        builder.addI18nLabel("config.network.proxy.address", parent.gbcSet(0, 1));
        fieldAddress = builder.add(new FilthyTextField(10), parent.gbcSet(1, 1, GridBagUtils.HORIZONTAL));

        builder.addI18nLabel("config.network.proxy.port", parent.gbcSet(0, 2));
        fieldPort = builder.add(new FilthyTextField(10), parent.gbcSet(1, 2, GridBagUtils.HORIZONTAL));

        fillAllFields();
    }

    /**
     * Fill all the fields with the current informations.
     */
    private void fillAllFields() {
        CoreConfiguration config = core.getConfiguration();

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
        CoreConfiguration config = core.getConfiguration();

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
    public void validate(Collection<JThequeError> errors) {
        if (boxProxy.isSelected()) {
            ValidationUtils.rejectIfEmpty(fieldAddress.getText(), "config.network.proxy.address", errors);
            ValidationUtils.rejectIfEmpty(fieldPort.getText(), "config.network.proxy.port", errors);
        }
    }

    @Override
    public JComponent getComponent() {
        return this;
    }
}