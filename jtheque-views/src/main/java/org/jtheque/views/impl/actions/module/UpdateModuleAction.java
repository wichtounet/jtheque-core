package org.jtheque.views.impl.actions.module;

import org.jtheque.modules.able.Module;
import org.jtheque.update.IUpdateService;
import org.jtheque.views.ViewsServices;
import org.jtheque.views.able.IViewService;

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
 * An action to update a module.
 *
 * @author Baptiste Wicht
 */
public final class UpdateModuleAction extends AbstractUpdateAction {
    /**
     * Construct a new UpdateModuleAction.
     */
    public UpdateModuleAction() {
        super("modules.actions.update");
    }

    @Override
    boolean isUpToDate() {
        return ViewsServices.get(IUpdateService.class).isUpToDate(getSelectedObject());
    }

    @Override
    String getMessage() {
        return "module";
    }

    @Override
    Module getSelectedObject() {
        return ViewsServices.get(IViewService.class).getViews().getModuleView().getSelectedModule();
    }
}