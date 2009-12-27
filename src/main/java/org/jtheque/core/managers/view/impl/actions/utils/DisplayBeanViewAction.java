package org.jtheque.core.managers.view.impl.actions.utils;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.view.able.IView;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;

import java.awt.event.ActionEvent;

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

public class DisplayBeanViewAction extends JThequeAction {
    private final String view;

    /**
     * Construct a new DisplayViewAction with a specific internationalization key.
     *
     * @param key The i18n key.
     * @param view The name of the view to display. The view will be searched in the Spring context.
     */
    public DisplayBeanViewAction(String key, String view) {
        super(key);

        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Managers.getManager(IBeansManager.class).<IView>getBean(view).display();
    }
}