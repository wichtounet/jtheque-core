package org.jtheque.core.managers.view.impl;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.state.AbstractState;
import org.jtheque.core.managers.state.NodeState;
import org.jtheque.core.managers.state.NodeStateAttribute;
import org.jtheque.core.managers.view.able.IView;
import org.jtheque.core.managers.view.able.IViewManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
 * A state for persist different views configuration.
 *
 * @author Baptiste Wicht
 */
public final class WindowsConfiguration extends AbstractState {
    private final Map<String, WindowConfiguration> configurations = new HashMap<String, WindowConfiguration>(10);

    @Override
    public boolean isDelegated() {
        return true;
    }

    @Override
    public void delegateLoad(Collection<NodeState> nodes) {
        for (NodeState node : nodes) {
            if ("window".equals(node.getName())) {
                WindowConfiguration configuration = new WindowConfiguration();

                for (NodeState child : node.getChildrens()) {
                    applyValueFromChild(configuration, child);
                }

                add(node.getAttributeValue("name"), configuration);
            }
        }
    }

    /**
     * Apply the value from child.
     *
     * @param configuration The window configuration.
     * @param child         The child.
     */
    private static void applyValueFromChild(WindowConfiguration configuration, NodeState child) {
        if ("width".equals(child.getName())) {
            configuration.setWidth(Integer.parseInt(child.getText()));
        } else if ("height".equals(child.getName())) {
            configuration.setHeight(Integer.parseInt(child.getText()));
        } else if ("posX".equals(child.getName())) {
            configuration.setPositionX(Integer.parseInt(child.getText()));
        } else if ("posY".equals(child.getName())) {
            configuration.setPositionY(Integer.parseInt(child.getText()));
        }
    }

    @Override
    public Collection<NodeState> delegateSave() {
        Collection<NodeState> states = new ArrayList<NodeState>(10);

        for (Entry<String, WindowConfiguration> configuration : configurations.entrySet()) {
            NodeState state = new NodeState("window");

            state.getAttributes().add(new NodeStateAttribute("name", configuration.getKey()));
            state.getChildrens().add(new NodeState("width", Integer.toString(configuration.getValue().getWidth())));
            state.getChildrens().add(new NodeState("height", Integer.toString(configuration.getValue().getHeight())));
            state.getChildrens().add(new NodeState("posX", Integer.toString(configuration.getValue().getPositionX())));
            state.getChildrens().add(new NodeState("posY", Integer.toString(configuration.getValue().getPositionY())));

            states.add(state);
        }

        return states;
    }

    /**
     * Add a window configuration for a view.
     *
     * @param name          The name of the view.
     * @param configuration The configuration to add.
     */
    private void add(String name, WindowConfiguration configuration) {
        configurations.put(name, configuration);
    }

    /**
     * Update the configuration with the view.
     *
     * @param name The name of the view.
     * @param view The view.
     */
    public void update(String name, IView view) {
        if (Managers.getCore().getConfiguration().retainSizeAndPositionOfWindow()) {
            WindowConfiguration configuration = get(name);

            if (configuration != null) {
                Managers.getManager(IViewManager.class).getViewDelegate().fill(configuration, view);
            }
        }
    }

    /**
     * Return the window configuration for a view.
     *
     * @param name The name of the view.
     * @return The window configuration for the view.
     */
    private WindowConfiguration get(String name) {
        return configurations.get(name);
    }

    /**
     * Configure the view.
     *
     * @param name          The name of the view.
     * @param view          The view.
     * @param defaultWidth  The default width of the view.
     * @param defaultHeight The default height of the view.
     */
    public void configure(String name, IView view, int defaultWidth, int defaultHeight) {
        if (Managers.getCore().getConfiguration().retainSizeAndPositionOfWindow()) {
            WindowConfiguration configuration = get(name);

            if (configuration == null) {
                configuration = new WindowConfiguration();

                configuration.setWidth(defaultWidth);
                configuration.setHeight(defaultHeight);
                configuration.setPositionX(-1);
                configuration.setPositionY(-1);

                add(name, configuration);
            }

            Managers.getManager(IViewManager.class).getViewDelegate().configure(configuration, view);
        } else {
            Managers.getManager(IViewManager.class).getViewDelegate().setSize(view, defaultWidth, defaultHeight);
        }
    }
}