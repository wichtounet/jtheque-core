package org.jtheque.resources;

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

import org.jtheque.utils.ui.ImageUtils;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * A resource manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class ResourceService implements IResourceService {
    private static final int DEFAULT_CACHE_SIZE = 50;

    private final Map<String, Resource> resources = new HashMap<String, Resource>(DEFAULT_CACHE_SIZE);
    private final Map<String, JThequeImage> cache = new HashMap<String, JThequeImage>(DEFAULT_CACHE_SIZE);

    @Override
    public ImageIcon getIcon(String id) {
        if (isImageNotCached(id) && resources.containsKey(id)) {
            loadImageInCache(id);
        }

        return cache.get(id) == null ? null : cache.get(id).asIcon();
    }

    @Override
    public BufferedImage getImage(String id, int width) {
        return getThumbnail(getImage(id), width);
    }

    @Override
    public BufferedImage getImage(String id) {
        if (isImageNotCached(id) && resources.containsKey(id)) {
            loadImageInCache(id);
        }

        return cache.get(id) == null ? null : cache.get(id).get();
    }

    public InputStream getResourceAsStream(String id) {
        InputStream stream = null;

        try {
            stream = resources.get(id).getInputStream();
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).error("Unable to load stream for {}", id);
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }

        return stream;
    }

    @Override
    public void invalidateCache() {
        cache.clear();
    }

    @Override
    public Resource getResourceByID(String id) {
        return resources.get(id);
    }

    @Override
    public void registerResource(String id, Resource resource) {
        resources.put(id, resource);
    }

    @Override
    public void releaseResource(String id) {
        resources.remove(id);
        cache.remove(id);
    }

    @Override
    public void invalidateResource(String id) {
        cache.remove(id);
    }

    /* Private utility methods */

    /**
     * Indicate if an image is not cached.
     *
     * @param index The index of the image.
     * @return true if the image is not cached else false.
     */
    private boolean isImageNotCached(String index) {
        return cache.get(index) == null;
    }

    /**
     * Load the image and put it in cache.
     *
     * @param path The path to the image.
     */
    private void loadImageInCache(String path) {
        BufferedImage image = getCompatibleImage(path);

        cache.put(path, new JThequeImage(image));
    }

    /**
     * Read the image and make it compatible with the environment.
     *
     * @param path The path to the image.
     * @return The compatible image.
     */
    private BufferedImage getCompatibleImage(String path) {
        return ImageUtils.openCompatibleImage(getResourceAsStream(path));
    }

    /**
     * Return a thumbnail of the image.
     *
     * @param image The image to make the thumbnail from.
     * @param width The width of the thumbnail.
     * @return The thumbnail of the image, with the specified width.
     */
    private static BufferedImage getThumbnail(BufferedImage image, int width) {
        return image == null ? null : ImageUtils.createThumbnail(image, width);
    }
}