package org.jtheque.views.impl.windows;

import org.jtheque.events.able.IEvent;
import org.jtheque.events.able.IEventService;
import org.jtheque.i18n.able.LanguageService;
import org.jtheque.ui.able.IModel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.ui.able.components.filthy.Filthy;
import org.jtheque.ui.utils.models.SimpleListModel;
import org.jtheque.ui.utils.windows.dialogs.SwingFilthyBuildedDialogView;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.able.windows.IEventView;
import org.jtheque.views.impl.models.EventsTableModel;

import org.jdesktop.swingx.JXTable;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
 * A log view implementation.
 *
 * @author Baptiste Wicht
 */
public final class EventView extends SwingFilthyBuildedDialogView<IModel> implements ListSelectionListener, ItemListener, IEventView {
    private JXTable tableEvents;
    private EventsTableModel eventsModel;

    private JLabel labelTitle;
    private JLabel labelLog;
    private JLabel labelSource;
    private JLabel labelLevel;
    private JLabel labelDate;
    private JLabel labelTime;

    private JTextArea areaDetails;

    private final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    private final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss,SSS", Locale.getDefault());

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 450;

    @Override
    protected void initView() {
        setTitleKey("log.view.title");
        setResizable(false);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        builder.setDefaultInsets(new Insets(4, 4, 4, 4));

        builder.addI18nLabel("log.view.log", builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.LINE_END));

        builder.addComboBox(new SimpleListModel<String>(getService(IEventService.class).getEventLogs()), Filthy.newListRenderer(),
                builder.gbcSet(1, 0, GridBagUtils.HORIZONTAL, GridBagUtils.LINE_START, 1.0, 0.0)).addItemListener(this);

        eventsModel = new EventsTableModel(getService(IEventService.class), getService(LanguageService.class));
        eventsModel.setHeaders(new String[]{
                getMessage("log.view.level"),
                getMessage("log.view.date"),
                getMessage("log.view.time"),
                getMessage("log.view.source"),
                getMessage("log.view.title")});

        tableEvents = (JXTable) builder.addScrolledTable(eventsModel, builder.gbcSet(0, 1, GridBagUtils.BOTH, GridBagUtils.LINE_START, 2, 1, 1.0, 0.67));
        tableEvents.getSelectionModel().addListSelectionListener(this);
        tableEvents.setVisibleRowCount(8);

        createInfosPanel(builder);

        builder.addButton(getAction("log.view.actions.update"),
                builder.gbcSet(1, 3, GridBagUtils.NONE, GridBagUtils.LINE_END));
    }

    /**
     * Create the panel to display the informations of an event.
     *
     * @param parent The parent builder.
     */
    private void createInfosPanel(I18nPanelBuilder parent) {
        I18nPanelBuilder builder = parent.addPanel(parent.gbcSet(0, 2, GridBagUtils.BOTH, GridBagUtils.LINE_START, 2, 1, 1.0, 0.33));

        builder.setI18nTitleBorder("log.view.details");

        addLabels(builder);
        addFieldLabels(builder);
    }

    /**
     * Add all the fields.
     *
     * @param builder The frame builder.
     */
    private void addFieldLabels(PanelBuilder builder) {
        labelTitle = builder.addLabel(builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 4, 1));
        labelLog = builder.addLabel(builder.gbcSet(1, 1, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 0.5, 0.0));
        labelSource = builder.addLabel(builder.gbcSet(1, 2, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 0.5, 0.0));
        labelLevel = builder.addLabel(builder.gbcSet(1, 3, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 0.5, 0.0));
        labelDate = builder.addLabel(builder.gbcSet(3, 1, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 0.5, 0.0));
        labelTime = builder.addLabel(builder.gbcSet(3, 2, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 0.5, 0.0));

        areaDetails = new JTextArea();
        areaDetails.setRows(3);

        builder.addScrolled(areaDetails, builder.gbcSet(0, 5, GridBagUtils.BOTH, GridBagUtils.LINE_START, 4, 1, 1.0, 1.0));
    }

    /**
     * Add all the labels.
     *
     * @param builder The frame builder.
     */
    private static void addLabels(I18nPanelBuilder builder) {
        builder.addI18nLabel("log.view.log", PanelBuilder.BOLD, builder.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_TRAILING));
        builder.addI18nLabel("log.view.source", PanelBuilder.BOLD, builder.gbcSet(0, 2, GridBagUtils.NONE, GridBagUtils.BASELINE_TRAILING));
        builder.addI18nLabel("log.view.level", PanelBuilder.BOLD, builder.gbcSet(0, 3, GridBagUtils.NONE, GridBagUtils.BASELINE_TRAILING));

        builder.addI18nLabel("log.view.date", PanelBuilder.BOLD, builder.gbcSet(2, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_TRAILING));
        builder.addI18nLabel("log.view.time", PanelBuilder.BOLD, builder.gbcSet(2, 2, GridBagUtils.NONE, GridBagUtils.BASELINE_TRAILING));

        builder.addI18nLabel("log.view.details", builder.gbcSet(0, 4, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 4, 1));
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (tableEvents.getSelectedRowCount() > 0) {
            IEvent event = eventsModel.getValueAt(tableEvents.getSelectedRow());

            labelTitle.setText(getMessage(event.getTitleKey()));
            labelLog.setText(event.getLog());
            labelSource.setText(event.getSource());
            labelLevel.setText(getMessage(event.getLevel().getKey()));
            labelDate.setText(dateFormat.format(event.getDate()));
            labelTime.setText(timeFormat.format(event.getDate()));
            areaDetails.setText(getMessage(event.getDetailsKey()));
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            eventsModel.setLog((String) e.getItem());
        }
    }
}