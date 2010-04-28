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

import org.jtheque.ui.able.IView;
import org.jtheque.views.able.panel.ICollectionView;
import org.jtheque.views.impl.WindowConfiguration;

/**
 * A view manager.
 *
 * @author Baptiste Wicht
 */
public interface IViewService {
    /**
     * Display the about view.
     */
    void displayAboutView();

    /**
     * Display the choose collection view.
     */
    void displayCollectionView();

    /**
     * Return the collection view.
     *
     * @return The collection view.
     */
    ICollectionView getCollectionView();

    /**
     * Save the current state of the window.
     *
     * @param window The window.
     * @param name   The name of the view.
     */
    void saveState(IView window, String name);

    /**
     * Configure a view. It seems sets the size and location of the view.
     *
     * @param window        The window to configure.
     * @param name          The name of the view.
     * @param defaultWidth  The default width of the view.
     * @param defaultHeight The default height of the view.
     */
    void configureView(IView window, String name, int defaultWidth, int defaultHeight);

    /**
     * Set size of the view considering the configuration of the view.
     *
     * @param view          The view to configure. ̀
     * @param defaultWidth  The default width of the view.
     * @param defaultHeight The default height of the view.
     */
    void setSize(IView view, int defaultWidth, int defaultHeight);

    /**
     * Fill the configuration with the view informations.
     *
     * @param configuration The configuration to fill.
     * @param view          The view to fill the configuration with.
     */
    void fill(WindowConfiguration configuration, IView view);

    /**
     * Configure the view with the window configuration.
     *
     * @param configuration The window configuration.
     * @param view          The view to configure.
     */
    void configure(WindowConfiguration configuration, IView view);
}