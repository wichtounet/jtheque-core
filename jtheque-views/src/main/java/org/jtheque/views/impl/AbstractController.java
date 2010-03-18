package org.jtheque.views.impl;

import org.jtheque.views.able.Controller;

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
 * An abstract controller.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractController implements Controller {
    @Override
    public final void closeView() {
        getView().closeDown();
    }

    @Override
    public void displayView() {
        getView().display();
    }
}