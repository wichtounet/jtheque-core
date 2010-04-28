package org.jtheque.ui.utils.filthy;

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

import org.jtheque.utils.ui.SizeTracker;

import javax.annotation.Resource;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;

/**
 * A panel with a filthy background.
 *
 * @author Baptiste Wicht
 */
public class FilthyBackgroundPanel extends JPanel {
    private final SizeTracker tracker = new SizeTracker(this);
    private final IFilthyUtils filthyUtils;
    
    private Image gradientImage;

    public FilthyBackgroundPanel(IFilthyUtils filthyUtils) {
        super();

        this.filthyUtils = filthyUtils;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (!isVisible()) {
            return;
        }

        gradientImage = filthyUtils.paintFilthyBackground(g, gradientImage, tracker, this);
    }
}