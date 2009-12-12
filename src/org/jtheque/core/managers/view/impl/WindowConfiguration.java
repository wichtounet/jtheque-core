package org.jtheque.core.managers.view.impl;

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