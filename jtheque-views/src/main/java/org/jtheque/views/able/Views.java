package org.jtheque.views.able;

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

import org.jtheque.views.able.components.MainComponent;
import org.jtheque.views.able.frame.*;
import org.jtheque.views.able.panel.IModuleView;
import org.jtheque.views.able.panel.IRepositoryView;

/**
 * A views manager.
 *
 * @author Baptiste Wicht
 */
public interface Views {
    /**
     * Return the main view.
     *
     * @return the main view.
     */
    IMainView getMainView();

    /**
     * Return the licence view.
     *
     * @return The licence view.
     */
    ILicenceView getLicenceView();

    /**
     * Return the config view.
     *
     * @return The config view.
     */
    IConfigView getConfigView();

    /**
     * Set the selected view.
     *
     * @param component The view to select.
     */
    void setSelectedView(MainComponent component);

    /**
     * Return the selected view.
     *
     * @return The selected view.
     */
    MainComponent getSelectedView();

    /**
     * Return the module view.
     *
     * @return the module view.
     */
    IModuleView getModuleView();

    /**
     * Return the messages view.
     *
     * @return the messages view.
     */
    IMessageView getMessagesView();

    ILogView getLogView();

    IRepositoryView getRepositoryView();

    IUpdateView getUpdateView();
}