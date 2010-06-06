package org.jtheque.ui.utils.windows.frames;

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

import org.jtheque.core.able.ICore;
import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.images.able.IImageService;
import org.jtheque.ui.able.IModel;
import org.jtheque.ui.able.IWindowView;
import org.jtheque.utils.ui.SwingUtils;

import org.osgi.framework.BundleContext;
import org.springframework.osgi.context.BundleContextAware;

import javax.swing.JFrame;

import java.awt.Image;

/**
 * A swing frame view.
 *
 * @author Baptiste Wicht
 */
public abstract class SwingFrameView extends JFrame implements IWindowView, BundleContextAware {
    private IModel model;

    private BundleContext bundleContext;

    /**
     * Construct a new SwingFrameView.
     */
    protected SwingFrameView() {
        super("");
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
    protected Image getDefaultWindowIcon() {
        return getService(IImageService.class).getImage(ICore.WINDOW_ICON);
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

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    /**
     * Return the service of the given class.
     *
     * @param classz The class of the service to get.
     * @param <T>    The type of service to get.
     * @return The service of the given class.
     */
    <T> T getService(Class<T> classz) {
        return OSGiUtils.getService(bundleContext, classz);
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