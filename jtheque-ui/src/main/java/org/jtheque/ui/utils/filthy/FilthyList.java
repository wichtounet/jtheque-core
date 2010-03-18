package org.jtheque.ui.utils.filthy;
import org.jtheque.ui.utils.components.Borders;

import javax.swing.JList;
import javax.swing.ListModel;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
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
 * A filthy list.
 *
 * @author Baptiste Wicht
 */
public final class FilthyList extends JList {
    /**
     * Construct a new <code>FilthyList</code>.
     *
     * @param dataModel The model to store data.
     */
    public FilthyList(ListModel dataModel) {
        super(dataModel);

        setOpaque(false);
        setBorder(Borders.createEmptyBorder(5));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Composite composite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));

        g2.setColor(Color.white);

        Shape background = new RoundRectangle2D.Double(3.0, 3.0,
                (double) getWidth() - 18.0,
                (double) getHeight() - 6.0,
                10, 10);
        g2.fill(background);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        Stroke stroke = g2.getStroke();
        g2.setStroke(new BasicStroke(3.0f));
        g2.draw(background);
        g2.setStroke(stroke);

        g2.setComposite(composite);

        super.paintComponent(g);
    }
}