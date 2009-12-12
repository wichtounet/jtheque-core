package org.jtheque.core.managers.view.impl.components;

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

import org.jtheque.core.managers.view.able.IWindowView;

import javax.swing.JPanel;
import java.awt.Cursor;
import java.awt.Graphics;

/**
 * An Extended Glass Pane. It's a glass pane on which we can display an wait animation.
 *
 * @author Baptiste Wicht
 */
public final class ExtendedGlassPane extends JPanel {
    private static final long serialVersionUID = -6859351186159311114L;

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