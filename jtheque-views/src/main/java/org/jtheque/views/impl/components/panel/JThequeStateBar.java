package org.jtheque.views.impl.components.panel;

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

import org.jtheque.ui.able.IUIUtils;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.ViewsServices;
import org.jtheque.views.able.IViewService;
import org.jtheque.views.able.components.StateBarComponent;

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

    /**
     * Construct a new JThequeStateBar.
     */
    public JThequeStateBar() {
        super();

        setLayout(new GridBagLayout());
        setBackground(new Color(133, 144, 165));

        build();
    }

    /**
     * Build the state bar.
     */
    private void build() {
        List<StateBarComponent> components = new ArrayList<StateBarComponent>(getManager().getStateBarComponents());

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
    private void addLeftComponents(Iterable<StateBarComponent> components) {
        for (StateBarComponent component : components) {
            if (component.isLeft()) {
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
    private void addCenterComponents(Iterable<StateBarComponent> components) {
        addGlue();

        if (leftFilled) {
            addSeparator();
        }

        for (StateBarComponent component : components) {
            if (component.isCenter()) {
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
    private void addRightComponents(Iterable<StateBarComponent> components) {
        addGlue();

        if (centerFilled) {
            addSeparator();
        }

        for (StateBarComponent component : components) {
            if (component.isRight()) {
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

    public void removeComponent(StateBarComponent component) {
        remove(component.getComponent());

        if (getManager().getStateBarComponents().isEmpty()) {
            setVisible(false);
        }

        ViewsServices.get(IUIUtils.class).getDelegate().refresh(this);
    }

    public void addComponent(StateBarComponent component) {
        build();

        ViewsServices.get(IUIUtils.class).getDelegate().refresh(this);
    }

    private static IViewService getManager() {
        return ViewsServices.get(IViewService.class);
    }

    /**
     * A comparator to sort the state bar component from left to right.
     *
     * @author Baptiste Wicht
     */
    private static final class LeftToRightComparator implements Comparator<StateBarComponent> {
        @Override
        public int compare(StateBarComponent component, StateBarComponent other) {
            return component.getPosition().compareTo(other.getPosition());
        }
    }
}