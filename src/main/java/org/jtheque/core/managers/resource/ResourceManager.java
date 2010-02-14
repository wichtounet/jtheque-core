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

import org.jtheque.core.managers.AbstractManager;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.beans.ioc.Ioc;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.utils.ui.ImageUtils;
import org.springframework.core.io.Resource;

import javax.swing.Action;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * A resource manager implementation.
 * <p/>
 * WARNING : BeansManager must be inited before this manager for his good working.
 *
 * @author Baptiste Wicht
 */
public final class ResourceManager extends AbstractManager implements IResourceManager {
    private static final int DEFAULT_CACHE_SIZE = 50;

    private final Map<String, JThequeImage> cache = new HashMap<String, JThequeImage>(DEFAULT_CACHE_SIZE);

    @Override
    public ImageIcon getIcon(String baseName, String id, ImageType type) {
        return getIcon(baseName + '/' + id + '.' + type.getExtension());
    }

    @Override
    public ImageIcon getIcon(String id, ImageType type) {
        return getIcon(id + '.' + type.getExtension());
    }

    @Override
    public ImageIcon getIcon(String path) {
        if (isImageNotCached(path)) {
            loadImageInCache(path);
        }

        return cache.get(path) == null ? null : cache.get(path).asIcon();
    }

    @Override
    public BufferedImage getImage(String baseName, String id, ImageType type, int width) {
        return getThumbnail(getImage(baseName, id, type), width);
    }

    @Override
    public BufferedImage getImage(String baseName, String id, ImageType type) {
        return getImage(baseName + '/' + id + '.' + type.getExtension());
    }

    @Override
    public BufferedImage getImage(String id, ImageType type, int width) {
        return getThumbnail(getImage(id, type), width);
    }

    @Override
    public BufferedImage getImage(String id, ImageType type) {
        return getImage(id + '.' + type.getExtension());
    }

    @Override
    public BufferedImage getImage(String id, int width) {
        return getThumbnail(getImage(id), width);
    }

    @Override
    public BufferedImage getImage(String path) {
        if (isImageNotCached(path)) {
            loadImageInCache(path);
        }

        return cache.get(path) == null ? null : cache.get(path).get();
    }

    @Override
    public Color getColor(String name) {
        return Managers.getManager(IBeansManager.class).getBean(name);
    }

    @Override
    public Action getAction(String name) {
        return Managers.getManager(IBeansManager.class).getBean(name);
    }

    @Override
    public Resource getResource(String path) {
        return Ioc.getContainer().getApplicationContext().getResource(path);
    }

    @Override
    public InputStream getResourceAsStream(String path) {
        InputStream stream = null;

        try {
            stream = getResource(path).getInputStream();
        } catch (IOException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error("Unable to load stream for {}", path);
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        }

        return stream;
    }

    @Override
    public void invalidateCache() {
        cache.clear();
    }

    @Override
    public void invalidateImage(String id) {
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