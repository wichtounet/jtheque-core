package org.jtheque.ui.impl;

import org.jtheque.images.able.IImageService;
import org.jtheque.ui.able.IFilthyUtils;
import org.jtheque.ui.able.IUIUtils;
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
 * Utility class for filthy component.
 *
 * @author Baptiste Wicht
 */
public class FilthyUtils implements IFilthyUtils {
    private final BufferedImage lightImage;
    private final LinearGradientPaint backgroundPaint;

    /**
     * Utility class, not instanciable.
     *
     * @param imageService The resource service, used to get the image of the background.
     */
    public FilthyUtils(IImageService imageService) {
        super();

        backgroundPaint = new LinearGradientPaint(new Point2D.Float(0, 0), new Point2D.Float(0, 584),
                new float[]{0.22f, 0.9f}, new Color[]{new Color(32, 39, 55), new Color(133, 144, 165)});

        lightImage = imageService.getImage(IUIUtils.LIGHT_IMAGE);
    }

    /**
     * Paint a filthy background to a panel.
     *
     * @param g             The graphics to paint to.
     * @param gradientImage The gradient image to use.
     * @param tracker       The size tracker of the panel.
     * @param panel         The panel to paint.
     *
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
            g2d.setPaint(backgroundPaint);
            g2d.fillRect(0, 0, panel.getWidth(), panel.getHeight());
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

            g2d.drawImage(lightImage, 0, 0, panel.getWidth(), lightImage.getHeight(), null);
            g2d.setComposite(composite);
            g2d.dispose();
        }

        g2.drawImage(gradient, 0, 0, null);

        tracker.updateSize();

        return gradient;
    }
}