package org.jtheque.views.impl.actions.module.repository;

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

import org.jtheque.core.ICore;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.impl.ModuleDescription;
import org.jtheque.spring.utils.injection.Injectable;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.able.IViews;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * Install the selected module.
 *
 * @author Baptiste Wicht
 */
public final class InstallRepositoryModuleAction extends JThequeAction implements Injectable {
    @Resource
    private IViews views;

    @Resource
    private IUIUtils utils;

    @Resource
    private IModuleService moduleService;

    /**
     * Construct a new InstallRepositoryModuleAction.
     */
    public InstallRepositoryModuleAction() {
        super("repository.actions.install");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ModuleDescription description = views.getRepositoryView().getSelectedModule();

        if (description.getCoreVersion().isGreaterThan(ICore.VERSION)) {
            utils.displayI18nText("error.module.version.core");
        } else {
            if (moduleService.isInstalled(description.getId())) {
                utils.displayI18nText("message.repository.module.installed");
            } else {
                moduleService.install(description.getVersionsFileURL());
            }
        }
    }
}