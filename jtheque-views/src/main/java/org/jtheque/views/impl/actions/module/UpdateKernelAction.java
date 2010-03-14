package org.jtheque.views.impl.actions.module;

import org.jtheque.update.IUpdateManager;
import org.jtheque.views.ViewsServices;

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
 * An action to update the kernel.
 *
 * @author Baptiste Wicht
 */
public final class UpdateKernelAction extends AbstractUpdateAction {
    /**
     * Construct a new UpdateKernelAction.
     */
    public UpdateKernelAction() {
        super("modules.actions.update.kernel");
    }

    @Override
    boolean isUpToDate() {
        return ViewsServices.get(IUpdateManager.class).isCurrentVersionUpToDate();
    }

    @Override
    String getMessage() {
        return "kernel";
    }

    @Override
    Object getSelectedObject() {
        return null;
    }
}