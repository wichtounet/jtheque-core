package org.jtheque.modules.able;

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

import java.util.EventListener;

/**
 * A Module Listener.
 *
 * @author Baptiste Wicht
 */
public interface ModuleListener extends EventListener {
	/**
	 * Invoked when a module is started.
	 *
	 * @param module The started module.
	 */
	void moduleStarted(Module module);

	/**
	 * Invoked when a module is stopped.
	 *
	 * @param module The stopped module.
	 */
	void moduleStopped(Module module);

	/**
	 * Invoked when a module is installed.
	 *
	 * @param module The installed module.
	 */
	void moduleInstalled(Module module);

	/**
	 * Invoked when a module is uninstalled.
	 *
	 * @param module The uninstalled module.
	 */
	void moduleUninstalled(Module module);
}