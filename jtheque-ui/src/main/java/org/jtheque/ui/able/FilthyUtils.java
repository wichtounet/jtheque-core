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

import org.jtheque.utils.ui.SizeTracker;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

/**
 * A filthy utils specification.
 *
 * @author Baptiste Wicht
 */
public interface FilthyUtils {
    /**
     * Paint a filthy buffered background on the given graphics.
     *
     * @param g             The graphics to use.
     * @param gradientImage The gradient image.
     * @param tracker       The size tracker of the component.
     * @param panel         The panel to paint the filthy background for.
     *
     * @return The new gradient image.
     */
    Image paintFilthyBackground(Graphics g, Image gradientImage, SizeTracker tracker, Component panel);
}
