package org.jtheque.core.managers.view.able;

import java.awt.Color;
import java.awt.Font;

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

/**
 * The view defaults.
 *
 * @author Baptiste Wicht
 */
public interface ViewDefaults {
    /**
     * Return the default background color.
     *
     * @return The default background color.
     */
    Color getBackgroundColor();

    /**
     * Return the default filthy background color.
     *
     * @return The default filthy background color.
     */
    Color getFilthyBackgroundColor();

    /**
     * Return the default foreground color.
     *
     * @return The default foreground color.
     */
    Color getForegroundColor();

    /**
     * Return the default selected foreground color.
     *
     * @return The default selected foreground color.
     */
    Color getSelectedForegroundColor();

    /**
     * Return the default selected background color.
     *
     * @return The default selected background color.
     */
    Color getSelectedBackgroundColor();

    /**
     * Return the default filthy foreground color.
     *
     * @return The default filthy foreground color.
     */
    Color getFilthyForegroundColor();

    /**
     * Return the filthy button font.
     *
     * @return The filthy button font.
     */
    Font getFilthyButtonFont();

    /**
     * Return the filthy input font.
     *
     * @return The filthy input font.
     */
    Font getFilthyInputFont();
}