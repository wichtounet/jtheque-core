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

import org.jtheque.utils.DesktopUtils;

import java.awt.event.ActionEvent;

/**
 * An action to open a site in the default browser.
 *
 * @author Baptiste Wicht
 */
public final class OpenSiteLinkAction extends JThequeSimpleAction {
    private final String url;
    private static final long serialVersionUID = 1597739877090719576L;

    /**
     * Construct a new AcOpenSite.
     *
     * @param url The URL of the site.
     */
    public OpenSiteLinkAction(String url) {
        super();

        setText(url);

        this.url = url;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        DesktopUtils.browse(url);
    }
}