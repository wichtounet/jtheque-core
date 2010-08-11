package org.jtheque.views;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.awt.Window;

/**
 * A view manager.
 *
 * @author Baptiste Wicht
 */
public interface ViewService {
    /**
     * Display the about view.
     */
    void displayAboutView();

    /**
     * Display the choose collection view.
     */
    void displayCollectionView();

    /**
     * Save the current state of the window.
     *
     * @param window The window.
     * @param name   The name of the view.
     */
    void saveState(Window window, String name);

    /**
     * Configure a view. It seems sets the size and location of the view.
     *
     * @param window        The window to configure.
     * @param name          The name of the view.
     * @param defaultWidth  The default width of the view.
     * @param defaultHeight The default height of the view.
     */
    void configureView(Window window, String name, int defaultWidth, int defaultHeight);

    /**
     * Set size of the view considering the configuration of the view.
     *
     * @param view          The view to configure. ̀
     * @param defaultWidth  The default width of the view.
     * @param defaultHeight The default height of the view.
     */
    void setSize(Window view, int defaultWidth, int defaultHeight);

    /**
     * Fill the configuration with the view informations.
     *
     * @param configuration The configuration to fill.
     * @param view          The view to fill the configuration with.
     */
    void fill(WindowConfiguration configuration, Window view);

    /**
     * Configure the view with the window configuration.
     *
     * @param configuration The window configuration.
     * @param view          The view to configure.
     */
    void configure(WindowConfiguration configuration, Window view);

    /**
     * Close the collection view. 
     */
    void closeCollectionView();
}