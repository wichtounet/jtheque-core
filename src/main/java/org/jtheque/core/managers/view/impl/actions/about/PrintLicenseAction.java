package org.jtheque.core.managers.view.impl.actions.about;

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
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.view.able.ILicenceView;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.edt.SimpleTask;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.utils.print.PrintUtils;

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

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        Managers.getManager(IViewManager.class).execute(new SimpleTask() {
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
            PrintUtils.printLineFiles(getClass().getClassLoader().
                    getResourceAsStream("org/jtheque/core/resources/others/licence.txt"));

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
            licenceView.stopWait();
        }
    }
}