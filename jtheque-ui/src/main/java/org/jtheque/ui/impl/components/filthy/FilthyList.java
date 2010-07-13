package org.jtheque.ui.impl.components.filthy;

import org.jtheque.ui.able.components.Borders;

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