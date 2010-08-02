package org.jtheque.views.impl.windows;

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

import org.jtheque.messages.able.Message;
import org.jtheque.messages.able.IMessageService;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.ui.utils.windows.dialogs.SwingFilthyBuildedDialogView;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.able.windows.IMessageView;
import org.jtheque.views.impl.models.IMessageModel;
import org.jtheque.views.impl.models.MessageModel;

import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * A view to display the messages of the module and the application.
 *
 * @author Baptiste Wicht
 */
public final class MessageView extends SwingFilthyBuildedDialogView<IMessageModel> implements IMessageView {
    private JLabel dateLabel;
    private JLabel sourceLabel;
    private JLabel titleLabel;
    private JTextArea messageArea;

    @Override
    protected void initView() {
        setModel(new MessageModel(getService(IMessageService.class)));
        setTitleKey("messages.view.title");
        setResizable(false);
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        addLabels(builder);
        addFields(builder);

        builder.addButtonBar(builder.gbcSet(0, 4, GridBagUtils.HORIZONTAL, GridBagUtils.LINE_END, 2, 1),
                getAction("messages.actions.close"),
                getAction("messages.actions.display.next"),
                getAction("messages.actions.display.previous"));
    }

    /**
     * Add the labels to the view.
     *
     * @param builder The builder to add the labels to.
     */
    private static void addLabels(I18nPanelBuilder builder) {
        builder.addI18nLabel("messages.view.data.date", PanelBuilder.BOLD, builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.LINE_END));
        builder.addI18nLabel("messages.view.data.source", PanelBuilder.BOLD, builder.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.LINE_END));
        builder.addI18nLabel("messages.view.data.title", PanelBuilder.BOLD, builder.gbcSet(0, 2, GridBagUtils.NONE, GridBagUtils.LINE_END));
    }

    /**
     * Add the fields to the view.
     *
     * @param builder The builder to add the fields to.
     */
    private void addFields(PanelBuilder builder) {
        dateLabel = builder.addLabel(getModel().isDefaultMessage() ? "" : getModel().getCurrentMessage().getDate().getStrDate(), builder.gbcSet(1, 0));
        sourceLabel = builder.addLabel(getModel().isDefaultMessage() ? "" : getModel().getCurrentMessage().getSource(), builder.gbcSet(1, 1));
        titleLabel = builder.addLabel(getModel().isDefaultMessage() ? "" : getModel().getCurrentMessage().getTitle(), builder.gbcSet(1, 2));

        messageArea = builder.addScrolledTextArea(getModel().isDefaultMessage() ? "" : getModel().getCurrentMessage().getMessage(),
                builder.gbcSet(0, 3, GridBagUtils.BOTH, GridBagUtils.LINE_START, 2, 1));
        messageArea.setRows(8);
        messageArea.setEnabled(false);
    }

    @Override
    public void next() {
        setMessage(getModel().getNextMessage());
    }

    @Override
    public void previous() {
        setMessage(getModel().getPreviousMessage());
    }

    /**
     * Set the message to display.
     *
     * @param message The message to display.
     */
    private void setMessage(Message message) {
        if (getModel().isDefaultMessage()) {
            dateLabel.setText(message.getDate().getStrDate());
            sourceLabel.setText(message.getSource());
            titleLabel.setText(message.getTitle());
            messageArea.setText(message.getMessage());
        }
    }
}