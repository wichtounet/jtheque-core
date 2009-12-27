package org.jtheque.core.managers.view.impl.frame;

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

import org.jtheque.core.managers.message.Message;
import org.jtheque.core.managers.view.able.IMessageView;
import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.core.managers.view.impl.actions.messages.DisplayNextMessageAction;
import org.jtheque.core.managers.view.impl.actions.messages.DisplayPreviousMessageAction;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingBuildedDialogView;
import org.jtheque.core.managers.view.impl.models.IMessageModel;
import org.jtheque.core.managers.view.impl.models.MessageModel;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * A view to display the messages of the module and the application. 
 *
 * @author Baptiste Wicht
 */
public final class MessageView extends SwingBuildedDialogView<IModel> implements IMessageView {
    private JLabel dateLabel;
    private JLabel sourceLabel;
    private JLabel titleLabel;
    private JTextArea messageArea;

    /**
     * Construct a new MessageView.
     */
    public MessageView(){
        super();

        build();
    }

    @Override
    protected void initView(){
        setModel(new MessageModel());
        setTitleKey("messages.view.title");
        setResizable(false);
    }

    @Override
    protected void buildView(PanelBuilder builder){
        addLabels(builder);
        addFields(builder);

        builder.addButtonBar(builder.gbcSet(0, 4, GridBagUtils.HORIZONTAL, GridBagUtils.LINE_END, 2, 1),
                getCloseAction("messages.actions.close"), new DisplayNextMessageAction(), new DisplayPreviousMessageAction());
    }

    /**
     * Add the labels to the view.
     *
     * @param builder The builder to add the labels to.
     */
    private static void addLabels(PanelBuilder builder) {
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
        dateLabel = builder.add(new JLabel(getModel().isDefaultMessage() ? "" : getModel().getCurrentMessage().getDate().getStrDate()),
                builder.gbcSet(1, 0));

        sourceLabel = builder.add(new JLabel(getModel().isDefaultMessage() ? "" : getModel().getCurrentMessage().getSource()),
                builder.gbcSet(1, 1));

        titleLabel = builder.add(new JLabel(getModel().isDefaultMessage() ? "" : getModel().getCurrentMessage().getTitle()),
                builder.gbcSet(1, 2));

        messageArea = new JTextArea(getModel().isDefaultMessage() ? "" : getModel().getCurrentMessage().getMessage());
        messageArea.setRows(8);
        messageArea.setEnabled(false);

        builder.addScrolled(messageArea, builder.gbcSet(0, 3, GridBagUtils.BOTH, GridBagUtils.LINE_START, 2, 1));
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

    @Override
    public IMessageModel getModel() {
        return (IMessageModel) super.getModel();
    }
}