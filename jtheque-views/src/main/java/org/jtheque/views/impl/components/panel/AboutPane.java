package org.jtheque.views.impl.components.panel;

import org.jtheque.core.Core;
import org.jtheque.core.application.Application;
import org.jtheque.i18n.LanguageService;
import org.jtheque.i18n.Internationalizable;
import org.jtheque.ui.utils.AnimationUtils;
import org.jtheque.utils.DesktopUtils;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.ImageUtils;
import org.jtheque.utils.ui.PaintUtils;
import org.jtheque.utils.ui.SizeTracker;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.windows.AboutView;
import org.jtheque.views.windows.LicenseView;
import org.jtheque.views.impl.models.AboutInfo;
import org.jtheque.views.impl.models.AboutModel;

import org.jdesktop.swingx.JXPanel;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.callback.TimelineCallbackAdapter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import static org.jtheque.views.impl.models.AboutModel.*;

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
 * An about view using filthy paint with Java 2D.
 *
 * @author Baptiste Wicht
 */
public final class AboutPane extends JXPanel implements AboutView, Internationalizable {
    private final SizeTracker tracker = new SizeTracker(this);

    private final Map<String, Shape> shapes = CollectionUtils.newHashMap(3);
    private final Map<Rectangle, String> urlRectangles = CollectionUtils.newHashMap(5);

    private BufferedImage logo;
    private BufferedImage infosImage;
    private BufferedImage creditsImage;

    private int xStart = -1;
    private int yStart = -1;

    private Font fontName;
    private Font fontInfos;

    private int max;
    private int start;
    private int paintWidth;

    private boolean inited;

    private Timeline timeline;

    private final AboutModel model = new AboutModel();

    @Resource
    private LicenseView licenseView;

    @Resource
    private Core core;

    @Resource
    private LanguageService languageService;

    /**
     * Create the view. 
     */
    @PostConstruct
    public void create(){
        setOpaque(false);
        setVisible(true);

        languageService.addInternationalizable(this);

        Application application = core.getApplication();

        if (!StringUtils.isEmpty(application.getLogo())) {
            //We must use directly that, or we can also add prefix file to use as Spring resource
            logo = ImageUtils.openCompatibleImageFromFileSystem(application.getLogo() + '.' +
                    application.getLogoType().getExtension());
        }

        addMouseListener(new MouseController());
        addMouseMotionListener(new MouseMotionController());

        timeline = AnimationUtils.createInterpolationAnimation(this, 4 * 1000, "start", 0, model.getCreditsHeight());

        shapes.put("license", new GeneralPath()); //Set a default path to avoid NPE if display license is false

        init();
    }

    /**
     * Init the about pane. 
     */
    private void init() {
        model.refresh(core, languageService);
        inited = false;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if (!inited) {
            initView(g2);
        }

        PaintUtils.initHints(g2);

        Color textColor = new Color(255, 255, 255, (int) (getAlpha() * 255));

        if (tracker.hasSizeChanged()) {
            xStart = (getWidth() - paintWidth) / 2;
            yStart = (getHeight() - VIEW_HEIGHT) / 2;

            calcQuitShape(xStart, yStart);
        }

        paintBackground(g2);
        paintTitle(g2, xStart, yStart, textColor);

        if (logo != null) {
            PaintUtils.drawAlphaImage(g2, logo, xStart, yStart + 65, getAlpha());
        }

        int y = paintInfos(g2, yStart + 65);

        y = Math.max(y, 65 + (logo == null ? 0 : logo.getHeight())) + 30;

        int x = (getWidth() - creditsImage.getWidth()) / 2;

        paintCredits(g2, x, y);

        PaintUtils.drawString(g2, model.getCopyright(), xStart, y + 120, fontInfos, textColor);

        paintLicense(g2, textColor, x, y);

        tracker.updateSize();
    }

    /**
     * Paint the infos.
     *
     * @param g2     The graphics 2D elements.
     * @param yStart The y position to start from to paint.
     *
     * @return The y position stop of the infos.
     */
    private int paintInfos(Graphics2D g2, int yStart) {
        int startX = logo == null ? 0 : logo.getWidth() + (getWidth() - paintWidth) / 2 + INFO_MARGIN;

        if (tracker.hasSizeChanged()) {
            createBufferInfosImage(yStart, startX);
        }

        PaintUtils.drawAlphaImage(g2, infosImage, startX, yStart, getAlpha());

        return yStart + infosImage.getHeight();
    }

