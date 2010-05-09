package org.jtheque.ui.utils.windows;

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

import org.jtheque.ui.able.WaitFigure;
import org.slf4j.LoggerFactory;

import javax.swing.JComponent;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * A wait figure with an infinite animation.
 *
 * @author Baptiste Wicht
 */
public final class InfiniteWaitFigure implements WaitFigure, ActionListener {
    private static final int CLOCK_TICK_WIDTH = 12;
    private static final int NUMBER_OF_BARS = 12;
    private static final int SPEED = 1000 / 8;
    private static final double SCALE = 1.5d;

    private int iterate;

    private JComponent glassPane;

    private BufferedImage imageBuf;
    private Area[] bars;
    private Rectangle barsBounds;
    private Rectangle barsScreenBounds;
    private AffineTransform centerAndScaleTransform;
    private Timer timer;
    private Color[] colors;
    private int colorOffset;
    
    private final KeyListener keyListener = new InactiveKeyListener();
    private final MouseMotionListener mouseMotionListener = new InactiveMouseMotionListener();

    /**
     * A key listener who do nothing. 
     * 
     * @author Baptiste Wicht
     */
    private static final class InactiveKeyListener extends KeyAdapter{}

    /**
     * A mouse motion listener who do nothing. 
     * 
     * @author Baptiste Wicht
     */
    private static final class InactiveMouseMotionListener extends MouseMotionAdapter{}
    
