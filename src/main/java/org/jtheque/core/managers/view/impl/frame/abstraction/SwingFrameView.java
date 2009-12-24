package org.jtheque.core.managers.view.impl.frame.abstraction;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.view.able.IWindowView;
import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.JFrame;
import java.awt.Image;

/**
 * A swing frame view.
 *
 * @author Baptiste Wicht
 */
public abstract class SwingFrameView extends JFrame implements IWindowView {
    private IModel model;

    /**
     * Construct a new SwingFrameView.
     */
    protected SwingFrameView() {
        this("");
    }

    /**
     * Construct a new SwingFrameView.
     *
     * @param title The title of the frame.
     */
    private SwingFrameView(String title) {
        super(title);
    }

    /**
     * Return the default icon for the window icon.
     *
     * @return The default icon.
     */
    protected static Image getDefaultWindowIcon() {
        return Managers.getManager(IResourceManager.class).getImage(
                Managers.getCore().getApplication().getWindowIcon(),
                Managers.getCore().getApplication().getWindowIconType());
    }

    @Override
    public void closeDown() {
        setVisible(false);
    }

    @Override
    public final void display() {
        setVisible(true);
    }

    @Override
    public final void toFirstPlan() {
        toFront();
    }

    @Override
    public void sendMessage(String message, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void refresh() {
        SwingUtils.refresh(this);
    }

    /**
     * Set the model of the view.
     *
     * @param model The model of the view.
     */
    public final void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public final IModel getModel() {
        return model;
    }

    @Override
    public final JFrame getImpl() {
        return this;
    }
}