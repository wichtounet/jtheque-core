package org.jtheque.views.impl.actions.about;

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
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.able.IViewService;

import java.awt.event.ActionEvent;

/**
 * Action to display the about view.
 *
 * @author Baptiste Wicht
 */
public final class DisplayAboutViewAction extends JThequeAction {
    private final IViewService viewService;

    /**
     * Construct a new DisplayAboutViewAction.
     *
     * @param core The core service.
     *
     * @param viewService The view service. 
     */
    public DisplayAboutViewAction(ICore core, IViewService viewService) {
        super("about.actions.display", core.getApplication().getName());

        this.viewService = viewService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        viewService.displayAboutView();
    }
}