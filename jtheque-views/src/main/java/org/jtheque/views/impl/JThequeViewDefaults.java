package org.jtheque.views.impl;

import org.jtheque.views.able.ViewDefaults;

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
 * @author Baptiste Wicht
 */
public final class JThequeViewDefaults implements ViewDefaults {
    private Color backgroundColor;
    private Color foregroundColor;
    private Color selectedForegroundColor;
    private Color selectedBackgroundColor;
    private Color filthyBackgroundColor;
    private Color filthyForegroundColor;

    private Font filthyButtonFont;
    private Font filthyInputFont;

    @Override
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Set the default background color.
     *
     * @param backgroundColor The default background color.
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public Color getFilthyBackgroundColor() {
        return filthyBackgroundColor;
    }

    /**
     * Set the filthy background color.
     *
     * @param filthyBackgroundColor The filthy background color.
     */
    public void setFilthyBackgroundColor(Color filthyBackgroundColor) {
        this.filthyBackgroundColor = filthyBackgroundColor;
    }

    @Override
    public Color getForegroundColor() {
        return foregroundColor;
    }

    /**
     * Set the foreground color.
     *
     * @param foregroundColor The foreground color.
     */
    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    @Override
    public Color getSelectedForegroundColor() {
        return selectedForegroundColor;
    }

    /**
     * Set the selected foreground color.
     *
     * @param selectedForegroundColor The selected foreground color.
     */
    public void setSelectedForegroundColor(Color selectedForegroundColor) {
        this.selectedForegroundColor = selectedForegroundColor;
    }

    @Override
    public Color getSelectedBackgroundColor() {
        return selectedBackgroundColor;
    }

    /**
     * Set the selected background color.
     *
     * @param selectedBackgroundColor The selected background color.
     */
    public void setSelectedBackgroundColor(Color selectedBackgroundColor) {
        this.selectedBackgroundColor = selectedBackgroundColor;
    }

    @Override
    public Font getFilthyButtonFont() {
        return filthyButtonFont;
    }

    /**
     * Set the filthy button font.
     *
     * @param filthyButtonFont The filthy button font.
     */
    public void setFilthyButtonFont(Font filthyButtonFont) {
        this.filthyButtonFont = filthyButtonFont;
    }

    @Override
    public Color getFilthyForegroundColor() {
        return filthyForegroundColor;
    }

    /**
     * Set the filthy foreground color.
     *
     * @param filthyForegroundColor The filthy foreground color.
     */
    public void setFilthyForegroundColor(Color filthyForegroundColor) {
        this.filthyForegroundColor = filthyForegroundColor;
    }

    @Override
    public Font getFilthyInputFont() {
        return filthyInputFont;
    }

    /**
     * Set the filthy input font.
     *
     * @param filthyInputFont The filthy input font.
     */
    public void setFilthyInputFont(Font filthyInputFont) {
        this.filthyInputFont = filthyInputFont;
    }
}
