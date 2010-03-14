package org.jtheque.views.impl.actions.author;

import org.jtheque.core.ICore;
import org.jtheque.ui.utils.actions.AbstractBrowseAction;

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
 * Action to inform the author of a bug.
 *
 * @author Baptiste Wicht
 */
public final class AcInformOfABug extends AbstractBrowseAction {
    /**
     * Construct a new AcInformOfABug with a specific key.
     */
    public AcInformOfABug() {
        super("menu.bug");
    }

    @Override
    public String getUrl() {
        return ICore.FORUM_URL;
    }
}