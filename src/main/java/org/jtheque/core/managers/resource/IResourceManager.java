package org.jtheque.core.managers.resource;

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

import org.springframework.core.io.Resource;

import javax.swing.Action;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * A resource manager.
 *
 * @author Baptiste Wicht
 */
public interface IResourceManager {
    /**
     * Return the resource of the path.
     *
     * @param path The path to the resource.
     * @return The loaded <code>Resource</code>.
     */
    Resource getResource(String path);

    /**
     * Return the resource as stream.
     *
     * @param path The path to the resource.
     * @return The <code>InputStream</code> of the loaded resource.
     */
    InputStream getResourceAsStream(String path);

    /**
     * Load and return the icon.
     *
     * @param baseName The images base name.
     * @param id       The image index.
     * @param type     The image type.
     * @return The loaded icon.
     */
    ImageIcon getIcon(String baseName, String id, ImageType type);

    /**
     * Load and return the icon.
     *
     * @param id   The image index.
     * @param type The image type.
     * @return The loaded icon.
     */
    ImageIcon getIcon(String id, ImageType type);

    /**
     * Load and return the icon.
     *
     * @param id The image index.
     * @return The loaded icon.
     */
    ImageIcon getIcon(String id);

    /**
     * Load, return and resize the image.
     *
     * @param baseName The image base name.
     * @param id       The image index.
     * @param type     The image type.
     * @param width    The width.
     * @return The loaded icon.
     */
    BufferedImage getImage(String baseName, String id, ImageType type, int width);

    /**
     * Load and return the image.
     *
     * @param baseName The image base name.
     * @param id       The image index.
     * @param type     The image type.
     * @return The loaded icon.
     */
    BufferedImage getImage(String baseName, String id, ImageType type);

    /**
     * Load, return and resize the image.
     *
     * @param id    The image index.
     * @param type  The image type.
     * @param width The width.
     * @return The loaded icon.
     */
    BufferedImage getImage(String id, ImageType type, int width);

    /**
     * Load and return the image.
     *
     * @param id   The image index.
     * @param type The image type.
     * @return The loaded icon.
     */
    BufferedImage getImage(String id, ImageType type);

    /**
     * Load, return and resize the image.
     *
     * @param id    The image index.
     * @param width The width.
     * @return The loaded icon.
     */
    BufferedImage getImage(String id, int width);

    /**
     * Load and return the image.
     *
     * @param id The image index.
     * @return The loaded icon.
     */
    BufferedImage getImage(String id);

    /**
     * Return the color of the specified name. The color will be searched in the Spring context.
     *
     * @param name The name of the color.
     * @return The color of the specified name.
     */
    Color getColor(String name);

    /**
     * Return the action of the specified name. The color will be searched in the Spring context.
     *
     * @param name The name of the color.
     * @return The action of the specified name.
     */
    Action getAction(String name);

    /**
     * Invalidate the entire cache. 
     */
    void invalidateCache();

    /**
     * Invalidate the specified cached image.
     *
     * @param id The id of the image to invalidate. 
     */
    void invalidateImage(String id);
}