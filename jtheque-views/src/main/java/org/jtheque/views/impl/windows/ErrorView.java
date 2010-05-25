package org.jtheque.views.impl.windows;

import org.jdesktop.swingx.error.ErrorInfo;
import org.jtheque.errors.able.ErrorListener;
import org.jtheque.errors.able.IError;
import org.jtheque.errors.able.IErrorService;
import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.resources.able.IResourceService;
import org.jtheque.ui.able.IModel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.windows.dialogs.SwingFilthyBuildedDialogView;
import org.jtheque.views.able.windows.IErrorView;
import org.jtheque.views.impl.components.renderers.ErrorListRenderer;
import org.jtheque.views.impl.models.ErrorsListModel;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
public final class ErrorView extends SwingFilthyBuildedDialogView<IModel> implements ListSelectionListener, IErrorView, ErrorListener {
    private JList listEvents;
    private ErrorsListModel errorsModel;

    private JLabel labelTitle;
    private JLabel labelLevel;
    private JLabel labelLevelImage;

    private JTextArea areaDetails;

    @Override
    protected void initView(){
	    setModal(false);
        setTitleKey("error.view.title");
        setResizable(true);
    }

    @Override
    protected void buildView(I18nPanelBuilder builder){
        builder.setDefaultInsets(new Insets(4, 4, 4, 4));

        errorsModel = new ErrorsListModel(getService(IErrorService.class), getService(ILanguageService.class));

        listEvents = builder.addScrolledList(errorsModel, new ErrorListRenderer(getService(IResourceService.class)),
                builder.gbcSet(0, 1, BOTH, LINE_START, 2, 1, 1.0, 0.67));
        listEvents.getSelectionModel().addListSelectionListener(this);
        listEvents.setVisibleRowCount(8);

        createInfosPanel(builder);

	    getService(IErrorService.class).addErrorListener(this);
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
        labelLevel = builder.addLabel(builder.gbcSet(1, 3, HORIZONTAL, BASELINE_LEADING, 0.5, 0.0));
        labelLevelImage = builder.addLabel(builder.gbcSet(0, 1, NONE, BASELINE_LEADING, 1, 2, 0.5, 0.0));

        areaDetails = new JTextArea();
        areaDetails.setRows(3);

        builder.addScrolled(areaDetails, builder.gbcSet(0, 5, BOTH, LINE_START, 2, 1, 1.0, 1.0));
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (listEvents.getSelectedValues().length > 0) {
            ErrorInfo info = (ErrorInfo) errorsModel.getElementAt(listEvents.getSelectedIndex());

            labelTitle.setText(info.getTitle());
            labelLevel.setText(info.getErrorLevel().toString());
            labelLevelImage.setText(info.getErrorLevel().toString());
            areaDetails.setText(info.getDetailedErrorMessage());
        }
    }

    @Override
    public void errorOccurred(IError error) {
        display();
    }
}