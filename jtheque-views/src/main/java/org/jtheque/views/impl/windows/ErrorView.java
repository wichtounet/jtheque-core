package org.jtheque.views.impl.windows;

import org.jtheque.errors.able.*;
import org.jtheque.errors.able.Error;
import org.jtheque.errors.able.Error.Level;
import org.jtheque.i18n.able.LanguageService;
import org.jtheque.images.able.ImageService;
import org.jtheque.ui.able.Model;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.windows.frames.SwingFilthyBuildedFrameView;
import org.jtheque.views.impl.ViewsResources;
import org.jtheque.views.impl.components.renderers.ErrorListRenderer;
import org.jtheque.views.impl.models.ErrorsListModel;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Dialog.ModalExclusionType;
import java.awt.Insets;

import static org.jtheque.utils.ui.GridBagUtils.*;

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

/**
 * An error view implementation.
 *
 * @author Baptiste Wicht
 */
public final class ErrorView extends SwingFilthyBuildedFrameView<Model> implements ListSelectionListener,
        org.jtheque.views.able.windows.ErrorView, ErrorListener {
    private ImageIcon errorIcon;
    private ImageIcon warningIcon;

    private JList listEvents;

    private JLabel labelTitle;

    private JTextArea areaDetails;
    private LanguageService languageService;

    @Override
    protected void initView() {
        setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        setTitleKey("error.view.title");
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        builder.setDefaultInsets(new Insets(4, 4, 4, 4));

        languageService = getService(LanguageService.class);

        listEvents = builder.addScrolledList(new ErrorsListModel(getService(ErrorService.class)),
                new ErrorListRenderer(getService(ImageService.class), languageService),
                builder.gbcSet(0, 1, BOTH, LINE_START, 2, 1, 1.0, 0.67));
        listEvents.getSelectionModel().addListSelectionListener(this);
        listEvents.setVisibleRowCount(8);

        createInfosPanel(builder);

        getService(ErrorService.class).addErrorListener(this);

        errorIcon = getService(ImageService.class).getIcon(ViewsResources.ERROR_ICON);
        warningIcon = getService(ImageService.class).getIcon(ViewsResources.WARNING_ICON);
    }

    /**
     * Create the panel to display the informations of an event.
     *
     * @param parent The parent builder.
     */
    private void createInfosPanel(I18nPanelBuilder parent) {
        I18nPanelBuilder builder = parent.addPanel(parent.gbcSet(0, 2, BOTH, LINE_START, 2, 1, 1.0, 0.33));

        builder.setI18nTitleBorder("error.view.details");

        labelTitle = builder.addLabel(builder.gbcSet(0, 0, HORIZONTAL, BASELINE_LEADING));

        areaDetails = new JTextArea();
        areaDetails.setRows(3);
        areaDetails.setWrapStyleWord(true);
        areaDetails.setLineWrap(true);

        builder.addScrolled(areaDetails, builder.gbcSet(0, 2, BOTH, LINE_START, 1.0, 1.0));
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (listEvents.getSelectedValues().length > 0) {
            org.jtheque.errors.able.Error error = (Error) listEvents.getSelectedValue();

            labelTitle.setText(error.getTitle(languageService));
            labelTitle.setToolTipText(error.getLevel().toString());

            if (error.getLevel() == Level.WARNING) {
                labelTitle.setIcon(warningIcon);
            } else {
                labelTitle.setIcon(errorIcon);
            }

            areaDetails.setText(error.getDetails(languageService));
        }
    }

    @Override
    public void display() {
        super.display();
        toFirstPlan();
    }

    @Override
    public void errorOccurred(Error error) {
        display();
    }
}