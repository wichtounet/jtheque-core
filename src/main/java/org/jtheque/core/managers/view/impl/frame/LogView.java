package org.jtheque.core.managers.view.impl.frame;

import org.jdesktop.swingx.JXTable;
import org.jtheque.core.managers.event.EventLog;
import org.jtheque.core.managers.view.able.ILogView;
import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.core.managers.view.impl.actions.event.UpdateAction;
import org.jtheque.core.managers.view.impl.components.model.EventsTableModel;
import org.jtheque.core.managers.view.impl.components.model.LogComboBoxModel;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingBuildedDialogView;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;

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

/**
 * A log view implementation.
 *
 * @author Baptiste Wicht
 */
public final class LogView extends SwingBuildedDialogView<IModel> implements ListSelectionListener, ItemListener, ILogView {
    private JXTable tableEvents;
    private EventsTableModel eventsModel;

    private JLabel labelTitle;
    private JLabel labelLog;
    private JLabel labelSource;
    private JLabel labelLevel;
    private JLabel labelDate;
    private JLabel labelTime;

    private JTextArea areaDetails;

    private final DateFormat dateFormat;
    private final DateFormat timeFormat;

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 450;

    /**
     * Construct a new LogView.
     *
     * @param dateFormat The dateformat to use.
     * @param timeFormat The image format to use. 
     */
    public LogView(DateFormat dateFormat, DateFormat timeFormat){
        super();

        this.dateFormat = dateFormat;
        this.timeFormat = timeFormat;

        build();
    }

    @Override
    protected void initView(){
        setTitleKey("log.view.title");
        setResizable(false);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    protected void buildView(PanelBuilder builder){
        builder.setDefaultInsets(new Insets(4, 4, 4, 4));

        builder.addI18nLabel("log.view.log", builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.LINE_END));

        JComboBox comboLogs = builder.addComboBox(new LogComboBoxModel(), builder.gbcSet(1, 0, GridBagUtils.HORIZONTAL, GridBagUtils.LINE_START, 1.0, 0.0));
        comboLogs.addItemListener(this);

        eventsModel = new EventsTableModel();
        eventsModel.setHeaders(new String[]{
                getMessage("log.view.level"),
                getMessage("log.view.date"),
                getMessage("log.view.time"),
                getMessage("log.view.source"),
                getMessage("log.view.title")});

        tableEvents = new JXTable(eventsModel);
        tableEvents.getSelectionModel().addListSelectionListener(this);
        tableEvents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableEvents.setVisibleRowCount(5);
        tableEvents.setColumnControlVisible(true);
        tableEvents.packAll();

        builder.addScrolled(tableEvents, builder.gbcSet(0, 1, GridBagUtils.BOTH, GridBagUtils.LINE_START, 2, 1, 1.0, 0.67));

        createInfosPanel(builder);

        builder.addButton(new UpdateAction(), builder.gbcSet(1, 3, GridBagUtils.NONE, GridBagUtils.LINE_END));
    }

    /**
     * Create the panel to display the informations of an event.
     *
     * @param parent The parent builder.
     */
    private void createInfosPanel(PanelBuilder parent) {
        PanelBuilder builder = parent.addPanel(parent.gbcSet(0, 2, GridBagUtils.BOTH, GridBagUtils.LINE_START, 2, 1, 1.0, 0.33));

        builder.setBorder(Borders.createTitledBorder("log.view.details"));

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
    private static void addLabels(PanelBuilder builder) {
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
            EventLog event = eventsModel.getValueAt(tableEvents.getSelectedRow());

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