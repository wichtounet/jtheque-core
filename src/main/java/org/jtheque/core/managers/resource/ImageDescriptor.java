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

/**
 * An image descriptor.
 *
 * @author Baptiste Wicht
 */
public class ImageDescriptor {
    private final String image;
    private final ImageType type;

    /**
     * Construct a new ImageDescriptor.
     *
     * @param image The image path.
     * @param type The type of the image. 
     */
    public ImageDescriptor(String image, ImageType type){
        super();

        this.image = image;
        this.type = type;
    }

    /**
     * Return the path to the image.
     *
     * @return The path to the image.
     */
    public String getImage(){
        return image;
    }

    /**
     * Return the type of the image.
     *
     * @return The type of the image.
     */
    public ImageType getType(){
        return type;
    }
}