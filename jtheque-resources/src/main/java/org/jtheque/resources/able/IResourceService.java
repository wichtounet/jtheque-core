package org.jtheque.resources.able;

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

import org.springframework.core.io.Resource;

import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;

/**
 * A resource manager.
 *
 * @author Baptiste Wicht
 */
public interface IResourceService {
    void registerResource(String id, Resource resource);
    void releaseResource(String id);
    
    Resource getResourceByID(String id);

    ImageIcon getIcon(String id);
    BufferedImage getImage(String id, int width);
    BufferedImage getImage(String id);

    BufferedImage getFileImage(String id, int width);
    BufferedImage getFileImage(String id);

    void invalidateCache();
    void invalidateImage(String id);
}