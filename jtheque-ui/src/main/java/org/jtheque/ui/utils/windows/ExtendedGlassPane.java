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

import org.jtheque.ui.able.IWindowView;
import org.jtheque.ui.able.WaitFigure;

import javax.swing.JPanel;

import java.awt.Cursor;
import java.awt.Graphics;

/**
 * An Extended Glass Pane. It's a glass pane on which we can display an wait animation.
 *
 * @author Baptiste Wicht
 */
public final class ExtendedGlassPane extends JPanel {
    private WaitFigure waitFigure;

    private boolean wait;

    private final IWindowView frame;

    /**
     * Construct a new glass pane associated to a frame.
     *
     * @param frame The parent frame
     */
    public ExtendedGlassPane(IWindowView frame) {
        super();

        this.frame = frame;

        setOpaque(false);
    }

    /**
     * Start the wait.
     */
    public void startWait() {
        wait = true;
        setVisible(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        waitFigure.start();
    }

    /**
     * Stop the wait.
     */
    public void stopWait() {
        wait = false;
        setVisible(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        waitFigure.stop();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

        waitFigure.setBounds(width, height);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        if (wait) {
            waitFigure.paint(graphics);
        }
    }

    /**
     * Set the wait figure.
     *
     * @param waitFigure The new wait figure
     */
    public void setWaitFigure(WaitFigure waitFigure) {
        this.waitFigure = waitFigure;
        waitFigure.setGlassPane(this);
        waitFigure.init();
    }

    /**
     * Get the window of the glass pane.
     *
     * @return The window.
     */
    public IWindowView getWindow() {
        return frame;
    }
}