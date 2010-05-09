package org.jtheque.ui.utils.actions;

import org.jtheque.utils.DesktopUtils;

import java.awt.event.ActionEvent;

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
 * Ab abstract browse action.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractBrowseAction extends JThequeAction {
    /**
     * Construct a new AbstractBrowseAction.
     *
     * @param key The internationalization key.
     */
    protected AbstractBrowseAction(String key) {
        super(key);
    }

    @Override
    public final void actionPerformed(ActionEvent e) {
        DesktopUtils.browse(getUrl());
    }

    /**
     * Return the URL.
     *
     * @return The URL to browse.
     */
    protected abstract String getUrl();
}
