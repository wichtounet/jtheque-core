package org.jtheque.views.impl.actions.module;

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

import org.jtheque.modules.able.IModuleManager;
import org.jtheque.ui.IUIUtils;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.views.ViewsServices;

import java.awt.event.ActionEvent;
import java.io.File;

/**
 * An action to install a module.
 *
 * @author Baptiste Wicht
 */
public final class InstallModuleAction extends JThequeAction {
    /**
     * Construct a new InstallModuleAction.
     */
    public InstallModuleAction() {
        super("modules.actions.new");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File file = new File(ViewsServices.get(IUIUtils.class).getDelegate().chooseFile(new SimpleFilter("JAR File (*.jar)", "jar")));

        boolean installed = ViewsServices.get(IModuleManager.class).installModule(file);

        if (installed) {
            ViewsServices.get(IUIUtils.class).displayI18nText("message.module.installed");
        } else {
            ViewsServices.get(IUIUtils.class).displayI18nText("error.module.not.installed");
        }
    }
}