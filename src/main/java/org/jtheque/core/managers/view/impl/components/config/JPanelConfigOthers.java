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
import org.jtheque.core.managers.view.able.config.IOthersConfigView;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyBackgroundPanel;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyTextField;
import org.jtheque.core.utils.ui.builders.FilthyPanelBuilder;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import java.util.Collection;

/**
 * Configuration panel for the others options.
 *
 * @author Baptiste Wicht
 */
public final class JPanelConfigOthers extends FilthyBackgroundPanel implements IOthersConfigView, ConfigTabComponent {
    private JCheckBox boxDeleteLogs;
    private JCheckBox checkBoxStart;
    private FilthyTextField fieldEmail;
    private FilthyTextField fieldSmtpHost;

    /**
     * Construct a new JPanelConfigOthers.
     */
    public JPanelConfigOthers() {
        super();

        build();

        fillAllFields();
    }

    @Override
    public String getTitle() {
        return Managers.getManager(ILanguageManager.class).getMessage("config.view.tab.others");
    }

    /**
     * Build the view.
     */
    private void build() {
        I18nPanelBuilder builder = new FilthyPanelBuilder(this);

        addLogsPanel(builder);
        addMailPanel(builder);

        checkBoxStart = builder.addI18nCheckBox("update.view.verify", builder.gbcSet(0, 2));
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
        builder.addI18nLabel("config.others.mail.smtpHost", builder.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_TRAILING));

        fieldEmail = builder.add(new FilthyTextField(10), builder.gbcSet(1, 0, GridBagUtils.HORIZONTAL));
        fieldSmtpHost = builder.add(new FilthyTextField(10), builder.gbcSet(1, 1, GridBagUtils.HORIZONTAL));
    }

    /**
     * Fill all the fields with the current informations.
     */
    private void fillAllFields() {
        checkBoxStart.setSelected(Managers.getCore().getConfiguration().verifyUpdateOnStartup());
        boxDeleteLogs.setSelected(Managers.getCore().getConfiguration().mustDeleteLogs());
        fieldEmail.setText(Managers.getCore().getConfiguration().getUserEmail());
        fieldSmtpHost.setText(Managers.getCore().getConfiguration().getSmtpHost());
    }

    @Override
    public void apply() {
        Managers.getCore().getConfiguration().setVerifyUpdateOnStartup(checkBoxStart.isSelected());
        Managers.getCore().getConfiguration().setMustDeleteLogs(boxDeleteLogs.isSelected());
        Managers.getCore().getConfiguration().setUserEmail(fieldEmail.getText());
        Managers.getCore().getConfiguration().setSmtpHost(fieldSmtpHost.getText());
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