    private final ComponentListener componentAdapter = new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            resize();
        }
    };

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == timer) {
            update();
        }
    }

    @Override
    public void init() {
        timer = new Timer(SPEED, this);

        colors = new Color[NUMBER_OF_BARS * 2];

        bars = buildTicker(NUMBER_OF_BARS);

        barsBounds = new Rectangle();

        int i = 0;
        for (Area bar : bars) {
            barsBounds = barsBounds.union(bar.getBounds());

            int channel = 224 - 128 / (i + 1);
            colors[i] = new Color(channel, channel, channel);
            colors[NUMBER_OF_BARS + i] = colors[i];

            ++i;
        }
    }

    @Override
    public void paint(Graphics graphics) {
        Rectangle clip = glassPane.getBounds();

        if (imageBuf == null) {
            Color bg = ((RootPaneContainer) SwingUtilities.getWindowAncestor(glassPane)).
                    getContentPane().getBackground();
            graphics.setColor(new Color(bg.getRed(), bg.getGreen(), bg.getBlue(), 180));
            graphics.fillRect(clip.x, clip.y, clip.width, clip.height);
        }

        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.transform(centerAndScaleTransform);

        int i = 0;
        for (Area bar : bars) {
            ++i;
            g2.setColor(colors[i + colorOffset]);
            g2.fill(bar);
        }
    }

    /**
     * This method will be called when the frame will be resized.
     */
    void resize() {
        glassPane.setOpaque(false);
        imageBuf = null;
        iterate = 3;
    }

    @Override
    public void start() {
        glassPane.setOpaque(false);

        Window window = SwingUtilities.getWindowAncestor(glassPane);

        if (window == null) {
            glassPane.addAncestorListener(new SimpleGlassPaneAncestorListener());
        } else {
            window.addComponentListener(componentAdapter);
        }

        iterate = 3;

        glassPane.addMouseMotionListener(mouseMotionListener);
        glassPane.addKeyListener(keyListener);

        timer.start();
    }

    @Override
    public void stop() {
        timer.stop();

        imageBuf = null;

        glassPane.removeMouseMotionListener(mouseMotionListener);
        glassPane.removeKeyListener(keyListener);

        Window oWindow = SwingUtilities.getWindowAncestor(glassPane);
        if (oWindow != null) {
            oWindow.removeComponentListener(componentAdapter);
        }
    }

    /**
     * Update the figure. This method will be called each turn of the timer.
     */
    void update() {
        if (colorOffset == NUMBER_OF_BARS - 1) {
            colorOffset = 0;
        } else {
            ++colorOffset;
        }

        if (barsScreenBounds == null) {
            glassPane.repaint();
        } else {
            glassPane.repaint(barsScreenBounds);
        }

        if (imageBuf == null) {
            if (iterate < 0) {
                try {
                    makeSnapshot();
                    glassPane.setOpaque(true);
                } catch (AWTException e1) {
                    LoggerFactory.getLogger(getClass()).error(e1.getMessage(), e1);
                }
            } else {
                --iterate;
            }
        }
    }

    /**
     * Make a snapshot.
     *
     * @throws AWTException When an error occurs during the snapshot.
     */
    private void makeSnapshot() throws AWTException {
        Rectangle oRectangle = new Rectangle(glassPane.getBounds());

        imageBuf = new Robot().createScreenCapture(oRectangle);
    }

    /**
     * Build the tickers.
     *
     * @param barCount The number of bars
     * @return All the tickers
     */
    private Area[] buildTicker(int barCount) {
        Area[] ticker = new Area[barCount];
        Point2D center = new Point2D.Double(0, 0);
        double fixedAngle = 2 * Math.PI / barCount;

        for (double i = 0.0; i < barCount; i++) {
            Area primitive = buildPrimitive();

            AffineTransform toCenter = AffineTransform.getTranslateInstance(center.getX(), center.getY());
            AffineTransform toBorder = AffineTransform.getTranslateInstance(35.0, -6.0);
            AffineTransform toCircle = AffineTransform.getRotateInstance(-i * fixedAngle, center.getX(), center.getY());

            AffineTransform toWheel = new AffineTransform();
            toWheel.concatenate(toCenter);
            toWheel.concatenate(toBorder);

            primitive.transform(toWheel);
            primitive.transform(toCircle);

            ticker[(int) i] = primitive;
        }

        return ticker;
    }

    /**
     * Build a primitive.
     *
     * @return A primitive
     */
    private Area buildPrimitive() {
        int length;

        length = glassPane.getWidth() < glassPane.getHeight() ? glassPane.getWidth() / 10 : glassPane.getHeight() / 10;

        Rectangle2D.Double body = new Rectangle2D.Double(6, 0, length, CLOCK_TICK_WIDTH);
        Ellipse2D.Double head = new Ellipse2D.Double(0, 0, CLOCK_TICK_WIDTH, CLOCK_TICK_WIDTH);
        Ellipse2D.Double tail = new Ellipse2D.Double(length, 0, CLOCK_TICK_WIDTH, CLOCK_TICK_WIDTH);

        Area tick = new Area(body);

        tick.add(new Area(head));
        tick.add(new Area(tail));

        return tick;
    }

    @Override
    public void setBounds(int width, int height) {
        centerAndScaleTransform = new AffineTransform();
        centerAndScaleTransform.translate(width / 2d, height / 2d);

        centerAndScaleTransform.scale(SCALE, SCALE);

        if (barsBounds != null) {
            Area oBounds = new Area(barsBounds);
            oBounds.transform(centerAndScaleTransform);
            barsScreenBounds = oBounds.getBounds();
        }
    }

    @Override
    public void setGlassPane(JComponent glassPane) {
        this.glassPane = glassPane;
    }

    /**
     * An ancestor listener for the glass pane.
     *
     * @author Baptiste Wicht
     */
    private final class SimpleGlassPaneAncestorListener implements AncestorListener {
        @Override
        public void ancestorAdded(AncestorEvent event) {
            Window w = SwingUtilities.getWindowAncestor(glassPane);
            if (w != null) {
                w.addComponentListener(componentAdapter);
            }
        }

        @Override
        public void ancestorRemoved(AncestorEvent event) {
            //Nothing to do
        }

        @Override
        public void ancestorMoved(AncestorEvent event) {
            //Nothing to do
        }
    }
}