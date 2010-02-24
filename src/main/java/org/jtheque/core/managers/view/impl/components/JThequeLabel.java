package org.jtheque.core.managers.view.impl.components;

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

import org.jtheque.core.managers.view.able.components.ViewComponent;

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
    public Object getImpl(){
        return this;
    }
}