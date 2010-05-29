package org.jtheque.ui.utils.windows.dialogs;

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

import org.jtheque.ui.able.IFilthyUtils;
import org.jtheque.ui.able.IModel;
import org.jtheque.ui.utils.builders.FilthyPanelBuilder;
import org.jtheque.ui.utils.builders.JThequePanelBuilder;
import org.jtheque.ui.utils.filthy.FilthyBackgroundPanel;

/**
 * A swing dialog view.
 *
 * @author Baptiste Wicht
 */
public abstract class SwingFilthyBuildedDialogView<T extends IModel> extends SwingBuildedDialogView<T> {
    @Override
    JThequePanelBuilder createBuilder() {
        return new FilthyPanelBuilder(new FilthyBackgroundPanel(getService(IFilthyUtils.class)));
    }
}