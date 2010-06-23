package org.jtheque.ui.utils.components;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

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
     *
     * @return the <code>Border</code> object
     */
    public static Border createEmptyBorder(int i) {
        return BorderFactory.createEmptyBorder(i, i, i, i);
    }

    /**
     * Creates an empty border that takes up space but which does no drawing, specifying the width of the top, left,
     * bottom, and right sides.
     *
     * @param top    an integer specifying the width of the top, in pixels
     * @param left   an integer specifying the width of the left side, in pixels
     * @param bottom an integer specifying the width of the bottom, in pixels
     * @param right  an integer specifying the width of the right side, in pixels
     *
     * @return the <code>Border</code> object
     */
    public static Border createEmptyBorder(int top, int left, int bottom, int right) {
        return BorderFactory.createEmptyBorder(top, left, bottom, right);
    }
}
