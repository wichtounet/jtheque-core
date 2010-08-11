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

import org.jtheque.core.Core;
import org.jtheque.core.CoreConfiguration;
import org.jtheque.ui.components.TextField;
import org.jtheque.ui.components.filthy.Filthy;
import org.jtheque.ui.constraints.Constraint;
import org.jtheque.ui.utils.builded.OSGIFilthyBuildedPanel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.components.ConfigTabComponent;
import org.jtheque.views.config.OthersConfigView;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import java.util.Collections;
import java.util.Map;

/**
 * Configuration panel for the others options.
 *
 * @author Baptiste Wicht
 */
public final class JPanelConfigOthers extends OSGIFilthyBuildedPanel implements OthersConfigView, ConfigTabComponent {
    private JCheckBox boxDeleteLogs;
    private JCheckBox checkBoxStart;
    private TextField fieldEmail;
    private TextField fieldSmtpHost;

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        addLogsPanel(builder);
        addMailPanel(builder);

        checkBoxStart = builder.addI18nCheckBox("update.view.verify", builder.gbcSet(0, 2));

        fillAllFields();
    }

    @Override
    public String getTitleKey() {
        return "config.view.tab.others";
    }

    /**
     * Add the "logs" panel.
     *
     * @param parent The parent builder.
     */
    private void addLogsPanel(I18nPanelBuilder parent) {
        I18nPanelBuilder builder = parent.addPanel(parent.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START));
        builder.setI18nTitleBorder("config.others.logs.title");

        boxDeleteLogs = builder.addI18nCheckBox("config.others.logs.delete",
                builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 2, 1));

        builder.addI18nLabel("config.others.logs.days", builder.gbcSet(0, 1));
    }

    /**
     * Add the "mail" panel.
     *
     * @param parent The parent builder.
     */
    private void addMailPanel(I18nPanelBuilder parent) {
        I18nPanelBuilder builder = parent.addPanel(parent.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START));
        builder.setI18nTitleBorder("config.others.mail.title");

        builder.addI18nLabel("config.others.mail.userEmail", builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_TRAILING));
        fieldEmail = builder.add(Filthy.newTextField(10), builder.gbcSet(1, 0, GridBagUtils.HORIZONTAL));

        builder.addI18nLabel("config.others.mail.smtpHost", builder.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_TRAILING));
        fieldSmtpHost = builder.add(Filthy.newTextField(10), builder.gbcSet(1, 1, GridBagUtils.HORIZONTAL));
    }

    /**
     * Fill all the fields with the current informations.
     */
    private void fillAllFields() {
        CoreConfiguration config = getService(Core.class).getConfiguration();

        checkBoxStart.setSelected(config.verifyUpdateOnStartup());
        boxDeleteLogs.setSelected(config.mustDeleteLogs());
        fieldEmail.setText(config.getUserEmail());
        fieldSmtpHost.setText(config.getSmtpHost());
    }

    @Override
    public void apply() {
        CoreConfiguration config = getService(Core.class).getConfiguration();

        config.setVerifyUpdateOnStartup(checkBoxStart.isSelected());
        config.setMustDeleteLogs(boxDeleteLogs.isSelected());
        config.setUserEmail(fieldEmail.getText());
        config.setSmtpHost(fieldSmtpHost.getText());
    }

    @Override
    public void cancel() {
        fillAllFields();
    }

    @Override
    public JCheckBox getBoxDeleteLogs() {
        return boxDeleteLogs;
    }

    @Override
    public Map<Object, Constraint> getConstraints() {
        return Collections.emptyMap();
    }

    @Override
    public JComponent getComponent() {
        return this;
    }
}