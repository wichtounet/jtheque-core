package org.jtheque.views.impl.actions.module;

import org.jtheque.update.IUpdateManager;
import org.jtheque.update.Updatable;
import org.jtheque.views.ViewsServices;
import org.jtheque.views.able.IViewManager;

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
public final class UpdateUpdatableAction extends AbstractUpdateAction {
    /**
     * Construct a new UpdateModuleAction.
     */
    public UpdateUpdatableAction() {
        super("modules.actions.update");
    }

    @Override
    boolean isUpToDate() {
        return ViewsServices.get(IUpdateManager.class).isUpToDate(getSelectedObject());
    }

    @Override
    String getMessage() {
        return "updatable";
    }

    @Override
    Updatable getSelectedObject() {
        return ViewsServices.get(IViewManager.class).getViews().getModuleView().getSelectedUpdatable();
    }
}