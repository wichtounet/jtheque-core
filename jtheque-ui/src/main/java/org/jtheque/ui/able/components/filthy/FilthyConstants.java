package org.jtheque.ui.able.components.filthy;

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
 * An utility class for filthy views.
 *
 * @author Baptiste Wicht
 */
public final class FilthyConstants {
    public static final Color INPUT_COLOR = new Color(70, 70, 70);
    public static final Color INPUT_BORDER_COLOR = new Color(220, 220, 220);

    public static final Color HINT_COLOR = Color.white;
    public static final Color BACKGROUND_COLOR = Color.black;
    public static final Color ERROR_COLOR = Color.red;
    public static final Color FOREGROUND_COLOR = Color.white;

    public static final Font HINT_FONT = new Font(null, 1, 22);
    public static final Font ERROR_FONT = new Font(null, 1, 20);
    public static final Font TITLE_FONT = new Font(null, 1, 16);
    public static final Font INPUT_FONT = new Font(null, 1, 14);

    /**
     * 
     */
    private FilthyConstants() {
        throw new AssertionError();
    }
}