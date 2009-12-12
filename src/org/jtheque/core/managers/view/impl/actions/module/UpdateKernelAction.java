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
import org.jtheque.core.managers.update.IUpdateManager;

/**
 * An action to update the kernel.
 *
 * @author Baptiste Wicht
 */
public final class UpdateKernelAction extends AbstractUpdateAction {
    private static final long serialVersionUID = 3190615300122331869L;

    /**
     * Construct a new UpdateKernelAction.
     */
    public UpdateKernelAction() {
        super("modules.actions.update.kernel");
    }

    @Override
    boolean isUpToDate() {
        return Managers.getManager(IUpdateManager.class).isCurrentVersionUpToDate();
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