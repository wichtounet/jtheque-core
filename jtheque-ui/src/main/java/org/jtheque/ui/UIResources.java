package org.jtheque.ui;

import org.jtheque.resources.IResourceService;
import org.springframework.core.io.Resource;

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

public class UIResources {
    public static final String LIGHT_IMAGE = "jtheque-ui-light";

    private UIResources() {
        super();
    }

    public static UIResources register(IResourceService resourceService, Resource lightImage) {
        resourceService.registerResource(LIGHT_IMAGE, lightImage);

        return new UIResources();
    }
}