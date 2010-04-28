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

import org.jdesktop.swingx.JXPanel;
import org.jtheque.core.ICore;
import org.jtheque.core.utils.SimplePropertiesCache;
import org.jtheque.ui.utils.AnimationUtils;
import org.jtheque.ui.utils.components.JThequeI18nLabel;
import org.jtheque.ui.utils.filthy.Filthy;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.panel.ICollectionView;
import org.jtheque.views.impl.actions.collections.CancelAction;
import org.jtheque.views.impl.actions.collections.ChooseAction;
import org.jtheque.views.impl.actions.collections.CreateAction;
import org.jtheque.views.impl.filthy.FilthyPasswordField;
import org.jtheque.views.impl.filthy.FilthyTextField;

import javax.annotation.PostConstruct;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

/**
 * @author Baptiste Wicht
 */
public final class CollectionPane extends JXPanel implements ICollectionView, Filthy {
    private FilthyTextField textField;
    private FilthyPasswordField passwordField;
    private JLabel labelError;

    private static final int LEFT_MARGIN_WIDTH = 200;

    private final Action chooseAction = new ChooseAction();

    private final ICore core;

    public CollectionPane(ICore core) {
        super();

        this.core = core;
    }

    /**
     * Build the view.
     */
    @PostConstruct
    void build() {
        setOpaque(true);
        setBackground(BACKGROUND_COLOR);
        setAlpha(1.0f);
        setLayout(new GridBagLayout());

        GridBagUtils gbc = new GridBagUtils();

        gbc.setDefaultInsets(new Insets(0, 0, 0, 0));

        add(Box.createVerticalStrut(SimplePropertiesCache.<Window>get("MainView").getHeight() / 3),
                gbc.gbcSet(0, 0, GridBagConstraints.NONE, GridBagConstraints.CENTER, 4, 1, 1.0, 0.0));

        add(Box.createHorizontalStrut(LEFT_MARGIN_WIDTH), gbc.gbcSet(0, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 4, 0.3, 0.0));

        addErrorLabel(gbc);

        add(Box.createHorizontalStrut(LEFT_MARGIN_WIDTH), gbc.gbcSet(3, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 4, 0.3, 0.0));

        addCollectionField(gbc);
        addPasswordField(gbc);
        addButtonBar(gbc);

        add(Box.createVerticalGlue(), gbc.gbcSet(0, 5, GridBagConstraints.VERTICAL, GridBagConstraints.CENTER, 4, 1, 1.0, 1.0));
    }

    /**
     * Add the error label.
     *
     * @param gbc The grid bag utils constraints object.
     */
    private void addErrorLabel(GridBagUtils gbc) {
        labelError = new JLabel();
        labelError.setForeground(ERROR_COLOR);
        labelError.setVisible(false);
        labelError.setFont(ERROR_FONT);

        add(labelError, gbc.gbcSet(1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 2, 1));
    }

    /**
     * Add the collection field to the view.
     *
     * @param gbc The grid bag utils constraints object.
     */
    private void addCollectionField(GridBagUtils gbc) {
        Component labelCollection = new JThequeI18nLabel("collections.name");

        labelCollection.setForeground(HINT_COLOR);
        labelCollection.setFont(HINT_FONT);

        gbc.setDefaultInsets(new Insets(0, 0, 2, 6));

        add(labelCollection, gbc.gbcSet(1, 2, GridBagConstraints.NONE, GridBagConstraints.LINE_START));

        gbc.setDefaultInsets(new Insets(0, 0, 0, 0));

        textField = new FilthyTextField();
        textField.setText(core.getConfiguration().getLastCollection());

        SwingUtils.addFieldValidateAction(textField.getField(), chooseAction);

        gbc.setDefaultInsets(new Insets(0, 2, 6, 2));

        add(textField, gbc.gbcSet(2, 2, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 1.0, 0.0));
    }

    /**
     * Add the password field to the view.
     *
     * @param gbc The grid bag utils constraints object.
     */
    private void addPasswordField(GridBagUtils gbc) {
        Component labelPassword = new JThequeI18nLabel("collections.password");

        labelPassword.setForeground(HINT_COLOR);
        labelPassword.setFont(HINT_FONT);

        gbc.setDefaultInsets(new Insets(0, 0, 2, 6));

        add(labelPassword, gbc.gbcSet(1, 3, GridBagConstraints.NONE, GridBagConstraints.LINE_START));

        passwordField = new FilthyPasswordField();

        SwingUtils.addFieldValidateAction(passwordField.getField(), chooseAction);

        gbc.setDefaultInsets(new Insets(0, 2, 6, 2));

        add(passwordField, gbc.gbcSet(2, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 1.0, 0.0));
    }

    /**
     * Add the button bar to the view.
     *
     * @param gbc The grid bag utils constraints object.
     */
    private void addButtonBar(GridBagUtils gbc) {
        Container buttonsPanel = new JPanel();

        buttonsPanel.add(new JButton(new CreateAction()));
        buttonsPanel.add(new JButton(new ChooseAction()));
        buttonsPanel.add(new JButton(new CancelAction()));

        buttonsPanel.setBackground(BACKGROUND_COLOR);

        gbc.setDefaultInsets(new Insets(0, 0, 0, 0));

        add(buttonsPanel, gbc.gbcSet(1, 4, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 2, 1, 1.0, 0.0));
    }

    @Override
    public void setErrorMessage(String message) {
        labelError.setVisible(true);
        labelError.setText(message);
    }

    @Override
    public String getCollection() {
        return textField.getText();
    }

    @Override
    public String getPassword() {
        return passwordField.getPassword();
    }

    @Override
    public void appear() {
        AnimationUtils.startFadeIn(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        super.paintComponent(g);
    }

    @Override
    public Component getImpl() {
        return this;
    }
}