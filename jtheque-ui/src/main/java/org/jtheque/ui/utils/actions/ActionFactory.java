package org.jtheque.ui.utils.actions;

import org.jtheque.ui.able.IView;
import org.jtheque.ui.utils.actions.CloseBeanViewAction;
import org.jtheque.ui.utils.actions.CloseViewAction;
import org.jtheque.ui.utils.actions.DisplayBeanViewAction;
import org.jtheque.ui.utils.actions.DisplayViewAction;

import javax.swing.Action;

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
 * A factory to create generic actions.
 *
 * @author Baptiste Wicht
 */
public final class ActionFactory {
    /**
     * Construct a new ActionFactory.
     */
    private ActionFactory(){
        super();
    }

    /**
     * Create an action to close the view.
     *
      *@param key The i18n key.
     * @param view The view to close.
     *
     * @return An action to close the view.
     */
    public static Action createCloseViewAction(String key, IView view){
        return new CloseViewAction(key, view);
    }

    /**
     * Create an action to close the view.
     *
      *@param key The i18n key.
     * @param view The name of the view to close. The action will be searched in Spring context.
     *
     * @return An action to close the view.
     */
    public static Action createCloseViewAction(String key, String view){
        return new CloseBeanViewAction(key, view);
    }

    /**
     * Create an action to display the view.
     *
      *@param key The i18n key.
     * @param view The view to close.
     *
     * @return An action to close the view.
     */
    public static Action createDisplayViewAction(String key, IView view){
        return new DisplayViewAction(key, view);
    }

    /**
     * Create an action to display the view.
     *
      *@param key The i18n key. 
     * @param view The name of the view to close. The action will be searched in Spring context.
     *
     * @return An action to close the view.
     */
    public static Action createDisplayViewAction(String key, String view){
        return new DisplayBeanViewAction(key, view);
    }
}