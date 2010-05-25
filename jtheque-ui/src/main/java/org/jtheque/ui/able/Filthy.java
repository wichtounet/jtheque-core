package org.jtheque.ui.able;

import java.awt.Color;
import java.awt.Font;

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
 * An utility interface for filthy views.
 *
 * @author Baptiste Wicht
 */
public interface Filthy {
    Color INPUT_COLOR = new Color(70, 70, 70);
    Color INPUT_BORDER_COLOR = new Color(220, 220, 220);

    Color HINT_COLOR = Color.white;
    Color BACKGROUND_COLOR = Color.black;
    Color ERROR_COLOR = Color.red;
    Color FOREGROUND_COLOR = Color.white;

    Font HINT_FONT = new Font(null, 1, 22);
    Font ERROR_FONT = new Font(null, 1, 20);
    Font TITLE_FONT = new Font(null, 1, 16);
    Font INPUT_FONT = new Font(null, 1, 14);
}