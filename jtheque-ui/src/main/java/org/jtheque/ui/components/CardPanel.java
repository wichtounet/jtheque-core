package org.jtheque.ui.components;

import org.jtheque.utils.collections.CollectionUtils;

import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.Component;
import java.util.Map;

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
 * A panel with different layers. Only one layer is displayed at the same time.
 *
 * @author Baptiste Wicht
 * @param <T> The type of component to display for layer.
 */
public class CardPanel<T extends Component> extends JPanel {
    private final CardLayout layout;

    private T current;

    private final Map<String, T> layers = CollectionUtils.newHashMap(5);

    /**
     * Construct a new <code>CardPanel</code>.
     */
    public CardPanel() {
        super();

        layout = new CardLayout();

        setLayout(layout);
    }

    /**
     * Add a layer to the panel.
     *
     * @param component The component.
     * @param layer     The layer key.
     */
    public void addLayer(T component, String layer) {
        layers.put(layer, component);

        add(component, layer);
    }

    /**
     * Return all the layers of the of the panel.
     *
     * @return A <code>Collection</code> containing all the layers of the panel.
     */
    public Iterable<T> getLayers() {
        return layers.values();
    }

    /**
     * Return the currently displayed layer.
     *
     * @return The current layer.
     */
    public T getCurrentLayer() {
        return current;
    }

    /**
     * Display the layer.
     *
     * @param layer The key of the layer.
     */
    public void displayLayer(String layer) {
        current = layers.get(layer);

        layout.show(this, layer);
    }
}