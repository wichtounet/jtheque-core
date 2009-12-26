package org.jtheque.core.managers.view.impl.actions;

import org.jtheque.core.managers.view.able.IView;
import org.jtheque.core.managers.view.impl.actions.utils.CloseBeanViewAction;
import org.jtheque.core.managers.view.impl.actions.utils.CloseViewAction;
import org.jtheque.core.managers.view.impl.actions.utils.DisplayBeanViewAction;
import org.jtheque.core.managers.view.impl.actions.utils.DisplayViewAction;

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

public class ActionFactory {
    private ActionFactory(){
        super();
    }

    public static Action createCloseViewAction(String key, IView view){
        return new CloseViewAction(key, view);
    }

    public static Action createCloseViewAction(String key, String view){
        return new CloseBeanViewAction(key, view);
    }

    public static Action createDisplayViewAction(String key, IView view){
        return new DisplayViewAction(key, view);
    }

    public static Action createDisplayViewAction(String key, String view){
        return new DisplayBeanViewAction(key, view);
    }
}