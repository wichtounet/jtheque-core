package org.jtheque.ui.impl.components;

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

import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.i18n.able.Internationalizable;
import org.jtheque.ui.able.components.FileChooser;
import org.jtheque.ui.utils.builders.JThequePanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * A panel with a label, a text field and a button to choose a file.
 *
 * @author Baptiste Wicht
 */
public final class FileChooserPanel extends FileChooser implements Internationalizable {
    private JTextField fieldFilePath;
    private JLabel label;
    private JButton button;
    private SimpleFilter filter;

    private boolean directoriesOnly;

    private String key;

    /**
     * Construct a new FileChooserPanel.
     */
    public FileChooserPanel() {
        super();

        build();
    }

    /**
     * Build the panel.
     */
    private void build() {
        PanelBuilder builder = new JThequePanelBuilder(this);

        builder.setDefaultInsets(new Insets(0, 0, 0, 0));

        label = builder.add(new JLabel(),
                builder.gbcSet(0, 0, GridBagConstraints.NONE, GridBagConstraints.ABOVE_BASELINE_LEADING, 1, 0));

        JComponent panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEtchedBorder(1));

        fieldFilePath = new JTextField(15);
        Insets insets = fieldFilePath.getMargin();
        fieldFilePath.setBorder(BorderFactory.createEmptyBorder(insets.top, insets.left, insets.bottom, insets.right));
        panel.add(fieldFilePath, BorderLayout.CENTER);

        addBrowseButton(panel);

        builder.setDefaultInsets(new Insets(0, 5, 0, 5));

        builder.add(panel, builder.gbcSet(1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.ABOVE_BASELINE_LEADING, 0, 0, 1.0, 1.0));
    }

    /**
     * Add the browse button.
     *
     * @param panel The parent panel.
     */
    private void addBrowseButton(Container panel) {
        button = new JButton("...");

        button.setMargin(new Insets(0, 0, 0, 0));
        button.setDefaultCapable(false);
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.addActionListener(new BrowseAction());

        panel.add(button, BorderLayout.EAST);
    }

    @Override
    public void setText(String text) {
        label.setText(text);
    }

    @Override
    public void setTextKey(String key) {
        this.key = key;
    }

    @Override
    public String getFilePath() {
        return fieldFilePath.getText();
    }

    @Override
    public void setFilePath(String path) {
        fieldFilePath.setText(path);
    }

    @Override
    public void setFileFilter(SimpleFilter filter) {
        this.filter = filter;
    }

    @Override
    public void setDirectoriesOnly() {
        directoriesOnly = true;
    }

    @Override
    public void setFilesOnly() {
        directoriesOnly = false;
    }

    @Override
    public JTextField getTextField() {
        return fieldFilePath;
    }

    @Override
    public void setEnabled(boolean enable) {
        super.setEnabled(enable);

        fieldFilePath.setEnabled(enable);
        button.setEnabled(enable);
    }

    @Override
    public void refreshText(ILanguageService languageService) {
        if (key != null) {
            setText(languageService.getMessage(key));
        }
    }

    /**
     * A Browse action.
     *
     * @author Baptiste Wicht
     */
    private final class BrowseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            File file = directoriesOnly ? SwingUtils.chooseDirectory() : SwingUtils.chooseFile(filter);

            if (file != null) {
                fieldFilePath.setText(file.getAbsolutePath());
            }
        }
    }
}