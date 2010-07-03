package org.jtheque.views.impl.controllers;

import org.jtheque.ui.utils.AbstractController;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.utils.ui.SimpleSwingWorker;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.utils.ui.edt.SimpleTask;
import org.jtheque.views.able.windows.IMessageView;
import org.jtheque.views.able.windows.IUpdateView;

import javax.annotation.Resource;

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

public class UpdateController extends AbstractController {
    @Resource
    private IUpdateView updateView;

    @Resource
    private IUpdateService updateService;

    private void update() {
        new UpdateWorker().start();
    }

    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(1);

        translations.put("update.actions.update", "update");

        return translations;
    }

    private final class UpdateWorker extends SimpleSwingWorker {
        @Override
        protected void before() {
            updateView.startWait();
        }

        @Override
        protected void doWork() {
            if (updateView.getMode() == IUpdateView.Mode.KERNEL) {
                updateService.updateCore(updateView.getSelectedVersion());
            } else if (updateView.getMode() == IUpdateView.Mode.MODULE) {
                updateService.update(updateView.getModule(), updateView.getSelectedVersion());
            }
        }

        @Override
        public void done() {
            updateView.stopWait();
        }
    }
}