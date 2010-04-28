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

import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.update.IUpdateService;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.utils.ui.edt.SimpleTask;
import org.jtheque.views.able.windows.IUpdateView;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to validate the org.jtheque.update view.
 *
 * @author Baptiste Wicht
 */
public final class AcValidateUpdateView extends JThequeAction {
    @Resource
    private IUpdateView updateView;

    @Resource
    private IUpdateService updateService;

    /**
     * Construct a new AcValidateUpdateView.
     */
    public AcValidateUpdateView() {
        super("update.actions.update");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtils.execute(new SimpleTask() {
            @Override
            public void run() {
                updateView.startWait();

                new Thread(new UpdateRunnable()).start();
            }
        });
    }

    /**
     * A runnable to org.jtheque.update the module or the kernel.
     *
     * @author Baptiste Wicht
     */
    private final class UpdateRunnable implements Runnable {
        @Override
        public void run() {
            if (updateView.getMode() == IUpdateView.Mode.KERNEL) {
                updateService.update(updateView.getSelectedVersion());
            } else if (updateView.getMode() == IUpdateView.Mode.MODULE) {
                updateService.update(updateView.getModule(), updateView.getSelectedVersion());
            } else {
                updateService.update(updateView.getUpdatable(), updateView.getSelectedVersion());
            }

            SwingUtils.execute(new StopWaitTask());
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