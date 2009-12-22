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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.able.IConfigView;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.components.LayerTabbedPane;
import org.jtheque.core.managers.view.impl.components.config.ConfigTabComponent;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.managers.view.listeners.ConfigTabEvent;
import org.jtheque.core.managers.view.listeners.ConfigTabListener;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.Action;
import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

/**
 * A view for the configuration.
 *
 * @author Baptiste Wicht
 */
public final class ConfigView extends SwingDialogView implements ConfigTabListener, IConfigView {
    private LayerTabbedPane tab;

    private final Action applyChangesAndCloseAction;
    private final Action cancelChangesAction;
    private final Action applyChangesAction;

    /**
     * Construct a new ConfigView.
     *
     * @param frame                      The parent frame.
     * @param applyChangesAndCloseAction The action to apply the changes and close the view.
     * @param cancelChangesAction        The action to cancel the changes.
     * @param applyChangesAction         The action to apply the changes.
     */
    public ConfigView(Frame frame, Action applyChangesAndCloseAction, Action cancelChangesAction, Action applyChangesAction) {
        super(frame);

        this.applyChangesAndCloseAction = applyChangesAndCloseAction;
        this.cancelChangesAction = cancelChangesAction;
        this.applyChangesAction = applyChangesAction;

        build();
    }

    /**
     * Build the view.
     */
    private void build() {
        setContentPane(buildContentPane());
        setTitleKey("config.view.title");

        pack();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Build and return the content pane.
     *
     * @return The content pane
     */
    private Container buildContentPane() {
        PanelBuilder builder = new PanelBuilder();

        tab = new LayerTabbedPane();

        for (ConfigTabComponent component : Managers.getManager(IViewManager.class).getConfigTabComponents()) {
            tab.addLayeredTab(component.getTitle(), component.getComponent());
        }

        Managers.getManager(IViewManager.class).addConfigTabListener(this);

        builder.add(tab, builder.gbcSet(0, 0, GridBagUtils.BOTH));

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL),
                applyChangesAndCloseAction, cancelChangesAction, applyChangesAction);

        return builder.getPanel();
    }

    @Override
    public ConfigTabComponent getSelectedPanelConfig() {
        return (ConfigTabComponent) tab.getSelectedComponent();
    }

    @Override
    public void tabAdded(ConfigTabEvent event) {
        tab.addLayeredTab(event.getComponent().getTitle(), event.getComponent().getComponent());
    }

    @Override
    public void tabRemoved(ConfigTabEvent event) {
        tab.removeTabAt(tab.indexOfTab(event.getComponent().getTitle()));
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
        getSelectedPanelConfig().validate(errors);
    }
}