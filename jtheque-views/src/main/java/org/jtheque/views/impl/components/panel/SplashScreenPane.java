package org.jtheque.views.impl.components.panel;

import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.jxlayer.plaf.BufferedLayerUI;
import org.jdesktop.jxlayer.plaf.LayerUI;
import org.jtheque.core.able.ICore;
import org.jtheque.core.able.application.Application;
import org.jtheque.ui.utils.AnimationUtils;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.ui.ImageUtils;
import org.jtheque.utils.ui.PaintUtils;
import org.jtheque.views.able.panel.ISplashView;
import org.pushingpixels.trident.Timeline;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
 * @author Baptiste Wicht
 */
public final class SplashScreenPane extends BufferedLayerUI<JComponent> implements ISplashView {
    private boolean inited;

    private BufferedImage bImage;

    private Font fontName;
    private Font fontLoading;

    private final Point pImage;
    private final Point pTextName;
    private final Point pTextLoading;

    private final String[] loadings = {"Loading ", "Loading .", "Loading ..", "Loading ..."};
    private int current;

    private static final int LOADING_FONT_SIZE = 20;
    private static final int TITLE_FONT_SIZE = 24;

    private static final int ANIMATION_DURATION = 2000;

    private Timeline waitingAnimation;
    private Timeline fadeAnimation;

    private final ICore core;

    /**
     * Construct a new SplashScreenPane.
     * 
     * @param core The core.
     */
    public SplashScreenPane(ICore core) {
        super();

        this.core = core;

        pImage = new Point();
        pTextName = new Point();
        pTextLoading = new Point();

        init();
    }

    /**
     * Init the view.
     */
    public void init() {
        Application application = core.getApplication();

        if (!StringUtils.isEmpty(application.getLogo())) {
            bImage = ImageUtils.openCompatibleImageFromFileSystem(application.getLogo() + '.' + application.getLogoType().getExtension());
        }
    }

    @Override
    public void paintLayer(Graphics2D g2, JXLayer<? extends JComponent> layer) {
        super.paintLayer(g2, layer);

        PaintUtils.initHints(g2);

        g2.setColor(Color.BLACK);

        g2.fillRect(0, 0, layer.getWidth(), layer.getHeight());

        if (!inited) {
            initFonts(g2);

            computeSizes(layer);

            inited = true;
        }

        Color color = new Color(255, 255, 255, (int) (getAlpha() * 255));

        paintName(g2, color);
        paintImage(g2);
        paintLoading(g2, color);
    }

    /**
     * Compute the sizes.
     *
     * @param layer The component to compute the sizes.
     */
    private void computeSizes(Component layer) {
        int nameWidth = layer.getFontMetrics(fontName).stringWidth(core.getApplication().getName());
        int loadingWidth = layer.getFontMetrics(fontLoading).stringWidth("Loading ...");

        int height = layer.getFontMetrics(fontName).getHeight() + 10 + bImage.getHeight() + layer.getFontMetrics(fontLoading).getHeight();

        pTextName.y = (int) ((layer.getSize().getHeight() - height) / 2);
        pImage.y = pTextName.y + layer.getFontMetrics(fontName).getHeight() + 10;
        pTextLoading.y = pImage.y + bImage.getHeight() + 20;

        pTextName.x = (int) ((layer.getSize().getWidth() - nameWidth) / 2);
        pImage.x = (int) ((layer.getSize().getWidth() - bImage.getWidth()) / 2);
        pTextLoading.x = (int) ((layer.getSize().getWidth() - loadingWidth) / 2);
    }

    /**
     * Init the fonts.
     *
     * @param g2 The Graphics2D element.
     */
    private void initFonts(Graphics g2) {
        fontName = g2.getFont().deriveFont(Font.BOLD, TITLE_FONT_SIZE);
        fontLoading = g2.getFont().deriveFont(Font.BOLD, LOADING_FONT_SIZE);
    }

    /**
     * Paint the loading text.
     *
     * @param g2    The Graphics2D element.
     * @param color The color of the loading text.
     */
    private void paintLoading(Graphics g2, Color color) {
        g2.translate(pTextLoading.x, pTextLoading.y);

        PaintUtils.drawString(g2, loadings[current], 0, 0, fontLoading, color);
    }

    /**
     * Paint the image.
     *
     * @param g2 The Graphics2D element.
     */
    private void paintImage(Graphics2D g2) {
        g2.translate(pImage.x, pImage.y);

        PaintUtils.drawAlphaImage(g2, bImage, 0, 0, getAlpha());

        g2.translate(-pImage.x, -pImage.y);
    }

    /**
     * Paint the name of the application.
     *
     * @param g2    The Graphics2D element.
     * @param color The color of the application name.
     */
    private void paintName(Graphics g2, Color color) {
        g2.translate(pTextName.x, pTextName.y);

        PaintUtils.drawString(g2, core.getApplication().getName(), 0, 0, fontName, color);

        g2.translate(-pTextName.x, -pTextName.y);
    }

    /**
     * Set the current loading index.
     *
     * @param current The current loading index.
     */
    public void setCurrent(int current) {
        int oldCurrent = this.current;

        this.current = current;

        if (current != oldCurrent) {
            setDirty(true);
        }
    }

    /**
     * Return the current loading index.
     *
     * @return The current loading index.
     */
    public int getCurrent() {
        return current;
    }

    @Override
    public void appearsAndAnimate() {
        if (fadeAnimation != null && !fadeAnimation.isDone()) {
            fadeAnimation.suspend();
        }

        if (waitingAnimation == null) {
            waitingAnimation = AnimationUtils.createInterpolationAnimation(this, ANIMATION_DURATION,
                    "current", 0, 4);
        }

        fadeAnimation = AnimationUtils.createFadeInAnimation(this);

        AnimationUtils.startsLoopWhenStop(fadeAnimation, waitingAnimation);

        fadeAnimation.play();
    }

    @Override
    public void disappear() {
        if (fadeAnimation != null && !fadeAnimation.isDone()) {
            fadeAnimation.suspend();
        }

        waitingAnimation.suspend();

        fadeAnimation = AnimationUtils.startFadeOut(this);
    }

    @Override
    public LayerUI<JComponent> getImpl() {
        return this;
    }
}