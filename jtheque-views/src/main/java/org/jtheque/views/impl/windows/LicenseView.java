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

import org.jtheque.core.able.ICore;
import org.jtheque.ui.able.IModel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.windows.dialogs.SwingFilthyBuildedDialogView;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.able.IViewService;
import org.jtheque.views.able.windows.ILicenseView;

/**
 * A view to display the license.
 *
 * @author Baptiste Wicht
 */
public final class LicenseView extends SwingFilthyBuildedDialogView<IModel> implements ILicenseView {
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    @Override
    protected void initView() {
        setTitleKey("license.view.title", getService(ICore.class).getApplication().getName());
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        builder.addScrolledTextArea(FileUtils.getTextOf(getService(ICore.class).getApplication().getLicenseFilePath()),
                builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.BELOW_BASELINE_LEADING, 1.0, 1.0));

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL),
                getControllerAction("license.actions.print"),
                getCloseAction("license.actions.close"));

        getService(IViewService.class).configureView(this, "license", DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}