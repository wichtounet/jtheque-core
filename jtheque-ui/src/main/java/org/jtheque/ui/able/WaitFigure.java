package org.jtheque.ui.able;

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