package org.jtheque.views.impl.actions.about;

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

import org.jtheque.ui.IUIUtils;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.ViewsServices;
import org.jtheque.views.able.IViewManager;
import org.jtheque.ui.utils.edt.SimpleTask;
import org.jtheque.utils.print.PrintUtils;

import java.awt.event.ActionEvent;

/**
 * An action to print the licence of the application.
 *
 * @author Baptiste Wicht
 */
public final class PrintLicenseAction extends JThequeAction {
    /**
     * Construct a new AcPrintLicense.
     */
    public PrintLicenseAction() {
        super("licence.actions.print");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        ViewsServices.get(IUIUtils.class).execute(new SimpleTask() {
            @Override
            public void run() {
                ViewsServices.get(IViewManager.class).getViews().getLicenceView().startWait();

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
            PrintUtils.printLineFiles(getClass().getClassLoader().
                    getResourceAsStream("org/jtheque/core/resources/others/licence.txt"));

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
            ViewsServices.get(IViewManager.class).getViews().getLicenceView().stopWait();
        }
    }
}