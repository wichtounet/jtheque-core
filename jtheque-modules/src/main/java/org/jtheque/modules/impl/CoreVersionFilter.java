package org.jtheque.modules.impl;

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
import org.jtheque.modules.able.Module;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.Filter;

/**
 * A filter for the modules to filter with the version of the module and the core.
 *
 * @author Baptiste Wicht
 */
final class CoreVersionFilter implements Filter<Module> {
    private final ICore core;
    private final IUIUtils uiUtils;

    /**
     * Construct a new CoreVersionFilter.
     *
     * @param core    The core.
     * @param uiUtils The uiUtils.
     */
    CoreVersionFilter(ICore core, IUIUtils uiUtils) {
        super();

        this.core = core;
        this.uiUtils = uiUtils;
    }

    @Override
    public boolean accept(Module module) {
        Version neededVersion = module.getCoreVersion();
        Version currentVersion = ICore.VERSION;

        if (neededVersion != null && !currentVersion.equals(neededVersion)) {
            if (neededVersion.isGreaterThan(currentVersion)) {
                uiUtils.displayI18nText("error.module.core.version.greater");

                return false;
            } else if (!currentVersion.equals(neededVersion) && core.isNotCompatibleWith(neededVersion)) {
                uiUtils.displayI18nText("error.module.core.version.compatibility.no");

                return false;
            }
        }

        return true;
    }
}
