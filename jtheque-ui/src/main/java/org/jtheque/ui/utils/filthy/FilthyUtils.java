package org.jtheque.ui.utils.filthy;

import org.jtheque.resources.IResourceService;
import org.jtheque.ui.UIResources;
import org.jtheque.utils.ui.ImageUtils;
import org.jtheque.utils.ui.SizeTracker;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

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

/**
 * Utility class for filthy component. 
 *
 * @author Baptiste Wicht
 */
public class FilthyUtils implements IFilthyUtils {
    private final BufferedImage LIGHT;
    private final LinearGradientPaint BACKGROUND_PAINT;

    /**
     * Utility class, not instanciable.
     */
    public FilthyUtils(IResourceService resourceService) {
        super();

        BACKGROUND_PAINT = new LinearGradientPaint(new Point2D.Float(0, 0), new Point2D.Float(0, 584),
                new float[]{0.22f, 0.9f}, new Color[]{new Color(32, 39, 55), new Color(133, 144, 165)});

        LIGHT = resourceService.getImage(UIResources.LIGHT_IMAGE);
    }

    /**
     * Paint a filthy background to a panel.
     *
     * @param g             The graphics to paint to.
     * @param gradientImage The gradient image to use.
     * @param tracker       The size tracker of the panel.
     * @param panel         The panel to paint.
     * @return The current gradient image buffer.
     */
    @Override
    public Image paintFilthyBackground(Graphics g, Image gradientImage, SizeTracker tracker, Component panel) {
        Image gradient = gradientImage;

        Graphics2D g2 = (Graphics2D) g;

        if (gradient == null || tracker.hasSizeChanged()) {
            gradient = ImageUtils.createCompatibleImage(panel.getWidth(), panel.getHeight());
            Graphics2D g2d = (Graphics2D) gradient.getGraphics();
            Composite composite = g2.getComposite();

            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setPaint(BACKGROUND_PAINT);
            g2d.fillRect(0, 0, panel.getWidth(), panel.getHeight());
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

            g2d.drawImage(LIGHT, 0, 0, panel.getWidth(), LIGHT.getHeight(), null);
            g2d.setComposite(composite);
            g2d.dispose();
        }

        g2.drawImage(gradient, 0, 0, null);

        tracker.updateSize();

        return gradient;
    }
}