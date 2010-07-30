package org.jtheque.images.impl;

import org.jtheque.utils.annotations.ThreadSafe;

import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;
import java.lang.ref.SoftReference;

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
 * An image container. This image container stock the image as a SoftReference.
 *
 * @author Baptiste Wicht
 * @see SoftReference
 */
@ThreadSafe
final class JThequeImage {
    private final SoftReference<BufferedImage> image;

    /**
     * Construct a new JTheque Image.
     *
     * @param image The buffered image.
     */
    JThequeImage(BufferedImage image) {
        super();

        this.image = new SoftReference<BufferedImage>(image);
    }

    /**
     * Return the JThequeImage as an ImageIcon.
     *
     * @return The ImageIcon value of the image.
     */
    public ImageIcon asIcon() {
        return get() == null ? null : new ImageIcon(get());
    }

    /**
     * Return the buffered image.
     *
     * @return The buffered image.
     */
    public BufferedImage get() {
        return image == null ? null : image.get();
    }
}
