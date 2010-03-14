package org.jtheque.views.impl.components;

import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.jxlayer.plaf.BufferedLayerUI;
import org.jtheque.ui.WaitFigure;
import org.jtheque.ui.utils.frames.InfiniteWaitFigure;
import org.jtheque.utils.ui.SizeTracker;

import javax.swing.JComponent;
import java.awt.Graphics2D;

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
 * An UI to make a JXLayer waiting.
 *
 * @author Baptiste Wicht
 */
public final class InfiniteWaitUI extends BufferedLayerUI<JComponent> {
    private final WaitFigure waitFigure;

    private final SizeTracker tracker;
    
    /**
     * Construct a new <code>InfiniteWaitUI</code>.
     * 
     * @param content The content pane of the view.
     */
    public InfiniteWaitUI(JXLayer<JComponent> content) {
        super();

        waitFigure = new InfiniteWaitFigure();
        waitFigure.setGlassPane(content);
        waitFigure.init();
        
        tracker = new SizeTracker(content);
    }
    
    @Override
    public void paintLayer(Graphics2D g2, JXLayer<? extends JComponent> layer) {
        if(tracker.hasSizeChanged()){
            tracker.updateSize();
            waitFigure.setBounds(layer.getWidth(), layer.getHeight());
        }
        
        waitFigure.paint(g2);
    }

    /**
     * Start the animation.
     */
    public void start() {
        setAlpha(1.0f);
        waitFigure.start();
    }

    /**
     * Stop the animation.
     */
    public void stop() {
        setAlpha(0.0f);
        waitFigure.stop();
    }
}