package org.jtheque.ui.utils.actions;

import org.jtheque.ui.IView;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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

/**
 * An action to display a view.
 *
 * @author Baptiste Wicht
 */
public final class DisplayBeanViewAction extends JThequeAction implements ApplicationContextAware {
    private final String view;
    private ApplicationContext applicationContext;

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
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((IView)applicationContext.getBean(view)).display();
    }
}