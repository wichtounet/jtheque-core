package org.jtheque.core.utils;

import org.junit.Test;

import static org.junit.Assert.*;

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

public class ImageTypeTest {
    @Test
    public void verifyExtensions(){
        assertEquals("gif", ImageType.GIF.getExtension());
        assertEquals("png", ImageType.PNG.getExtension());
        assertEquals("jpeg", ImageType.JPEG.getExtension());
        assertEquals("jpg", ImageType.JPG.getExtension());
    }

    @Test
    public void resolve() {
        assertEquals(ImageType.GIF, ImageType.resolve("gif"));
        assertEquals(ImageType.PNG, ImageType.resolve("png"));
        assertEquals(ImageType.JPEG, ImageType.resolve("jpeg"));
        assertEquals(ImageType.JPG, ImageType.resolve("jpg"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidType() {
        assertEquals(ImageType.JPG, ImageType.resolve("adsfadsf"));
    }
}