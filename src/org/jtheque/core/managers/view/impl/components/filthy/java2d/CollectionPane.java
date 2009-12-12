package org.jtheque.core.managers.view.impl.components.filthy.java2d;

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
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.able.ICollectionView;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.components.JThequeI18nLabel;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyButton;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyPasswordField;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyTextField;
import org.jtheque.core.utils.ui.AnimationUtils;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * @author Baptiste Wicht
 */
public final class CollectionPane extends JXPanel implements ICollectionView {
    private FilthyTextField textField;
    private FilthyPasswordField passwordField;
    private JLabel labelError;

    private Color hintColor;
    private Color backgroundColor;
    private Color errorColor;

    private Action createAction;
    private Action cancelAction;
    private Action chooseAction;

    private Font hintFont;
    private static final int LEFT_MARGIN_WIDTH = 200;

    /**
     * Build the view.
     */
    @PostConstruct
    void build() {
        setOpaque(true);
        setBackground(backgroundColor);
        setAlpha(1.0f);
        setLayout(new GridBagLayout());

        GridBagUtils gbc = new GridBagUtils();

        gbc.setDefaultInsets(new Insets(0, 0, 0, 0));

        add(Box.createVerticalStrut(Managers.getManager(IViewManager.class).getViews().getMainView().getHeight() / 3),
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
        labelError.setForeground(errorColor);
        labelError.setVisible(false);
        labelError.setFont(hintFont);

        add(labelError, gbc.gbcSet(1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 2, 1));
    }

    /**
     * Add the button bar to the view.
     *
     * @param gbc The grid bag utils constraints object.
     */
    private void addButtonBar(GridBagUtils gbc) {
        Container buttonsPanel = new JPanel();

        buttonsPanel.add(new FilthyButton(createAction));
        buttonsPanel.add(new FilthyButton(chooseAction));
        buttonsPanel.add(new FilthyButton(cancelAction));

        buttonsPanel.setBackground(backgroundColor);

        gbc.setDefaultInsets(new Insets(0, 0, 0, 0));

        add(buttonsPanel, gbc.gbcSet(1, 4, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 2, 1, 1.0, 0.0));
    }

    /**
     * Add the password field to the view.
     *
     * @param gbc The grid bag utils constraints object.
     */
    private void addPasswordField(GridBagUtils gbc) {
        Component labelPassword = new JThequeI18nLabel("collections.password");

        labelPassword.setForeground(hintColor);
        labelPassword.setFont(hintFont);

        gbc.setDefaultInsets(new Insets(0, 0, 2, 6));

        add(labelPassword, gbc.gbcSet(1, 3, GridBagConstraints.NONE, GridBagConstraints.LINE_START));

        passwordField = new FilthyPasswordField();

        gbc.setDefaultInsets(new Insets(0, 2, 6, 2));

        add(passwordField, gbc.gbcSet(2, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 1.0, 0.0));
    }

    /**
     * Add the collection field to the view.
     *
     * @param gbc The grid bag utils constraints object.
     */
    private void addCollectionField(GridBagUtils gbc) {
        Component labelCollection = new JThequeI18nLabel("collections.name");

        labelCollection.setForeground(hintColor);
        labelCollection.setFont(hintFont);

        gbc.setDefaultInsets(new Insets(0, 0, 2, 6));

        add(labelCollection, gbc.gbcSet(1, 2, GridBagConstraints.NONE, GridBagConstraints.LINE_START));

        gbc.setDefaultInsets(new Insets(0, 0, 0, 0));

        textField = new FilthyTextField();

        gbc.setDefaultInsets(new Insets(0, 2, 6, 2));

        add(textField, gbc.gbcSet(2, 2, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 1.0, 0.0));
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

    /**
     * Set the hint color. Not for use, only for Spring injection.
     *
     * @param hintColor The hint color.
     */
    public void setHintColor(Color hintColor) {
        this.hintColor = hintColor;
    }

    /**
     * Set the background color. Not for use, only for Spring injection.
     *
     * @param backgroundColor The background color.
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Set the error color. Not for use, only for Spring injection.
     *
     * @param errorColor The error text color.
     */
    public void setErrorColor(Color errorColor) {
        this.errorColor = errorColor;
    }

    /**
     * Set the action to launch with the create button. Not for use, only for Spring injection.
     *
     * @param createAction The create action.
     */
    public void setCreateAction(Action createAction) {
        this.createAction = createAction;
    }

    /**
     * Set the action to launch with the cancel button. Not for use, only for Spring injection.
     *
     * @param cancelAction The cancel action.
     */
    public void setCancelAction(Action cancelAction) {
        this.cancelAction = cancelAction;
    }

    /**
     * Set the action to launch with the choose button. Not for use, only for Spring injection.
     *
     * @param chooseAction The choose action.
     */
    public void setChooseAction(Action chooseAction) {
        this.chooseAction = chooseAction;
    }

    /**
     * Set the hint font. Not for use, only for Spring injection.
     *
     * @param hintFont The hint font.
     */
    public void setHintFont(Font hintFont) {
        this.hintFont = hintFont;
    }

    @Override
    public Component getImpl() {
        return this;
    }
}