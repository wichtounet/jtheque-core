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

import org.jtheque.modules.able.Module;
import org.jtheque.ui.able.IModel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.filthy.FilthyRenderer;
import org.jtheque.ui.utils.windows.dialogs.SwingFilthyBuildedDialogView;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.update.able.Updatable;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.able.windows.IUpdateView;
import org.jtheque.views.impl.actions.module.update.AcValidateUpdateView;
import org.jtheque.views.impl.models.VersionsComboBoxModel;

/**
 * An org.jtheque.update view.
 *
 * @author Baptiste Wicht
 */
public final class UpdateView extends SwingFilthyBuildedDialogView<IModel> implements IUpdateView {
    private VersionsComboBoxModel model;

    private Mode mode = Mode.KERNEL;
    private Module module;
    private Updatable updatable;

    @Override
    protected void initView() {
        setTitleKey("update.view.title");
        setResizable(false);
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        builder.addI18nLabel("update.view.versions", builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL));

        model = new VersionsComboBoxModel(getService(IUpdateService.class));

        builder.addComboBox(model, new FilthyRenderer(), builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL));

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL), new AcValidateUpdateView(), getCloseAction("update.actions.cancel"));
    }

    @Override
    public void sendMessage(String message, Object value) {
        if ("kernel".equals(message)) {
            mode = Mode.KERNEL;

            model.loadKernelVersions();
        } else if ("module".equals(message)) {
            mode = Mode.MODULE;

            module = (Module) value;
            model.loadModuleVersions(module);
        } else if ("updatable".equals(message)) {
            mode = Mode.UPDATABLE;

            updatable = (Updatable) value;
            model.loadUpdatableVersions(updatable);
        }
    }

    @Override
    public Mode getMode() {
        return mode;
    }

    @Override
    public Version getSelectedVersion() {
        return model.getSelectedItem();
    }

    @Override
    public Module getModule() {
        return module;
    }

    @Override
    public Updatable getUpdatable() {
        return updatable;
    }
}