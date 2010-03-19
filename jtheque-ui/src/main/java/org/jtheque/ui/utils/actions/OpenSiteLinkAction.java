package org.jtheque.ui.utils.actions;

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

import org.jtheque.utils.DesktopUtils;

import java.awt.event.ActionEvent;

/**
 * An action to open a site in the default browser.
 *
 * @author Baptiste Wicht
 */
public final class OpenSiteLinkAction extends JThequeSimpleAction {
    private final String url;

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