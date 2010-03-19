package org.jtheque.ui.utils.components;

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

import org.jtheque.core.utils.UtilsServices;
import org.jtheque.i18n.ILanguageService;
import org.jtheque.i18n.Internationalizable;

import javax.swing.border.TitledBorder;

/**
 * A border title updater.
 *
 * @author Baptiste Wicht
 */
public final class BorderUpdater implements Internationalizable {
    private final TitledBorder border;
    private final String key;

    /**
     * Construct a new BorderUpdater for a specific border with a internationalization key.
     *
     * @param border The titled border.
     * @param key    The internationalization key.
     */
    public BorderUpdater(TitledBorder border, String key) {
        super();

        this.border = border;
        this.key = key;
    }

    @Override
    public void refreshText() {
        border.setTitle(UtilsServices.get(ILanguageService.class).getMessage(key));
    }
}
