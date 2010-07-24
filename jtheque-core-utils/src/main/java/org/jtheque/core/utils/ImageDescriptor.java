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
 * An image descriptor.
 *
 * @author Baptiste Wicht
 */
public final class ImageDescriptor {
    private final String image;
    private final ImageType type;

    /**
     * Construct a new ImageDescriptor.
     *
     * @param image The image path.
     * @param type  The type of the image.
     */
    public ImageDescriptor(String image, ImageType type) {
        super();

        this.image = image;
        this.type = type;
    }

    /**
     * Return the path to the image.
     *
     * @return The path to the image.
     */
    public String getImage() {
        return image;
    }

    /**
     * Return the type of the image.
     *
     * @return The type of the image.
     */
    public ImageType getType() {
        return type;
    }

    /**
     * Compute the complete path of the descriptor.
     * 
     * @return The complete path to the image.
     */
    public String toPath() {
        return image + '.' + type.getExtension();
    }
}