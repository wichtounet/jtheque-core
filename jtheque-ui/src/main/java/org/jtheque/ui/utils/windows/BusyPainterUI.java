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

import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.jxlayer.plaf.ext.LockableUI;
import org.jdesktop.swingx.painter.BusyPainter;

import javax.swing.JComponent;
import javax.swing.Timer;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

/**
 * Subclass of the {@link LockableUI} which uses the {@link BusyPainterUI} from the SwingX project to implement the
 * "busy effect" when {@link JXLayer} is locked.
 *
 * @author Alexander Potochkin
 */
public final class BusyPainterUI extends LockableUI implements ActionListener {
    private static final long serialVersionUID = -6855096525375295509L;
    
    private final transient BusyPainter busyPainter;
    private final Timer timer;

    /**
     * Create a new BusyPainterUI.
     *
     * @param view The view to create the painter to.
     */
    public BusyPainterUI(Component view) {
        busyPainter = new SimpleBusyPainter();
        busyPainter.setPointShape(new Ellipse2D.Double(0, 0, 20, 20));
        busyPainter.setTrajectory(new Ellipse2D.Double(
                view.getWidth() / 4, view.getHeight() / 4, 
                view.getWidth() / 2, view.getHeight() / 2));

        timer = new Timer(200, this);
    }

    @Override
    protected void paintLayer(Graphics2D g2, JXLayer<? extends JComponent> l) {
        super.paintLayer(g2, l);

        if (isLocked()) {
            busyPainter.paint(g2, l, l.getWidth(), l.getHeight());
        }
    }

    @Override
    public void setLocked(boolean isLocked) {
        super.setLocked(isLocked);

        if (isLocked) {
            timer.start();
        } else {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        busyPainter.setFrame((busyPainter.getFrame() + 1) % 8);
        setDirty(true);
    }

    /**
     * A simple busy painter for a Window.
     *
     * @author Baptiste Wicht
     */
    private static final class SimpleBusyPainter extends BusyPainter {
        @Override
        protected void doPaint(Graphics2D g, Object object, int width, int height) {
            Rectangle r = getTrajectory().getBounds();
            int tw = width - r.width - 2 * r.x;
            int th = height - r.height - 2 * r.y;
            g.translate(tw / 2, th / 2);
            super.doPaint(g, object, width, height);
        }
    }
}
