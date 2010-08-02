package org.jtheque.views.impl;

import org.jtheque.core.able.Core;
import org.jtheque.states.able.Load;
import org.jtheque.states.able.Save;
import org.jtheque.states.able.State;
import org.jtheque.states.utils.AbstractState;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.views.able.*;
import org.jtheque.views.able.ViewService;
import org.jtheque.xml.utils.Node;
import org.jtheque.xml.utils.NodeAttribute;

import java.awt.Window;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

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
 * A state for persist different views configuration.
 *
 * @author Baptiste Wicht
 */
@State(id = "jtheque-windows-configuration", delegated = true)
public final class WindowsConfiguration extends AbstractState {
    private final Map<String, WindowConfiguration> configurations = CollectionUtils.newHashMap(10);

    private final Core core;
    private final ViewService viewService;

    /**
     * Create a new WindowsConfiguration.
     *
     * @param core        The core.
     * @param viewService The view service.
     */
    public WindowsConfiguration(Core core, org.jtheque.views.able.ViewService viewService) {
        super();

        this.core = core;
        this.viewService = viewService;
    }

    /**
     * Load the nodes into the state.
     *
     * @param nodes The nodes to load.
     */
    @Load
    public void delegateLoad(Iterable<Node> nodes) {
        for (Node node : nodes) {
            if ("window".equals(node.getName())) {
                WindowConfiguration configuration = new org.jtheque.views.utils.WindowConfiguration();

                for (Node child : node.getChildrens()) {
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
    private static void applyValueFromChild(WindowConfiguration configuration, Node child) {
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

    /**
     * Save the nodes of the state.
     *
     * @return All the nodes to be saved by the state.
     */
    @Save
    public Collection<Node> delegateSave() {
        Collection<Node> states = CollectionUtils.newList();

        for (Entry<String, WindowConfiguration> configuration : configurations.entrySet()) {
            Node state = new Node("window");

            state.addAttribute(new NodeAttribute("name", configuration.getKey()));
            state.addChild(new Node("width", Integer.toString(configuration.getValue().getWidth())));
            state.addChild(new Node("height", Integer.toString(configuration.getValue().getHeight())));
            state.addChild(new Node("posX", Integer.toString(configuration.getValue().getPositionX())));
            state.addChild(new Node("posY", Integer.toString(configuration.getValue().getPositionY())));

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
    public void update(String name, Window view) {
        if (core.getConfiguration().retainSizeAndPositionOfWindow()) {
            WindowConfiguration configuration = get(name);

            if (configuration != null) {
                viewService.fill(configuration, view);
            }
        }
    }

    /**
     * Return the window configuration for a view.
     *
     * @param name The name of the view.
     *
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
    public void configure(String name, Window view, int defaultWidth, int defaultHeight) {
        if (core.getConfiguration().retainSizeAndPositionOfWindow()) {
            WindowConfiguration configuration = get(name);

            if (configuration == null) {
                configuration = new org.jtheque.views.utils.WindowConfiguration();

                configuration.setWidth(defaultWidth);
                configuration.setHeight(defaultHeight);
                configuration.setPositionX(-1);
                configuration.setPositionY(-1);

                add(name, configuration);
            }

            viewService.configure(configuration, view);
        } else {
            viewService.setSize(view, defaultWidth, defaultHeight);
        }
    }
}