package org.jtheque.views.impl.components;

import org.jtheque.ui.able.WaitFigure;
import org.jtheque.ui.utils.windows.InfiniteWaitFigure;
import org.jtheque.utils.ui.SizeTracker;

import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.jxlayer.plaf.BufferedLayerUI;

import javax.swing.JComponent;

import java.awt.Graphics2D;

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
        if (tracker.hasSizeChanged()) {
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