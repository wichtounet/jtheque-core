package org.jtheque.views.impl.panel;

import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

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
 * A panel with different layers. Only one layer is displayed at the same time.
 *
 * @author Baptiste Wicht
 * @param <T> The type of component to display for layer.
 */
public class CardPanel<T extends Component> extends JPanel {
    private final CardLayout layout;

    private T current;

    private final Map<String, T> layers = new HashMap<String, T>(5);

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