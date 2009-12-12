package org.jtheque.core.managers.view.impl.actions.author;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.impl.actions.utils.AbstractBrowseAction;

/**
 * Action to make a donation to the author.
 *
 * @author Baptiste Wicht
 */
public final class AcMakeDonation extends AbstractBrowseAction {
    private static final long serialVersionUID = -163510361075558672L;

    /**
     * Construct a new AcMakeDonation with a specific key.
     */
    public AcMakeDonation() {
        super("menu.donation");
    }

    @Override
    public String getUrl() {
        return Managers.getCore().getFiles().getMakeDonationURL();
    }
}