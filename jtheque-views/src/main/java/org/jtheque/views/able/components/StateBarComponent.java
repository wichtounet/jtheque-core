package org.jtheque.views.able.components;

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