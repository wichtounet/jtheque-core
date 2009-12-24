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

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.update.Updatable;
import org.jtheque.core.managers.view.able.components.IModulesPanelView;
import org.jtheque.core.managers.view.able.components.IUpdatablesPanelView;
import org.jtheque.core.managers.view.able.update.IModuleView;
import org.jtheque.core.managers.view.impl.components.LayerTabbedPane;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.JComponent;
import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

/**
 * A view to display the modules.
 *
 * @author Baptiste Wicht
 */
public final class ModuleView extends SwingDialogView implements IModuleView {
    @Resource
    private IModulesPanelView modulesPanel;

    @Resource
    private IUpdatablesPanelView updatablesPanel;

    /**
     * Construct a new <code>ModuleView</code>.
     *
     * @param frame The parent frame.
     */
    public ModuleView(Frame frame) {
        super(frame);
    }

    /**
     * Build the view.
     */
    @PostConstruct
    public void build() {
        setTitleKey("module.view.title");
        setContentPane(buildContentPane());
        pack();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Build the content pane.
     *
     * @return The content pane.
     */
    private Container buildContentPane() {
        LayerTabbedPane tabbed = new LayerTabbedPane();

        tabbed.addInternationalizedTab("modules.view.tab.modules", (JComponent) modulesPanel);
        tabbed.addInternationalizedTab("modules.view.tab.updatables", (JComponent) updatablesPanel);

        return tabbed;
    }

    @Override
    public ModuleContainer getSelectedModule() {
        return modulesPanel.getSelectedModule();
    }

    @Override
    public Updatable getSelectedUpdatable() {
        return updatablesPanel.getSelectedUpdatable();
    }

    @Override
    public void refreshList() {
        modulesPanel.refresh();
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
        //Nothing to validate
    }
}