    /**
     * Create the image of the informations to improve performance. The image is only modified when the size of the
     * window has changed.
     *
     * @param startY The y position to start painting.
     * @param startX The x position to start painting.
     */
    private void createBufferInfosImage(int startY, int startX) {
        infosImage = ImageUtils.createCompatibleImage(paintWidth - 30, model.getInfos().length * (INFO_HEIGHT + 2),
                BufferedImage.TRANSLUCENT);

        Graphics g = infosImage.getGraphics();

        PaintUtils.initHints((Graphics2D) g);

        Path2D urlPath = new GeneralPath();

        for (int i = 0; i < model.getInfos().length; i++) {
            AboutInfo info = model.getInfos()[i];

            int x = max - info.getLeftWidth();
            int y = i * INFO_HEIGHT + INFO_HEIGHT;

            PaintUtils.drawString(g, info.getLeft() + " : ", x, y, fontInfos);

            x += info.getLeftWidth() + INFO_MARGIN;

            if (info.isUrl() || info.isMail()) {
                PaintUtils.drawString(g, info.getRight(), x, y, SwingUtils.underline(fontInfos));

                Rectangle rectangle = new Rectangle(startX, startY + y - 10, info.getRightWidth(), CREDIT_HEIGHT);

                urlPath.append(rectangle, false);

                urlRectangles.put(rectangle, info.getRight());
            } else {
                PaintUtils.drawString(g, info.getRight(), x, y, fontInfos);
            }
        }

        shapes.put("url", urlPath);

        g.dispose();
    }

    /**
     * Paint the license clickable text.
     *
     * @param g2        The graphics 2D elements.
     * @param textColor The text color.
     * @param x         The x position to start painting the license.
     * @param y         The y position to start painting the license.
     */
    private void paintLicense(Graphics g2, Color textColor, int x, int y) {
        if (core.getApplication().isLicenseDisplayed()) {
            int xLicense = x + paintWidth - getFontMetrics(fontInfos).stringWidth(model.getLicense());

            PaintUtils.drawString(g2, model.getLicense(), xLicense, y + 150, fontInfos, textColor);

            shapes.put("license", new GeneralPath(
                    new Rectangle(xLicense, y + 140, getFontMetrics(fontInfos).stringWidth(model.getLicense()), CREDIT_HEIGHT)));
        }
    }

    /**
     * Paint the credits image.
     *
     * @param g2 The graphics 2D elements.
     * @param x  The x position to start painting the credits image.
     * @param y  The y position to start painting the credits image.
     */
    private void paintCredits(Graphics2D g2, int x, int y) {
        if (start >= creditsImage.getHeight()) {
            start = 0;
        }

        if (start + CREDITS_HEIGHT > creditsImage.getHeight()) {
            int painted = creditsImage.getHeight() - start;

            PaintUtils.drawAlphaImage(g2, creditsImage, //Image
                    x, y, x + creditsImage.getWidth(), y + painted, //Destination
                    0, start, creditsImage.getWidth(), creditsImage.getHeight(), getAlpha()); //Source

            PaintUtils.drawAlphaImage(g2, creditsImage, //Image
                    x, y + painted, x + creditsImage.getWidth(), y + CREDITS_HEIGHT, //Destination
                    0, 0, creditsImage.getWidth(), CREDITS_HEIGHT - painted, getAlpha()); //Source
        } else {
            PaintUtils.drawAlphaImage(g2, creditsImage, //Image
                    x, y, x + creditsImage.getWidth(), y + CREDITS_HEIGHT, //Destination
                    0, start, creditsImage.getWidth(), start + CREDITS_HEIGHT, getAlpha()); //Source
        }
    }

    /**
     * Paint the title of the view.
     *
     * @param g2        The graphics 2D elements.
     * @param xStart    The x position to start.
     * @param yStart    The y position to start.
     * @param textColor The text color.
     */
    private void paintTitle(Graphics g2, int xStart, int yStart, Color textColor) {
        PaintUtils.drawString(g2, core.getApplication().getI18nProperties().getName(), xStart, yStart, fontName, textColor);

        PaintUtils.fillRect(g2, xStart, yStart + 20, paintWidth, 3, textColor);
    }

    /**
     * Paint the background.
     *
     * @param g2 The graphics 2D elements.
     */
    private void paintBackground(Graphics g2) {
        PaintUtils.fillRect(g2, 0, 0, getWidth(), getHeight(), new Color(0, 0, 0, (int) (getAlpha() * 255)));
    }

    /**
     * Calculate the quit shape.
     *
     * @param xStart The x position to start.
     * @param yStart The y position to start.
     */
    private void calcQuitShape(int xStart, int yStart) {
        Path2D path = new GeneralPath();

        path.append(new Rectangle(0, 0, getWidth(), yStart), false);
        path.append(new Rectangle(0, yStart, xStart, VIEW_HEIGHT), false);
        path.append(new Rectangle(xStart + paintWidth, yStart, getWidth() - paintWidth - xStart, VIEW_HEIGHT), false);
        path.append(new Rectangle(0, yStart + VIEW_HEIGHT, getWidth(), yStart), false);

        shapes.put("quit", path);
    }

