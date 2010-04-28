package org.jtheque.ui.utils.actions;

import org.jtheque.ui.able.IView;
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
 * An action to close the view.
 *
 * @author Baptiste Wicht
 */
public final class CloseBeanViewAction extends JThequeAction implements ApplicationContextAware {
    private final String view;
    private ApplicationContext applicationContext;

    /**
     * Construct a new CloseViewAction with a specific internationalization key.
     *
     * @param key The i18n key.
     * @param view The view to close, it's the spring id.
     */
    public CloseBeanViewAction(String key, String view) {
        super(key);

        this.view = view;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((IView)applicationContext.getBean(view)).closeDown();
    }
}