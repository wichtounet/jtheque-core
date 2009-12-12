package org.jtheque.core.managers.view.impl.actions.module.update;

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
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.update.IUpdateView;
import org.jtheque.core.managers.view.able.update.IUpdateView.Mode;
import org.jtheque.core.managers.view.edt.SimpleTask;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to validate the update view.
 *
 * @author Baptiste Wicht
 */
public final class AcValidateUpdateView extends JThequeAction {
    private static final long serialVersionUID = -8738725543815418407L;

    @Resource
    private IUpdateView updateView;

    /**
     * Construct a new AcValidateUpdateView.
     */
    public AcValidateUpdateView() {
        super("update.actions.update");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Managers.getManager(IViewManager.class).execute(new SimpleTask() {
            @Override
            public void run() {
                updateView.startWait();

                new Thread(new UpdateRunnable()).start();
            }
        });
    }

    /**
     * A runnable to update the module or the kernel.
     *
     * @author Baptiste Wicht
     */
    private final class UpdateRunnable implements Runnable {
        @Override
        public void run() {
            if (updateView.getMode() == Mode.KERNEL) {
                Managers.getManager(IUpdateManager.class).update(updateView.getSelectedVersion());
            } else if (updateView.getMode() == Mode.MODULE) {
                Managers.getManager(IUpdateManager.class).update(updateView.getModule(), updateView.getSelectedVersion());
            } else {
                Managers.getManager(IUpdateManager.class).update(updateView.getUpdatable(), updateView.getSelectedVersion());
            }

            Managers.getManager(IViewManager.class).execute(new StopWaitTask());
        }
    }

    /**
     * A task to stop the wait progress of the view.
     *
     * @author Baptiste Wicht
     */
    private final class StopWaitTask extends SimpleTask {
        @Override
        public void run() {
            updateView.stopWait();
        }
    }
}