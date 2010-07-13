package org.jtheque.ui.impl.components.filthy;

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
import org.jtheque.ui.able.components.TextField;
import org.jtheque.ui.able.components.filthy.Filthy;
import org.jtheque.ui.utils.builders.JThequePanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.ui.utils.components.Borders;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * A panel with a label, a text field and a button to choose a file.
 *
 * @author Baptiste Wicht
 */
public final class FilthyFileChooserPanel extends FileChooser implements Internationalizable {
    private TextField fieldFilePath;
    private JLabel label;
    private JButton button;
    private SimpleFilter filter;

    private boolean directoriesOnly;

    private String key;

    private static final int FIELD_COLUMNS = 15;

    /**
     * Construct a new FileChooserPanel.
     *
     * @param label A boolean tag indicating if we must display the label or not.
     */
    public FilthyFileChooserPanel(boolean label) {
        super();

        build(label);
    }

    /**
     * Construct a new FilthyFileChooserPanel.
     */
    public FilthyFileChooserPanel() {
        this(true);
    }

    /**
     * Build the panel.
     *
     * @param displayLabel A boolean tag indicating if we must display the label or not.
     */
    private void build(boolean displayLabel) {
        PanelBuilder builder = new JThequePanelBuilder(this);

        setOpaque(false);
        setBorder(Borders.EMPTY_BORDER);

        builder.setDefaultInsets(new Insets(0, 0, 0, 0));

        int x = 0;

        if (displayLabel) {
            label = builder.add(new JLabel(),
                    builder.gbcSet(0, 0, GridBagConstraints.NONE, GridBagConstraints.ABOVE_BASELINE_LEADING, 1, 0));
            label.setForeground(Color.white);

            x++;
        }

        JComponent panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEtchedBorder(1));

        fieldFilePath = Filthy.newTextField(FIELD_COLUMNS);
        Insets insets = fieldFilePath.getField().getMargin();
        fieldFilePath.setBorder(BorderFactory.createEmptyBorder(insets.top, insets.left, insets.bottom, insets.right));
        panel.add(fieldFilePath, BorderLayout.CENTER);

        addBrowseButton(panel);

        if (displayLabel) {
            builder.setDefaultInsets(new Insets(0, 5, 0, 0));
        }

        builder.add(panel, builder.gbcSet(x, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.ABOVE_BASELINE_LEADING, 0, 0, 1.0, 1.0));
    }

    /**
     * Add the browse button.
     *
     * @param panel The parent panel.
     */
    private void addBrowseButton(Container panel) {
        button = new BrowseButton(new BrowseAction());

        button.setMargin(new Insets(2, 0, 2, 0));

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
     * A button to integrate in the component.
     *
     * @author Baptiste Wicht
     */
    private static final class BrowseButton extends JButton {
        private static final int BUTTON_WIDTH = 20;

        /**
         * Construct a new BrowseButton.
         *
         * @param action The action to launch on the button click.
         */
        private BrowseButton(Action action) {
            super(action);
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();

            size.height = 10;
            size.width = BUTTON_WIDTH;

            return size;
        }
    }

    @Override
    public JTextField getTextField() {
        return fieldFilePath.getField();
    }

    /**
     * A Browse action.
     *
     * @author Baptiste Wicht
     */
    private final class BrowseAction extends AbstractAction {
        /**
         * Construct a new BrowseAction.
         */
        private BrowseAction() {
            super("...");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            File file = directoriesOnly ? SwingUtils.chooseDirectory() : SwingUtils.chooseFile(filter);

            if (file != null) {
                fieldFilePath.setText(file.getAbsolutePath());
            }
        }
    }
}