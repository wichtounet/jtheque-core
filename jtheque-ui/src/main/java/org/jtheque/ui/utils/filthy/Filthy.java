package org.jtheque.ui.utils.filthy;

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

public interface Filthy {
    Color INPUT_COLOR = new Color(70, 70, 70);
    Color INPUT_BORDER_COLOR = new Color(220, 220, 220);

    Color HINT_COLOR = Color.white;
    Color BACKGROUND_COLOR = Color.black;
    Color ERROR_COLOR = Color.red;

    Font HINT_FONT = new Font(null, 1, 22);
    Font ERROR_FONT = new Font(null, 1, 20);
}