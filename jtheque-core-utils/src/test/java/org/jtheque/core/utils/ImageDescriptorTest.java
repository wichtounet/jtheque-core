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

import org.junit.Test;

import static org.junit.Assert.*;

public class ImageDescriptorTest {
    @Test
    public void constructors(){
        ImageDescriptor image = new ImageDescriptor("testimage", ImageType.PNG);

        assertEquals("testimage", image.getImage());
        assertEquals(ImageType.PNG, image.getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyImage() {
        new ImageDescriptor("", ImageType.PNG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullType() {
        new ImageDescriptor("asdf", null);
    }

    @Test
    public void toPath(){
        ImageDescriptor image = new ImageDescriptor("testimage", ImageType.PNG);

        assertEquals("testimage.png", image.toPath());
    }
}
