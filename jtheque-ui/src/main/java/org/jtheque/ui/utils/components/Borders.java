package org.jtheque.ui.utils.components;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

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
 * An utility class to create and get borders.
 *
 * @author Baptiste Wicht
 */
public final class Borders {
    public static final Border DIALOG_BORDER = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    public static final Border EMPTY_BORDER = BorderFactory.createEmptyBorder();

    /**
     * This is an utility class, not instanciable.
     */
    private Borders() {
        super();
    }

    /**
     * Create empty borders with a size of i on all side.
     *
     * @param i The size of borders.
     * @return the <code>Border</code> object
     */
    public static Border createEmptyBorder(int i) {
        return BorderFactory.createEmptyBorder(i, i, i, i);
    }

    /**
     * Creates an empty border that takes up space but which does
     * no drawing, specifying the width of the top, left, bottom, and
     * right sides.
     *
     * @param top    an integer specifying the width of the top, in pixels
     * @param left   an integer specifying the width of the left side, in pixels
     * @param bottom an integer specifying the width of the bottom, in pixels
     * @param right  an integer specifying the width of the right side, in pixels
     * @return the <code>Border</code> object
     */
    public static Border createEmptyBorder(int top, int left, int bottom, int right) {
        return BorderFactory.createEmptyBorder(top, left, bottom, right);
    }
}