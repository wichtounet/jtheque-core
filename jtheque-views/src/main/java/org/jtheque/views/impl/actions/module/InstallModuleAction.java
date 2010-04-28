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

import org.jtheque.modules.able.IModuleService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.ui.SwingUtils;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * An action to install a module.
 *
 * @author Baptiste Wicht
 */
public final class InstallModuleAction extends JThequeAction {
    @Resource
    private IUIUtils uiUtils;

    @Resource
    private IModuleService moduleService;

    /**
     * Construct a new InstallModuleAction.
     */
    public InstallModuleAction() {
        super("modules.actions.new");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File file = new File(SwingUtils.chooseFile(new SimpleFilter("JAR File (*.jar)", "jar")));

        boolean installed = moduleService.installModule(file);

        if (installed) {
            uiUtils.displayI18nText("message.module.installed");
        } else {
            uiUtils.displayI18nText("error.module.not.installed");
        }
    }
}