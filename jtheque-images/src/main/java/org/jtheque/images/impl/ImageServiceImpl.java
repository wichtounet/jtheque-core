package org.jtheque.images.impl;

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

import org.jtheque.images.able.ImageService;
import org.jtheque.utils.annotations.GuardedInternally;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.ImageCache;
import org.jtheque.utils.io.OnDemandStream;
import org.jtheque.utils.ui.ImageUtils;

import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * A resource manager implementation.
 *
 * @author Baptiste Wicht
 *
 * @see org.jtheque.utils.io.ImageCache
 */
@ThreadSafe
public final class ImageServiceImpl implements ImageService {
    private static final int DEFAULT_CACHE_SIZE = 50;

    @GuardedInternally
    private final Map<String, Resource> resources = CollectionUtils.newConcurrentMap(DEFAULT_CACHE_SIZE);

    @GuardedInternally
    private final ImageCache imageCache = new ImageCache(DEFAULT_CACHE_SIZE);

    @Override
    public BufferedImage getImageFromFile(String path, int width) {
        return ImageUtils.createThumbnail(getImageFromFile(path), width);
    }

    @Override
    public BufferedImage getImageFromFile(String path) {
        return imageCache.getFromFile(path);
    }

    @Override
    public ImageIcon getIcon(String id) {
        return imageCache.getIcon(id, new OnDemandResourceInputStream(id));
    }

    @Override
    public BufferedImage getImage(String id, int width) {
        return ImageUtils.createThumbnail(getImage(id), width);
    }

    @Override
    public BufferedImage getImage(String id) {
        return imageCache.get(id, new OnDemandResourceInputStream(id));
    }

    @Override
    public Resource getResource(String id) {
        return resources.get(id);
    }

    @Override
    public void registerResource(String id, Resource resource) {
        resources.put(id, resource);
    }

    @Override
    public void invalidateImage(String id) {
        imageCache.invalidate(id);
    }

    @Override
    public void releaseResource(String id) {
        resources.remove(id);
        imageCache.invalidate(id);
    }

    /**
     * A simple on demand stream that load a stream from a resource id.
     *
     * @author Baptiste Wicht
     */
    private final class OnDemandResourceInputStream implements OnDemandStream {
        private final String id;

        /**
         * Construct a new OnDemandResourceInputStream for the given resource id.
         *
         * @param id The id of the resource. 
         */
        private OnDemandResourceInputStream(String id) {
            super();

            this.id = id;
        }

        @Override
        public InputStream get() {
            InputStream stream = null;

            try {
                stream = resources.get(id).getInputStream();
            } catch (IOException e) {
                LoggerFactory.getLogger(getClass()).error("Unable to load stream for {}", id);
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }

            return stream;
        }
    }
}