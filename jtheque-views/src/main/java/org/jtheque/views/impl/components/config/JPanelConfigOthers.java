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

import org.jtheque.core.CoreConfiguration;
import org.jtheque.core.ICore;
import org.jtheque.errors.JThequeError;
import org.jtheque.i18n.ILanguageService;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.filthy.FilthyBuildedPanel;
import org.jtheque.ui.utils.filthy.IFilthyUtils;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.able.components.ConfigTabComponent;
import org.jtheque.views.able.config.IOthersConfigView;
import org.jtheque.views.impl.filthy.FilthyTextField;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import java.util.Collection;

/**
 * Configuration panel for the others options.
 *
 * @author Baptiste Wicht
 */
public final class JPanelConfigOthers extends FilthyBuildedPanel implements IOthersConfigView, ConfigTabComponent {
    private JCheckBox boxDeleteLogs;
    private JCheckBox checkBoxStart;
    private FilthyTextField fieldEmail;
    private FilthyTextField fieldSmtpHost;

    private final ICore core;

    public JPanelConfigOthers(IFilthyUtils filthyUtils, ILanguageService languageService, ICore core) {
        super(filthyUtils, languageService);

        this.core = core;

        build();
    }

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
        fieldEmail = builder.add(new FilthyTextField(10), builder.gbcSet(1, 0, GridBagUtils.HORIZONTAL));

        builder.addI18nLabel("config.others.mail.smtpHost", builder.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_TRAILING));
        fieldSmtpHost = builder.add(new FilthyTextField(10), builder.gbcSet(1, 1, GridBagUtils.HORIZONTAL));
    }

    /**
     * Fill all the fields with the current informations.
     */
    private void fillAllFields() {
        CoreConfiguration config = core.getConfiguration();

        checkBoxStart.setSelected(config.verifyUpdateOnStartup());
        boxDeleteLogs.setSelected(config.mustDeleteLogs());
        fieldEmail.setText(config.getUserEmail());
        fieldSmtpHost.setText(config.getSmtpHost());
    }

    @Override
    public void apply() {
        CoreConfiguration config = core.getConfiguration();

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
    public void validate(Collection<JThequeError> errors) {
        //Nothing to validate in the view
    }

    @Override
    public JComponent getComponent() {
        return this;
    }
}