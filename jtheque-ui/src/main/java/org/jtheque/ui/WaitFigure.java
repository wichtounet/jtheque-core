package org.jtheque.ui;

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

import javax.swing.JComponent;
import java.awt.Graphics;

/**
 * A wait figure.
 *
 * @author Baptiste Wicht
 */
public interface WaitFigure {
    /**
     * Init the wait figure.
     */
    void init();

    /**
     * Start the animation.
     */
    void start();

    /**
     * Stop the animation.
     */
    void stop();

    /**
     * Paint something in the Graphics of the GlassPane.
     *
     * @param graphics The Graphics of the glass pane
     */
    void paint(Graphics graphics);

    /**
     * Set the bounds of the figure. This method is called when the same method is called on the glass pane. We
     * use that to be sure that its correctly resized.
     *
     * @param width  The width of the glass pane
     * @param height The height of the glass pane
     */
    void setBounds(int width, int height);

    /**
     * Set the parent glass pane of the wait figure.
     *
     * @param glassPane The parent glass pane
     */
    void setGlassPane(JComponent glassPane);
}