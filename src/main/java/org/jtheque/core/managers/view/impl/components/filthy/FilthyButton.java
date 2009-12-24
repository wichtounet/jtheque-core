package org.jtheque.core.managers.view.impl.components.filthy;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.ui.PaintUtils;

import javax.swing.Action;
import javax.swing.JButton;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextLayout;
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

/*
    Copyright (c) 2009, Aerith Project Team
    All rights reserved.

    Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    Redistribution of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    Redistribution in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or 
    other materials provided with the distribution.
    Neither the name of the <ORGANIZATION> nor the names of its contributors may be used to endorse or promote products derived from this software without 
    specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
    HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
    LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
    THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
    THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

/**
 * A filthy Button. Adapted from Aerith Project. The main attribute indicate if the button is orange or normal.
 *
 * @author Baptiste Wicht
 * @author Romain Guy
 * @author Chet Haase
 */
public final class FilthyButton extends JButton {
    private float shadowOffsetX;
    private float shadowOffsetY;

    private static final double SHADOW_DIRECTION = 1.0471975511965976; //60°
    private static final int SHADOW_DISTANCE = 1;

    private Color shadowColor;

    private Image mainButton;
    private Image mainButtonPressed;
    private Image normalButton;
    private Image normalButtonPressed;
    private Image buttonHighlight;

    private static final Insets SOURCE_INSETS = new Insets(10, 10, 10, 10);
    private Insets insets = new Insets(2, 5, 2, 5);

    private float ghostValue;

    private boolean main;

    /**
     * Construct a new <code>FilthyButton</code>.
     *
     * @param action The action.
     */
    public FilthyButton(Action action) {
        super(action);

        init(0);
    }

    /**
     * Construct a <code>FilthyButton</code>.
     *
     * @param action The action of the button.
     * @param main   Indicate if the button is main or not.
     */
    public FilthyButton(Action action, boolean main) {
        super(action);

        this.main = main;

        init(0);
    }

    /**
     * Construct a <code>FilthyButton</code>.
     *
     * @param action The action of the button.
     * @param size   The font size.
     */
    public FilthyButton(Action action, float size) {
        super(action);

        init(size);
    }

    /**
     * Construct a <code>FilthyButton</code>.
     *
     * @param action The action of the button.
     * @param main   Indicate if the button is main or not.
     * @param size   The font size.
     */
    public FilthyButton(Action action, float size, boolean main) {
        super(action);

        this.main = main;

        init(size);
    }

    /**
     * Construct a <code>FilthyButton</code>.
     *
     * @param text The text of the button.
     */
    public FilthyButton(String text) {
        super(text);

        init(0);
    }

    /**
     * Construct a <code>FilthyButton</code>.
     */
    public FilthyButton() {
        super();

        init(0);
    }

    /**
     * Init the button.
     *
     * @param size The font size.
     */
    private void init(float size) {
        initImages();

        shadowColor = Managers.getManager(IViewManager.class).getViewDefaults().getFilthyBackgroundColor();

        computeShadow();

        addMouseListener(new HighlightHandler());

        setBorder(Borders.EMPTY_BORDER);
        setForeground(Managers.getManager(IViewManager.class).getViewDefaults().getFilthyForegroundColor());

        setFont(Managers.getManager(IViewManager.class).getViewDefaults().getFilthyButtonFont());

        if (size > 0) {
            setFont(getFont().deriveFont(size));
        }

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusable(false);
    }

    /**
     * Set the font size.
     *
     * @param size The font size.
     */
    public void setFontSize(float size) {
        setFont(getFont().deriveFont(size));
    }

    /**
     * Init the images.
     */
    private void initImages() {
        String baseName = Managers.getCore().getImagesBaseName();

        normalButton = Managers.getManager(IResourceManager.class).getImage(baseName, "button-normal", ImageType.PNG);
        normalButtonPressed = Managers.getManager(IResourceManager.class).getImage(baseName, "button-normal-pressed", ImageType.PNG);
        mainButton = Managers.getManager(IResourceManager.class).getImage(baseName, "button-main", ImageType.PNG);
        mainButtonPressed = Managers.getManager(IResourceManager.class).getImage(baseName, "button-main-pressed", ImageType.PNG);
        buttonHighlight = Managers.getManager(IResourceManager.class).getImage(baseName, "halo", ImageType.PNG);
    }

    /**
     * Set if the button is main or not.
     *
     * @param main The main attribute.
     */
    public void setMain(boolean main) {
        boolean old = this.main;
        this.main = main;
        firePropertyChange("main", old, this.main);
    }

