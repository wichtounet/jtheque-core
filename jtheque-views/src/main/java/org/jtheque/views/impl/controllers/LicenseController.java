package org.jtheque.views.impl.controllers;

import org.jtheque.core.utils.SystemProperty;
import org.jtheque.ui.utils.AbstractController;
import org.jtheque.utils.print.PrintUtils;
import org.jtheque.utils.ui.SimpleSwingWorker;
import org.jtheque.views.able.windows.ILicenseView;

import javax.annotation.Resource;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
public class LicenseController extends AbstractController {
    @Resource
    private ILicenseView licenseView;

    private void print() {
        new PrintWorker().start();
    }
    
    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(1);

        translations.put("license.actions.print", "print");

        return translations;
    }

    private final class PrintWorker extends SimpleSwingWorker {
        @Override
        protected void before() {
            licenseView.getWindowState().startWait();
        }

        @Override
        protected void doWork() {
            PrintUtils.printLineFiles(new File(SystemProperty.USER_DIR.get(), "license.txt"));
        }

        @Override
        protected void done() {
            licenseView.getWindowState().stopWait();
        }
    }
}