package org.jtheque.views.impl;

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
 * A Window Configuration. It seems a state to persist the location and the size on a window in closing.
 *
 * @author Baptiste Wicht
 */
public final class WindowConfiguration {
    private int width;
    private int height;
    private int positionX;
    private int positionY;

    /**
     * Set the width of the window.
     *
     * @param width The width of the window.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Set the height of the window.
     *
     * @param height The height of the window.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Set the X position of the window.
     *
     * @param positionX The X position of the window.
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * Set the Y position of the window.
     *
     * @param positionY The Y position of the window.
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    /**
     * Return the width of the window.
     *
     * @return The width of the window.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Return the height of the window.
     *
     * @return The height of the window.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Return the Y position of the window.
     *
     * @return The Y position of the window.
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * Return the X position of the window.
     *
     * @return The X position of the window.
     */
    public int getPositionX() {
        return positionX;
    }
}