package org.jtheque.ui.impl.components;

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

import org.jtheque.ui.ViewComponent;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * An internationalizable label.
 *
 * @author Baptiste Wicht
 */
public final class JThequeLabel extends JLabel implements ViewComponent {
    /**
     * Construct a new <code>JThequeLabel</code>.
     *
     * @param text The text of the label.
     */
    public JThequeLabel(String text) {
        super(text);
    }

    /**
     * Construct a new <code>JThequeLabel</code>.
     *
     * @param text The text of the label.
     * @param font The font to use.
     */
    public JThequeLabel(String text, Font font) {
        this(text);

        setFont(font);
    }

    /**
     * Construct a new <code>JThequeLabel</code>.
     *
     * @param text       The text of the label.
     * @param font       The font to use.
     * @param foreground The foreground color.
     */
    public JThequeLabel(String text, Font font, Color foreground) {
        this(text);

        setFont(font);
        setForeground(foreground);
    }

    @Override
    public void paint(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        super.paint(g);
    }

    @Override
    public Object getImpl() {
        return this;
    }
}