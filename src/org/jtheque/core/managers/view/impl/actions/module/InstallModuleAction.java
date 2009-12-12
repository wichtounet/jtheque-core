package org.jtheque.core.managers.view.impl.actions.module;

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
import org.jtheque.core.managers.module.IModuleManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.utils.io.SimpleFilter;

import java.awt.event.ActionEvent;
import java.io.File;

/**
 * An action to install a module.
 *
 * @author Baptiste Wicht
 */
public final class InstallModuleAction extends JThequeAction {
    private static final long serialVersionUID = -911919582657663109L;

    /**
     * Construct a new InstallModuleAction.
     */
    public InstallModuleAction() {
        super("modules.actions.new");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File file = new File(Managers.getManager(IViewManager.class).chooseFile(new SimpleFilter("JAR File (*.jar)", "jar")));

        boolean installed = Managers.getManager(IModuleManager.class).installModule(file);

        if (installed) {
            Managers.getManager(IViewManager.class).displayI18nText("message.module.installed");
        } else {
            Managers.getManager(IViewManager.class).displayI18nText("error.module.not.installed");
        }
    }
}