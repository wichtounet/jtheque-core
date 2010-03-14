package org.jtheque.resources;

import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.lang.ref.SoftReference;

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
 * An image container. This image container stock the image as a SoftReference.
 *
 * @author Baptiste Wicht
 * 
 * @see SoftReference
 */
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
