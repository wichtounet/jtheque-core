package org.jtheque.ui.utils.actions;

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

import org.jtheque.ui.able.IView;

import java.awt.event.ActionEvent;

/**
 * A generic close view action.
 *
 * @author Baptiste Wicht
 */
public final class CloseViewAction extends JThequeAction {
    private IView view;

    /**
     * Construct a new CloseViewAction with a specific internationalization key.
     *
     * @param key The i18n key.
     */
    public CloseViewAction(String key) {
        super(key);
    }

    /**
     * Construct a new CloseViewAction with a specific internationalization key.
     *
     * @param key  The i18n key.
     * @param view The view to close.
     */
    public CloseViewAction(String key, IView view) {
        super(key);

        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.closeDown();
    }

    /**
     * Set the view to close.
     *
     * @param view The view to close.
     */
    public void setView(IView view) {
        this.view = view;
    }
}