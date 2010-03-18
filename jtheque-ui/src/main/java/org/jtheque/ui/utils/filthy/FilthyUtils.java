package org.jtheque.ui.utils.filthy;

import org.jtheque.core.ICore;
import org.jtheque.core.utils.ImageType;
import org.jtheque.resources.IResourceManager;
import org.jtheque.utils.ui.ImageUtils;
import org.jtheque.utils.ui.SizeTracker;
import org.jtheque.ui.ViewsUtilsServices;

import java.awt.*;
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
 * A temporary swing utility class. This class must be moved to JTheque Core/Utils at
 * the next release of this two projects.
 *
 * @author Baptiste Wicht
 */
public final class FilthyUtils {
    private static final BufferedImage LIGHT;
    private static final LinearGradientPaint BACKGROUND_PAINT;

    static {
        BACKGROUND_PAINT = new LinearGradientPaint(new Point2D.Float(0, 0), new Point2D.Float(0, 584),
                new float[]{0.22f, 0.9f}, new Color[]{new Color(32, 39, 55), new Color(133, 144, 165)});
        
        LIGHT = ViewsUtilsServices.get(IResourceManager.class).getImage(ICore.IMAGES_BASE_NAME, "light", ImageType.PNG);
    }

    /**
     * Utility class, not instanciable.
     */
    private FilthyUtils() {
        super();
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
    public static Image paintFilthyBackground(Graphics g, Image gradientImage, SizeTracker tracker, Component panel) {
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