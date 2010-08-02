package org.jtheque.ui.able;

import javax.swing.Action;

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

/**
 * A window view.
 *
 * @author Baptiste Wicht
 */
public interface WindowView extends View {
    /**
     * Return the width of the window.
     *
     * @return The width of the window.
     */
    int getWidth();

    /**
     * Return the height of the window.
     *
     * @return The height of the window.
     */
    int getHeight();

    /**
     * Return the controller action with the given i18n key.
     *
     * @param key The key.
     *
     * @return The action bind to the controller of the view. 
     */
    Action getAction(String key);

    /**
     * Return the window state.
     *
     * @return The window state. 
     */
    WindowState getWindowState();
}