    /**
     * Init the view.
     *
     * @param g2 The graphics 2D elements.
     */
    private void initView(Graphics g2) {
        fontName = SwingUtils.getDefaultFont().deriveFont(24.0f).deriveFont(Font.BOLD);
        fontInfos = SwingUtils.getDefaultFont().deriveFont(18.0f).deriveFont(Font.PLAIN);

        computeWidths(g2);
        createCreditsImage(g2);

        inited = true;
    }

    /**
     * Compute the widths.
     *
     * @param g2 The graphics 2D elements.
     */
    private void computeWidths(Graphics g2) {
        FontMetrics metrics = getFontMetrics(fontInfos);

        int maxRight = 0;

        for (AboutInfo info : model.getInfos()) {
            info.setLeftWidth(metrics.stringWidth(info.getLeft()));

            info.setRightWidth(metrics.stringWidth(info.getRight()));

            if (info.isUrl() || info.isMail()) {
                info.setRightWidth(info.getRightWidth() + 5);
            }

            max = Math.max(max, info.getLeftWidth());
            maxRight = Math.max(maxRight, info.getRightWidth());
        }

        metrics = g2.getFontMetrics(fontName);

        int logoWidth = logo == null ? 0 : logo.getWidth();

        paintWidth = Math.max(metrics.stringWidth(core.getApplication().getI18nProperties().getName()), logoWidth + 30 + max + 20 + maxRight);
    }

    /**
     * Create the credits image.
     *
     * @param g2 The graphics 2D elements.
     */
    private void createCreditsImage(Graphics g2) {
        creditsImage = ImageUtils.createCompatibleImage(paintWidth - 30, model.getCredits().size() * CREDIT_HEIGHT, BufferedImage.TRANSLUCENT);

        Graphics2D gImage = (Graphics2D) creditsImage.getGraphics();

        PaintUtils.initHints(gImage);

        FontMetrics metrics = g2.getFontMetrics(fontInfos);

        gImage.setFont(fontInfos);

        int y = CREDIT_HEIGHT;
        for (String credit : model.getCredits()) {
            gImage.drawString(credit, (creditsImage.getWidth() - metrics.stringWidth(credit)) / 2, y);
            y += CREDIT_HEIGHT;
        }

        gImage.dispose();
    }

    /**
     * Return the start position. Called from Trident.
     *
     * @return The start position.
     */
    public int getStart() {
        return start;
    }

    /**
     * Set the start position. Called from Trident. 
     *
     * @param start The position. +
     */
    public void setStart(int start) {
        this.start = start;

        repaint();
    }

    @Override
    public void appear() {
        Timeline fadeAnimation = AnimationUtils.createFadeInAnimation(this);
        AnimationUtils.startsLoopWhenStop(fadeAnimation, timeline);
        fadeAnimation.play();
    }

    /**
     * Make disappear the view.
     */
    public void disappear() {
        timeline.suspend();

        Timeline fadeOut = AnimationUtils.createFadeOutAnimation(this);
        fadeOut.addCallback(new TimelineCallbackAdapter() {
            @Override
            public void onTimelineStateChanged(Timeline.TimelineState oldState, Timeline.TimelineState newState,
                                               float durationFraction, float timelinePosition) {
                if (newState == Timeline.TimelineState.DONE) {
                    setVisible(false);
                }
            }
        });
        fadeOut.play();
    }

    @Override
    public void refreshText(LanguageService languageService) {
        init();
        repaint();
    }

    @Override
    public Component getImpl() {
        return this;
    }

    /**
     * The mouse controller of the view.
     */
    private final class MouseController extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            if (shapes.get("quit").contains(event.getPoint())) {
                disappear();
            } else if (shapes.get("url").contains(event.getPoint())) {
                for (Map.Entry<Rectangle, String> entry : urlRectangles.entrySet()) {
                    if (entry.getKey().contains(event.getPoint())) {
                        DesktopUtils.browse(entry.getValue());

                        return;
                    }
                }
            } else if (shapes.get("license").contains(event.getPoint())) {
                disappear();

                licenseView.display();
            }
        }
    }

    /**
     * The mouse motion controller for the view.
     */
    private final class MouseMotionController extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent event) {
            if (shapes.get("quit").contains(event.getPoint()) ||
                    shapes.get("url").contains(event.getPoint()) ||
                    shapes.get("license").contains(event.getPoint())) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else {
                setCursor(Cursor.getDefaultCursor());
            }
        }
    }
}