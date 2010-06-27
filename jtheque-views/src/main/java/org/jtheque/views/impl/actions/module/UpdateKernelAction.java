package org.jtheque.views.impl.actions.module;

import org.jtheque.errors.able.IErrorService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.views.able.IViews;

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
 * An action to org.jtheque.update the kernel.
 *
 * @author Baptiste Wicht
 */
public final class UpdateKernelAction extends AbstractUpdateAction {
    /**
     * Construct a new UpdateKernelAction.
     */
    public UpdateKernelAction(IUpdateService updateService, IErrorService errorService, IUIUtils uiUtils, IViews views) {
        super("modules.actions.update.kernel", updateService, uiUtils, views);
    }

    @Override
    boolean isUpToDate() {
        return getUpdateService().isCurrentVersionUpToDate();
    }

    @Override
    String getMessage() {
        return "kernel";
    }

    @Override
    Object getSelectedObject() {
        return null;
    }
}