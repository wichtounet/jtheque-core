package org.jtheque.resources;

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

import org.springframework.core.io.Resource;

import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;

/**
 * A resource manager.
 *
 * @author Baptiste Wicht
 */
public interface IResourceService {
    Resource getResourceByID(String id);
    void registerResource(String id, Resource resource);
    void releaseResource(String id);

    ImageIcon getIcon(String id);
    BufferedImage getImage(String id, int width);
    BufferedImage getImage(String id);

    void invalidateCache();
    void invalidateResource(String id);
}