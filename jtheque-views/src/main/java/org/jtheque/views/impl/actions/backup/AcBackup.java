package org.jtheque.views.impl.actions.backup;

import org.jtheque.file.able.IFileService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.ui.SwingUtils;

import javax.annotation.Resource;

import java.awt.event.ActionEvent;
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

/**
 * An abstract action to backup.
 *
 * @author Baptiste Wicht
 */
public class AcBackup extends JThequeAction {
    @Resource
    private IUIUtils uiUtils;

    @Resource
    private IFileService fileService;

    /**
     * Construct a new AcBackup.
     */
    public AcBackup() {
        super("menu.backup");
    }

    @Override
    public final void actionPerformed(ActionEvent e) {
        final boolean yes = uiUtils.askI18nUserForConfirmation(
                "dialogs.confirm.backup", "dialogs.confirm.backup.title");

        if (yes) {
            File file = new File(SwingUtils.chooseFile(new SimpleFilter("XML(*.xml)", ".xml")));

            fileService.backup(file);
        }
    }
}