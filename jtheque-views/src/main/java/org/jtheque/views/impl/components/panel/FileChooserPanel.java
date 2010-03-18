package org.jtheque.views.impl.components.panel;

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

import org.jtheque.i18n.ILanguageManager;
import org.jtheque.i18n.Internationalizable;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.builders.JThequePanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.views.ViewsServices;

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

/**
 * A panel with a label, a text field and a button to choose a file.
 *
 * @author Baptiste Wicht
 */
public final class FileChooserPanel extends JPanel implements Internationalizable {
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

    /**
     * Set the text of the label.
     *
     * @param text The text of the label.
     */
    void setText(String text) {
        label.setText(text);
    }

    /**
     * Set the text key for the label.
     *
     * @param key The internationalization key.
     */
    public void setTextKey(String key) {
        this.key = key;

        setText(ViewsServices.get(ILanguageManager.class).getMessage(key));
    }

    /**
     * Return the path to the file.
     *
     * @return The path to the file.
     */
    public String getFilePath() {
        return fieldFilePath.getText();
    }

    /**
     * Set the path to the file.
     *
     * @param path The path to the file.
     */
    public void setFilePath(String path) {
        fieldFilePath.setText(path);
    }

    /**
     * Set the file filter.
     *
     * @param filter The file filter.
     */
    public void setFileFilter(SimpleFilter filter) {
        this.filter = filter;
    }

    /**
     * Set if the chooser search only for directories or not.
     */
    public void setDirectoriesOnly() {
        directoriesOnly = true;
    }

    /**
     * Set that the chooser search only for files.
     */
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
    public void refreshText() {
        if (key != null) {
            setText(ViewsServices.get(ILanguageManager.class).getMessage(key));
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
            String file = directoriesOnly ? ViewsServices.get(IUIUtils.class).getDelegate().chooseDirectory() :
                    ViewsServices.get(IUIUtils.class).getDelegate().chooseFile(filter);

            if (file != null) {
                fieldFilePath.setText(file);
            }
        }
    }
}