package org.jtheque.ui.components;

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

import org.jtheque.i18n.LanguageService;
import org.jtheque.ui.ViewComponent;
import org.jtheque.utils.collections.CollectionUtils;

import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.jxlayer.plaf.BufferedLayerUI;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * A tabbed pane with different layer.
 *
 * @author Baptiste Wicht
 */
public class LayerTabbedPane extends JTabbedPane implements ViewComponent {
    private final List<JXLayer<JComponent>> components = CollectionUtils.newList(5);

    private final LanguageService languageService;

    /**
     * Construct a new LayerTabbedPane.
     *
     * @param languageService The language service.
     */
    public LayerTabbedPane(LanguageService languageService) {
        super();

        this.languageService = languageService;

        addChangeListener(new TabbedAnimatingChangeListener());
    }

    /**
     * Add an internationalized tab.
     *
     * @param key       The internationalization key.
     * @param component The component to add.
     */
    public final void addInternationalizedTab(String key, JComponent component) {
        addLayeredTab(languageService.getMessage(key), component);
    }

    /**
     * Add a tab.
     *
     * @param title     The title of the tab.
     * @param component The tab component.
     */
    public final void addLayeredTab(String title, JComponent component) {
        JXLayer<JComponent> layerComponent = new JXLayer<JComponent>(component);

        components.add(layerComponent);
        addTab(title, layerComponent);
    }

    @Override
    public final JComponent getSelectedComponent() {
        if (getSelectedIndex() != -1) {
            return components.get(getSelectedIndex()).getView();
        }

        return null;
    }

    /**
     * Return the selected layer.
     *
     * @return the selected layer or null if there is no selected layer.
     */
    final JXLayer<JComponent> getSelectedLayer() {
        JXLayer<JComponent> layer = null;

        if (getSelectedIndex() != -1) {
            layer = components.get(getSelectedIndex());
        }

        return layer;
    }

    /**
     * Return the layer at a certain index.
     *
     * @param index The index for which we search the layer.
     *
     * @return the corresponding layer.
     */
    final JXLayer<JComponent> getLayerAt(int index) {
        return components.get(index);
    }

    /**
     * A listener to animate the tabbed pane.
     *
     * @author Baptiste Wicht
     */
    private static final class TabbedAnimatingChangeListener implements ChangeListener {
        private int index;
        private final Timer timer;
        private final AnimationLayerUI layerUI;
        private float delta;

        private static final int ANIMATION_DELAY = 50;

        /**
         * Construct a new <code>TabbedAnimatingChangeListener</code>.
         */
        private TabbedAnimatingChangeListener() {
            super();

            delta = 0.1f;
            layerUI = new AnimationLayerUI();

            timer = new Timer(ANIMATION_DELAY, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (layerUI.getAlpha() <= delta) {
                        layerUI.setAlpha(0);
                        timer.stop();
                        return;
                    }

                    layerUI.setAlpha(layerUI.getAlpha() - delta);
                }
            });
        }

        /**
         * Return the current delta for alpha.
         *
         * @return The delta for alpha value.
         */
        public float getDelta() {
            return delta;
        }

        /**
         * Set the delta for alpha.
         *
         * @param delta The delta.
         */
        public void setDelta(float delta) {
            if (delta <= 0 || delta > 1) {
                throw new IllegalArgumentException();
            }
            this.delta = delta;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            LayerTabbedPane pane = (LayerTabbedPane) e.getSource();
            JXLayer<JComponent> layer = pane.getSelectedLayer();
            JXLayer<JComponent> oldLayer = pane.getLayerAt(index);

            if (oldLayer != null && layer != null) {
                layerUI.setAlpha(1 - layerUI.getAlpha());

                layerUI.setComponent(oldLayer);

                oldLayer.setUI(layer.getUI());
                layer.setUI(layerUI);

                //painter.org.jtheque.update();
                timer.start();
                index = pane.getSelectedIndex();
            }
        }
    }

    @Override
    public Object getImpl() {
        return this;
    }

    /**
     * A layer UI for the animation.
     *
     * @author Baptiste Wicht
     */
    private static final class AnimationLayerUI extends BufferedLayerUI<JComponent> {
        private static final long serialVersionUID = 4575416080010970635L;

        private transient BufferedImage componentImage;

        /**
         * Construct a new <code>AnimationLayerUI</code>.
         */
        private AnimationLayerUI() {
            setAlpha(0);
            setIncrementalUpdate(false);
        }

        /**
         * Set the component to paint.
         *
         * @param component The component to paint.
         */
        public void setComponent(Component component) {
            if (component == null
                    || component.getWidth() <= 0 || component.getHeight() <= 0) {
                componentImage = null;
            } else {
                if (componentImage == null
                        || componentImage.getWidth() != component.getWidth()
                        || componentImage.getHeight() != component.getHeight()) {
                    componentImage = createBuffer(component.getWidth(), component.getHeight());
                }
                Graphics g = componentImage.getGraphics();
                component.paint(g);
                g.dispose();
            }
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            // always paint the layer
            c.paint(g);
            super.paint(g, c);
        }

        @Override
        protected void paintLayer(Graphics2D graphics2D, JXLayer<? extends JComponent> jxLayer) {
            if (componentImage != null) {
                // paint the old layer with diminishing alpha
                graphics2D.drawImage(componentImage, 0, 0, null);
            }
        }
    }
}