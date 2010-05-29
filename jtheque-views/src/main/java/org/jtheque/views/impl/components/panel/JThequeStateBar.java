package org.jtheque.views.impl.components.panel;

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

import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.IViews;
import org.jtheque.views.able.components.IStateBarComponent;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A State Bar for JTheque.
 *
 * @author Baptiste Wicht
 */
public final class JThequeStateBar extends JPanel {
    private int currentColumn;

    private final GridBagUtils gbc = new GridBagUtils();

    private boolean leftFilled;
    private boolean centerFilled;

    private final IViews viewService;

    /**
     * Construct a new JThequeStateBar.
     *
     * @param viewService The view service.
     */
    public JThequeStateBar(IViews viewService) {
        super();

        this.viewService = viewService;

        setLayout(new GridBagLayout());
        setBackground(new Color(133, 144, 165));

        build();
    }

    /**
     * Build the state bar.
     */
    private void build() {
        List<IStateBarComponent> components = new ArrayList<IStateBarComponent>(viewService.getStateBarComponents());

        if (components.isEmpty()) {
            setVisible(false);
        } else {
            setVisible(true);

            Collections.sort(components, new LeftToRightComparator());

            currentColumn = 0;

            addLeftComponents(components);
            addCenterComponents(components);
            addRightComponents(components);
        }
    }

    /**
     * Add the components in the left.
     *
     * @param components The state bar components.
     */
    private void addLeftComponents(Iterable<IStateBarComponent> components) {
        for (IStateBarComponent component : components) {
            if (component.getPosition() == IStateBarComponent.Position.LEFT) {
                addComponent(component.getComponent());
                leftFilled = true;
            }
        }

        addGlue();
    }

    /**
     * Add the components in the center.
     *
     * @param components The state bar components.
     */
    private void addCenterComponents(Iterable<IStateBarComponent> components) {
        addGlue();

        if (leftFilled) {
            addSeparator();
        }

        for (IStateBarComponent component : components) {
            if (component.getPosition() == IStateBarComponent.Position.CENTER) {
                addComponent(component.getComponent());
                centerFilled = true;
            }
        }

        addGlue();
    }

    /**
     * Add the components in the right.
     *
     * @param components The state bar components.
     */
    private void addRightComponents(Iterable<IStateBarComponent> components) {
        addGlue();

        if (centerFilled) {
            addSeparator();
        }

        for (IStateBarComponent component : components) {
            if (component.getPosition() == IStateBarComponent.Position.RIGHT) {
                addComponent(component.getComponent());
            }
        }
    }

    /**
     * Add a glue.
     */
    private void addGlue() {
        add(Box.createHorizontalGlue(), gbc.gbcSet(
                currentColumn++, 1, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 1.0, 1.0));
    }

    /**
     * Add a separated component.
     *
     * @param component The component to add.
     */
    private void addComponent(Component component) {
        add(component, gbc.gbcSet(currentColumn++, 1));
    }

    /**
     * Add a separator.
     */
    private void addSeparator() {
        Component separator = new JSeparator(JSeparator.VERTICAL);
        separator.setPreferredSize(new Dimension(1, 10));

        add(separator, gbc.gbcSet(currentColumn++, 1));
    }

    /**
     * Remove the component from the state bar.
     *
     * @param component The component to add to the state bar.
     */
    public void removeComponent(IStateBarComponent component) {
        remove(component.getComponent());

        if (viewService.getStateBarComponents().isEmpty()) {
            setVisible(false);
        }

        SwingUtils.refresh(this);
    }

    /**
     * Add the components to the state bar.
     *
     * @param component The component to add.
     */
    public void addComponent(IStateBarComponent component) {
        build();

        SwingUtils.refresh(this);
    }

    /**
     * A comparator to sort the state bar component from left to right.
     *
     * @author Baptiste Wicht
     */
    private static final class LeftToRightComparator implements Comparator<IStateBarComponent> {
        @Override
        public int compare(IStateBarComponent component, IStateBarComponent other) {
            return component.getPosition().compareTo(other.getPosition());
        }
    }
}