    /**
     * Indicate if the button is main or not.
     *
     * @return true if the button is main else false.
     */
    public boolean isMain() {
        return main;
    }

    /**
     * Compute the shadow.
     */
    private void computeShadow() {
        shadowOffsetX = (float) StrictMath.cos(SHADOW_DIRECTION) * SHADOW_DISTANCE;
        shadowOffsetY = (float) StrictMath.sin(SHADOW_DIRECTION) * SHADOW_DISTANCE;
    }

    /**
     * Return the image for the button.
     *
     * @param armed A boolean flag indicating if the button is armed or not.
     * @return The image.
     */
    private Image getImage(boolean armed) {
        if (main) {
            return armed ? mainButtonPressed : mainButton;
        } else {
            return armed ? normalButtonPressed : normalButton;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        PaintUtils.tileStretchPaint(g2, this, (BufferedImage) getImage(getModel().isArmed()), SOURCE_INSETS);

        drawGhost(g2);

        if (StringUtils.isNotEmpty(getText())) {
            drawText(g2);
        } else if (getIcon() != null) {
            drawIcon(g2);
        }
    }

    /**
     * Draw icon.
     *
     * @param g2 The Graphics object to paint.
     */
    private void drawIcon(Graphics g2) {
        int x = (getWidth() - getIcon().getIconWidth()) / 2;
        int y = (getHeight() - getIcon().getIconHeight()) / 2;

        getIcon().paintIcon(this, g2, x, y);
    }

    @Override
    public Insets getInsets() {
        return insets;
    }

    @Override
    public void setMargin(Insets m) {
        super.setMargin(m);

        insets = m;
    }

    /**
     * Draw the text.
     *
     * @param g2 The graphics2D element to paint to.
     */
    private void drawText(Graphics2D g2) {
        FontMetrics fm = getFontMetrics(getFont());
        TextLayout layout = new TextLayout(getText(), getFont(), g2.getFontRenderContext());

        int x = Math.max((int) (getWidth() - insets.left - insets.right - layout.getBounds().getWidth()) / 2, insets.left) + 2;
        int y = (getHeight() - insets.top - insets.bottom - fm.getMaxAscent() - fm.getMaxDescent()) / 2 + fm.getAscent() + 1;

        if (getModel().isArmed()) {
            x += 1;
            y += 1;
        }

        g2.setColor(shadowColor);
        layout.draw(g2, x + (int) Math.ceil(shadowOffsetX), y + (int) Math.ceil(shadowOffsetY));

        g2.setColor(getForeground());
        layout.draw(g2, x, y);
    }

    /**
     * Draw the ghost.
     *
     * @param g2 The graphics2D element to paint to.
     */
    private void drawGhost(Graphics2D g2) {
        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;

        if (ghostValue > 0.0f) {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            float alphaValue = ghostValue;

            Composite composite = g2.getComposite();
            if (composite instanceof AlphaComposite) {
                alphaValue *= ((AlphaComposite) composite).getAlpha();
            }

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));

            g2.drawImage(buttonHighlight,
                    insets.left + 2, insets.top + 2,
                    width - 4, height - 4, null);

            g2.setComposite(composite);
        }
    }

    /**
     * A mouse adapter for highlighting the button.
     *
     * @author Baptiste Wicht
     */
    private final class HighlightHandler extends MouseAdapter {
        private Animator timer;
        private static final int ANIMATION_DURATION = 300;

        @Override
        public void mouseEntered(MouseEvent e) {
            launchGhost(true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            launchGhost(false);
        }

        /**
         * Launch the ghost.
         *
         * @param forward Indicate if we must start.
         */
        private void launchGhost(boolean forward) {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }

            timer = new Animator(ANIMATION_DURATION, new AnimateGhost(forward));
            timer.start();
        }
    }

    /**
     * An animated ghost.
     *
     * @author Baptiste Wicht
     */
    private final class AnimateGhost extends TimingTargetAdapter {
        private final boolean forward;
        private final float oldValue;

        /**
         * Construct a new AnimateGhost.
         *
         * @param forward Indicate if we must start.
         */
        AnimateGhost(boolean forward) {
            super();

            this.forward = forward;
            oldValue = ghostValue;
        }

        @Override
        public void timingEvent(float fraction) {
            ghostValue = oldValue + fraction * (forward ? 1.0f : -1.0f);

            if (ghostValue > 1.0f) {
                ghostValue = 1.0f;
            } else if (ghostValue < 0.0f) {
                ghostValue = 0.0f;
            }

            repaint();
        }
    }
}