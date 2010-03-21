package org.jtheque.views.able.components;

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

import javax.swing.JComponent;
import java.awt.Component;

/**
 * A state bar component.
 *
 * @author Baptiste Wicht
 */
public final class StateBarComponent {
    private final JComponent component;
    private final Position position;

    /**
     * A position for state bar component.
     *
     * @author Baptiste Wicht
     */
    public enum Position {
        LEFT,
        CENTER,
        RIGHT,
    }

    /**
     * Construct a new StateBarComponent.
     *
     * @param component The component.
     * @param position  The position of the component.
     */
    public StateBarComponent(JComponent component, Position position) {
        super();

        this.component = component;
        this.position = position;
    }

    /**
     * Return the component.
     *
     * @return The component.
     */
    public Component getComponent() {
        return component;
    }

    /**
     * Return the position of the state bar component.
     *
     * @return The position of the component.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Indicate if the state bar is positioned to the left.
     *
     * @return true if the state bar is positioned to the left.
     */
    public boolean isLeft() {
        return position == Position.LEFT;
    }

    /**
     * Indicate if the state bar is positioned to the right.
     *
     * @return true if the state bar is positioned to the right.
     */
    public boolean isCenter() {
        return position == Position.CENTER;
    }

    /**
     * Indicate if the state bar is positioned to the center.
     *
     * @return true if the state bar is positioned to the center.
     */
    public boolean isRight() {
        return position == Position.RIGHT;
    }
}