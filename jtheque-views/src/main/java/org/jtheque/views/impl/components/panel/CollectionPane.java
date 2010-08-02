package org.jtheque.views.impl.components.panel;

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

import org.jtheque.core.able.Core;
import org.jtheque.utils.SimplePropertiesCache;
import org.jtheque.i18n.able.LanguageService;
import org.jtheque.ui.able.Controller;
import org.jtheque.ui.able.components.Components;
import org.jtheque.ui.able.components.TextField;
import org.jtheque.ui.able.components.filthy.Filthy;
import org.jtheque.ui.utils.AbstractPanelView;
import org.jtheque.ui.utils.AnimationUtils;
import org.jtheque.ui.utils.actions.ActionFactory;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.panel.CollectionView;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;

import static org.jtheque.ui.able.components.filthy.FilthyConstants.*;

/**
 * @author Baptiste Wicht
 */
public final class CollectionPane extends AbstractPanelView implements CollectionView {
    private TextField textField;
    private TextField passwordField;
    private JLabel labelError;

    private static final int LEFT_MARGIN_WIDTH = 200;

    @Resource
    private Core core;

    @Resource
    private Controller<CollectionView> collectionController;

    @Resource
    private LanguageService languageService;

    private JThequeAction chooseAction;

    /**
     * Build the view.
     */
    @PostConstruct
    void build() {
        chooseAction = ActionFactory.createAction("collections.actions.choose", collectionController);

        setOpaque(true);
        setBackground(BACKGROUND_COLOR);
        setAlpha(1.0f);
        setLayout(new GridBagLayout());

        GridBagUtils gbc = new GridBagUtils();

        gbc.setDefaultInsets(new Insets(0, 0, 0, 0));

        add(Box.createVerticalStrut(SimplePropertiesCache.get("mainView", Window.class).getHeight() / 3),
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
        Component labelCollection = Components.newI18nLabel("collections.name");

        labelCollection.setForeground(HINT_COLOR);
        labelCollection.setFont(HINT_FONT);

        gbc.setDefaultInsets(new Insets(0, 0, 2, 6));

        add(labelCollection, gbc.gbcSet(1, 2, GridBagConstraints.NONE, GridBagConstraints.LINE_START));

        gbc.setDefaultInsets(new Insets(0, 0, 0, 0));

        textField = Filthy.newTextField();
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
        Component labelPassword = Components.newI18nLabel("collections.password");

        labelPassword.setForeground(HINT_COLOR);
        labelPassword.setFont(HINT_FONT);

        gbc.setDefaultInsets(new Insets(0, 0, 2, 6));

        add(labelPassword, gbc.gbcSet(1, 3, GridBagConstraints.NONE, GridBagConstraints.LINE_START));

        passwordField = Filthy.newPasswordField();

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

        JThequeAction createAction = ActionFactory.createAction("collections.actions.create", collectionController);
        JThequeAction cancelAction = ActionFactory.createAction("collections.actions.cancel", collectionController);

        createAction.refreshText(languageService);
        cancelAction.refreshText(languageService);
        chooseAction.refreshText(languageService);

        buttonsPanel.add(new JButton(createAction));
        buttonsPanel.add(new JButton(chooseAction));
        buttonsPanel.add(new JButton(cancelAction));

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
        return passwordField.getText();
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