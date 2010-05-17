package org.jtheque.views.impl.actions.module.update;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.update.able.IUpdateService;
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