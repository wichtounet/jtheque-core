package org.jtheque.views.impl.actions.about;

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

import org.jtheque.core.ICore;
import org.jtheque.spring.utils.injection.Injectable;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.able.IViewService;

import javax.annotation.Resource;
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
     * @param viewService
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