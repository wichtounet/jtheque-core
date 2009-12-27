package org.jtheque.core.managers.view.impl.components.config;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.able.config.INetworkConfigView;
import org.jtheque.core.managers.view.impl.actions.config.CheckProxyAction;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Collection;

/**
 * A configuration panel for the network.
 *
 * @author Baptiste Wicht
 */
public final class JPanelConfigNetwork extends JPanel implements INetworkConfigView, ConfigTabComponent {
    private JCheckBox boxProxy;
    private JTextField fieldAddress;
    private JTextField fieldPort;

    /**
     * Construct a new JPanelConfigNetwork. 
     */
    public JPanelConfigNetwork(){
        super();

        addProxyPanel();

        fillAllFields();
    }

    /**
     * Add the proxy panel.
     */
    private void addProxyPanel() {
        PanelBuilder parent = new PanelBuilder(this);

        PanelBuilder builder = parent.addPanel(parent.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START));
        builder.getPanel().setBorder(Borders.createTitledBorder("config.network.proxy.title"));

        boxProxy = builder.addI18nCheckBox("config.network.proxy.check",
                parent.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 2, 1));
        boxProxy.addActionListener(new CheckProxyAction());

        builder.addI18nLabel("config.network.proxy.address", parent.gbcSet(0, 1));
        fieldAddress = builder.add(new JTextField(10), parent.gbcSet(1, 1, GridBagUtils.HORIZONTAL));

        builder.addI18nLabel("config.network.proxy.port", parent.gbcSet(0, 2));
        fieldPort = builder.add(new JTextField(10), parent.gbcSet(1, 2, GridBagUtils.HORIZONTAL));
    }

    /**
     * Fill all the fields with the current informations.
     */
    private void fillAllFields() {
        boxProxy.setSelected(Managers.getCore().getConfiguration().hasAProxy());
        fieldPort.setText(Managers.getCore().getConfiguration().getProxyPort());
        fieldAddress.setText(Managers.getCore().getConfiguration().getProxyAddress());
    }

    @Override
    public String getTitle() {
        return Managers.getManager(ILanguageManager.class).getMessage("config.view.tab.network");
    }

    @Override
    public void apply() {
        Managers.getCore().getConfiguration().setHasAProxy(boxProxy.isSelected());
        Managers.getCore().getConfiguration().setProxyPort(fieldPort.getText());
        Managers.getCore().getConfiguration().setProxyAddress(fieldAddress.getText());
    }

    @Override
    public void cancel() {
        fillAllFields();
    }

    @Override
    public JTextField getFieldPort() {
        return fieldPort;
    }

    @Override
    public JTextField getFieldAddress() {
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