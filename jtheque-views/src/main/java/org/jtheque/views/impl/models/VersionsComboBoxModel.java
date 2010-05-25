package org.jtheque.views.impl.models;

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

import org.jtheque.ui.utils.models.SimpleListModel;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.update.able.Updatable;
import org.jtheque.utils.bean.Version;
import org.jtheque.modules.able.Module;

/**
 * A combo box model to display versions.
 *
 * @author Baptiste Wicht
 */
public final class VersionsComboBoxModel extends SimpleListModel<Version> {
    private Mode mode;
    private Module currentModule;
    private Updatable currentUpdatable;

    private final IUpdateService updateService;

    /**
     * Construct a new VersionsComboBoxModel.
     * 
     * @param updateService The update service to use.
     */
    public VersionsComboBoxModel(IUpdateService updateService) {
        super();

        this.updateService = updateService;
    }

    /**
     * The mode of the model.
     */
    enum Mode {
        KERNEL,
        MODULE,
        UPDATABLE
    }

    /**
     * Load kernel versions into the model.
     */
    public void loadKernelVersions() {
        if (mode != Mode.KERNEL) {
            mode = Mode.KERNEL;

            setElements(updateService.getKernelVersions());
        }
    }

    /**
     * Load module versions into the model.
     *
     * @param value The module to load the versions from.
     */
    public void loadModuleVersions(Module value) {
        if (mode != Mode.MODULE || !currentModule.equals(value)) {
            mode = Mode.MODULE;
            currentModule = value;

            setElements(updateService.getVersions(currentModule));
        }
    }

    /**
     * Load module versions into the model.
     *
     * @param value The module to load the versions from.
     */
    public void loadUpdatableVersions(Updatable value) {
        if (mode != Mode.UPDATABLE || !currentUpdatable.equals(value)) {
            mode = Mode.UPDATABLE;
            currentUpdatable = value;

            setElements(updateService.getVersions(currentUpdatable));
        }
    }
}