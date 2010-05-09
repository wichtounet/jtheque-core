package org.jtheque.views.impl.actions.about;

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
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.utils.ui.edt.SimpleTask;
import org.jtheque.utils.print.PrintUtils;
import org.jtheque.views.able.windows.ILicenceView;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to print the licence of the application.
 *
 * @author Baptiste Wicht
 */
public final class PrintLicenseAction extends JThequeAction {
    @Resource
    private ILicenceView licenceView;

    /**
     * Construct a new AcPrintLicense.
     */
    public PrintLicenseAction() {
        super("licence.actions.print");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        SwingUtils.execute(new SimpleTask() {
            @Override
            public void run() {
                licenceView.startWait();

                new Thread(new PrintRunnable()).start();
            }
        });
    }

    /**
     * A runnable to print the license.
     *
     * @author Baptiste Wicht
     */
    private final class PrintRunnable implements Runnable {
        @Override
        public void run() {
            //TODO Review that
            PrintUtils.printLineFiles(getClass().getClassLoader().
                    getResourceAsStream("org/jtheque/core/resources/others/licence.txt"));

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
            licenceView.stopWait();
        }
    }
}