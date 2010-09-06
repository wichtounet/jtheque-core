package org.jtheque.core.utils;

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
     *
     * @return The corresponding ImageType value. 
     */
    public static ImageType resolve(String type) {
        if ("png".equalsIgnoreCase(type)) {
            return PNG;
        } else if ("jpg".equalsIgnoreCase(type)) {
            return JPG;
        } else if ("jpeg".equalsIgnoreCase(type)) {
            return JPEG;
        } else if("gif".equalsIgnoreCase(type)){
            return GIF;
        }

        throw new IllegalArgumentException("The given type is not an image type. ");
    }
}
