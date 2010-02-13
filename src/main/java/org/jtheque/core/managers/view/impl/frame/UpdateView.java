package org.jtheque.core.managers.view.impl.frame;

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

import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.update.Updatable;
import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.core.managers.view.able.update.IUpdateView;
import org.jtheque.core.managers.view.impl.actions.module.update.AcValidateUpdateView;
import org.jtheque.core.managers.view.impl.components.model.VersionsComboBoxModel;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingBuildedDialogView;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.ui.GridBagUtils;

/**
 * An update view.
 *
 * @author Baptiste Wicht
 */
public final class UpdateView extends SwingBuildedDialogView<IModel> implements IUpdateView {
    private VersionsComboBoxModel model;

    private Mode mode = Mode.KERNEL;
    private ModuleContainer module;
    private Updatable updatable;

    /**
     * Construct a new UpdateView. 
     */
    public UpdateView(){
        super();

        build();
    }

    @Override
    protected void initView(){
        setTitleKey("update.view.title");
        setResizable(false);
    }

    @Override
    protected void buildView(I18nPanelBuilder builder){
        builder.addI18nLabel("update.view.versions", builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL));

        model = new VersionsComboBoxModel();

        builder.addComboBox(model, builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL));

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL),
                new AcValidateUpdateView(), getCloseAction("update.actions.cancel"));
    }

    @Override
    public void sendMessage(String message, Object value) {
        if ("kernel".equals(message)) {
            mode = Mode.KERNEL;

            model.loadKernelVersions();
        } else if ("module".equals(message)) {
            mode = Mode.MODULE;

            module = (ModuleContainer) value;
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
        return model.getSelectedVersion();
    }

    @Override
    public ModuleContainer getModule() {
        return module;
    }

    @Override
    public Updatable getUpdatable() {
        return updatable;
    }
}