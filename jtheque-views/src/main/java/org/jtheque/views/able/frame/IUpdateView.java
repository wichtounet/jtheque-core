package org.jtheque.views.able.frame;

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

import org.jtheque.modules.able.Module;
import org.jtheque.ui.IView;
import org.jtheque.ui.WaitableView;
import org.jtheque.update.Updatable;
import org.jtheque.utils.bean.Version;

/**
 * An update view specification.
 *
 * @author Baptiste Wicht
 */
public interface IUpdateView extends IView, WaitableView {
    /**
     * The mode of the update view.
     */
    enum Mode {
        KERNEL,
        MODULE,
        UPDATABLE
    }

    /**
     * Return the current mode of the view.
     *
     * @return The current mode of the view.
     */
    Mode getMode();

    /**
     * Return the selected version.
     *
     * @return The selected version.
     */
    Version getSelectedVersion();

    /**
     * Return the current module.
     *
     * @return The current module.
     */
    Module getModule();

    /**
     * Return the current updatable.
     *
     * @return The current updatable.
     */
    Updatable getUpdatable();
}
