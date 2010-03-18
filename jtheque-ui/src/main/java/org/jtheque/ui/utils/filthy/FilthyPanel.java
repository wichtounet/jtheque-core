package org.jtheque.ui.utils.filthy;

import org.jtheque.ui.utils.components.Borders;

import javax.swing.JPanel;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;

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
 * A filthy panel. It's an opaque panel with rounded rectangle borders.
 *
 * @author Baptiste Wicht
 */
public final class FilthyPanel extends JPanel {
    /**
     * Construct a new <code>FilthyPanel</code>.
     */
    public FilthyPanel() {
        this(new FlowLayout());
    }

    /**
     * Construct a new <code>FilthyPanel</code>.
     *
     * @param layoutManager The layout manager to use.
     */
    public FilthyPanel(LayoutManager layoutManager) {
        super(layoutManager);

        setOpaque(false);
        setBorder(Borders.createEmptyBorder(10));
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        super.paint(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Composite composite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));

        g2.setColor(Color.white);

        RoundRectangle2D background;
        background = new RoundRectangle2D.Double(3.0, 3.0,
                (double) getWidth() - 10.0 - 3.0,
                (double) getHeight() - 6.0,
                12, 12);

        g2.fill(background);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        Stroke stroke = g2.getStroke();
        g2.setStroke(new BasicStroke(3.0f));
        g2.draw(background);
        g2.setStroke(stroke);

        g2.setComposite(composite);
    }
}