package org.jtheque.views.impl.actions.module.update;

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

import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.update.IUpdateManager;
import org.jtheque.views.ViewsServices;
import org.jtheque.views.able.IViewManager;
import org.jtheque.views.able.frame.IUpdateView;
import org.jtheque.ui.utils.edt.SimpleTask;

import java.awt.event.ActionEvent;

/**
 * An action to validate the update view.
 *
 * @author Baptiste Wicht
 */
public final class AcValidateUpdateView extends JThequeAction {
    /**
     * Construct a new AcValidateUpdateView.
     */
    public AcValidateUpdateView() {
        super("update.actions.update");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ViewsServices.get(IUIUtils.class).execute(new SimpleTask() {
            @Override
            public void run() {
                ViewsServices.get(IViewManager.class).getViews().getUpdateView().startWait();

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
            IUpdateView updateView = ViewsServices.get(IViewManager.class).getViews().getUpdateView();

            if (updateView.getMode() == IUpdateView.Mode.KERNEL) {
                ViewsServices.get(IUpdateManager.class).update(updateView.getSelectedVersion());
            } else if (updateView.getMode() == IUpdateView.Mode.MODULE) {
                ViewsServices.get(IUpdateManager.class).update(updateView.getModule(), updateView.getSelectedVersion());
            } else {
                ViewsServices.get(IUpdateManager.class).update(updateView.getUpdatable(), updateView.getSelectedVersion());
            }

            ViewsServices.get(IUIUtils.class).execute(new StopWaitTask());
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
            ViewsServices.get(IViewManager.class).getViews().getUpdateView().stopWait();
        }
    }
}