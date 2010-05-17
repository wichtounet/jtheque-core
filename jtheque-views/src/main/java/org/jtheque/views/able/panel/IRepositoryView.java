package org.jtheque.views.able.panel;

import org.jtheque.modules.able.IModuleDescription;
import org.jtheque.ui.able.IView;

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
 * @author Baptiste Wicht
 */
public interface IRepositoryView extends IView {
    /**
     * Return the selected module.
     *
     * @return The selected module.
     */
    IModuleDescription getSelectedModule();

    /**
     * Expand the selected module.
     */
    void expandSelectedModule();
}
