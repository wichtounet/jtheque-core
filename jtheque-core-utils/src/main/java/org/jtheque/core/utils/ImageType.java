package org.jtheque.core.utils;

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
 * The image type.
 *
 * @author Baptiste Wicht
 */
public enum ImageType {
    PNG("png"),
    JPG("jpg"),
    JPEG("jpeg"),
    GIF("gif");

    private final String extension;

    /**
     * Construct a new ImageType with a specified extension.
     *
     * @param extension The extension of the image.
     */
    ImageType(String extension) {
        this.extension = extension;
    }

    /**
     * Return the extension of the image.
     *
     * @return The extension of the image.
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Resolve the string type to the ImageType value.
     *
     * @param type The string type of the image.
     * @return The corresponding ImageType value.
     */
    public static ImageType resolve(String type) {
        if ("png".equalsIgnoreCase(type)) {
            return PNG;
        } else if ("jpg".equalsIgnoreCase(type)) {
            return JPG;
        } else if ("jpeg".equalsIgnoreCase(type)) {
            return JPEG;
        } else {
            return GIF;
        }
    }
}
