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

import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleListener;
import org.jtheque.ui.utils.models.SimpleListModel;

/**
 * A List model to display the modules.
 *
 * @author Baptiste Wicht
 */
public final class ModuleListModel extends SimpleListModel<Module> implements ModuleListener {
    /**
     * Construct a new ModuleListModel.
     *
     * @param moduleService The module service to use. 
     */
    public ModuleListModel(IModuleService moduleService) {
        super();

        moduleService.addModuleListener("", this);

        setElements(moduleService.getModules());
    }

	@Override
	public void moduleStarted(Module module) {
		int index = getIndexOfElement(module);

		fireContentsChanged(this, index, index);
	}

	@Override
	public void moduleStopped(Module module) {
		int index = getIndexOfElement(module);

		fireContentsChanged(this, index, index);
	}

	@Override
	public void moduleInstalled(Module module) {
		addElement(module);
	}

	@Override
	public void moduleUninstalled(Module module) {
		removeElement(module);
	}
}