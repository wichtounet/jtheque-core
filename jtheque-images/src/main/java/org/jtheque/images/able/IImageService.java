package org.jtheque.images.able;

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

import org.springframework.core.io.Resource;

import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;

/**
 * A resource manager. All the images and icons are cached and only created once. If the cache is invalidated or if the
 * specified resource is invalidated, the image/icon will be recreated the next time it will be requested. All the
 * images are guaranteed to be buffered and compatible with the current configuration. The thumbnails are not cached.
 * The ratios are kept during scale.
 *
 * @author Baptiste Wicht
 */
public interface IImageService {
    /**
     * Register a resource with the specified id.
     *
     * @param id       The id of the resource. Will be the id of the image and icon.
     * @param resource The resource.
     */
    void registerResource(String id, Resource resource);

    /**
     * Release the resource.
     *
     * @param id The id of the resource to release.
     */
    void releaseResource(String id);

    /**
     * Return the resource of the specified id.
     *
     * @param id The id of the resource to search.
     *
     * @return The resource with the specified id or null if there is no resource with this id.
     */
    Resource getResourceByID(String id);

    /**
     * Return the icon of the given id.
     *
     * @param id The id of the icon (the resource).
     *
     * @return The image icon with the given id or null if there is no resource with this id.
     */
    ImageIcon getIcon(String id);

    /**
     * Return the image with the specified id.
     *
     * @param id The id of the image (the resource).
     *
     * @return The image with the given id or null if there is no resource with this id.
     */
    BufferedImage getImage(String id);

    /**
     * Return the image with the specified id and scaled to the given width.
     *
     * @param id    The id of the image (the resource).
     * @param width The requested width.
     *
     * @return The image with the given id scaled to the specified width or null if there is no resource with this id.
     */
    BufferedImage getImage(String id, int width);

    /**
     * Return the image from the file at the given path. The file resource is also cached.
     *
     * @param path The path to the file.
     *
     * @return The image with the given id or null if there is no resource with this id.
     */
    BufferedImage getFileImage(String path);

    /**
     * Return the image from the file at the given path and scaled to the given width. The file resource is also
     * cached.
     *
     * @param path  The path to the file.
     * @param width The requested width.
     *
     * @return The image with the given id scaled to the specified width or null if there is no resource with this id.
     */
    BufferedImage getFileImage(String path, int width);

    /**
     * Invalidate the entire cache.
     */
    void invalidateCache();

    /**
     * Invalidate the cached version of the specified image.
     *
     * @param id The image id to invalidate the cache for.
     */
    void invalidateImage(String id);
}