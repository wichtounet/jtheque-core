package org.jtheque.views.impl.windows;

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

import org.jtheque.core.Core;
import org.jtheque.ui.Model;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.windows.dialogs.SwingFilthyBuildedDialogView;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.windows.LicenseView;

/**
 * A view to display the license.
 *
 * @author Baptiste Wicht
 */
public final class SwingLicenseView extends SwingFilthyBuildedDialogView<Model> implements LicenseView {
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    @Override
    protected void initView() {
        setTitleKey("license.view.title", getService(Core.class).getApplication().getI18nProperties().getName());
        setDefaults("license", DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        builder.addScrolledTextArea(FileUtils.getTextOf(getService(Core.class).getApplication().getLicenseFilePath()),
                builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.BELOW_BASELINE_LEADING, 1.0, 1.0));

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL),
                getAction("license.actions.print"),
                getAction("license.actions.close"));
    }
}