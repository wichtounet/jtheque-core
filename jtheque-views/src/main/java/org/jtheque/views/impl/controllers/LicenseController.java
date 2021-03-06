package org.jtheque.views.impl.controllers;

import org.jtheque.core.Core;
import org.jtheque.ui.Action;
import org.jtheque.ui.utils.AbstractController;
import org.jtheque.ui.utils.BetterSwingWorker;
import org.jtheque.utils.print.PrintUtils;
import org.jtheque.views.windows.LicenseView;

import javax.annotation.Resource;

import java.io.File;

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

/**
 * A controller for the license view.
 *
 * @author Baptiste Wicht
 */
public class LicenseController extends AbstractController<LicenseView> {
    @Resource
    private Core core;

    /**
     * Construct a new LicenseController.
     */
    public LicenseController() {
        super(LicenseView.class);
    }

    /**
     * Close the view.
     */
    @Action("license.actions.close")
    public void close() {
        getView().closeDown();
    }

    /**
     * Print the license.
     */
    @Action("license.actions.print")
    public void print() {
        new PrintWorker().execute();
    }

    /**
     * A simple swing worker to print the license and make the license view waiting during the print operation.
     *
     * @author Baptiste Wicht
     */
    private final class PrintWorker extends BetterSwingWorker {
        @Override
        protected void before() {
            getView().getWindowState().startWait();
        }

        @Override
        protected void doInBackground() {
            PrintUtils.printLineFiles(new File(core.getApplication().getLicenseFilePath()));
        }

        @Override
        protected void done() {
            getView().getWindowState().stopWait();
        }
    }
}