package org.jtheque.core.managers.view.impl.components;

import org.jtheque.core.managers.view.ViewComponent;

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
 * A view container.
 *
 * @author Baptiste Wicht
 */
public final class ViewContainer implements ViewComponent {
    private final Object viewComponent;

    /**
     * Construct a new <code>ViewContainer</code>.
     *
     * @param viewComponent The view component to contain.
     */
    public ViewContainer(Object viewComponent) {
        super();

        this.viewComponent = viewComponent;
    }

    /**
     * Return the view component.
     *
     * @return The view component.
     */
    public Object getViewComponent() {
        return viewComponent;
    }

    @Override
    public Object getImpl() {
        return viewComponent;
    }
}
