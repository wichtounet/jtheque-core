package org.jtheque.views.components;

import java.awt.Component;

/**
 * A state bar component. This is component who get in the state bar.
 *
 * @author Baptiste Wicht
 */
public interface StateBarComponent {
    /**
     * Return the component.
     *
     * @return The component.
     */
    Component getComponent();

    /**
     * Return the position of the state bar component.
     *
     * @return The position of the component.
     */
    Position getPosition();

    /**
     * A position for state bar component.
     *
     * @author Baptiste Wicht
     */
    enum Position {
        LEFT,
        CENTER,
        RIGHT,
    }
}
