package org.jtheque.core.managers.update.versions;

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

import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.Expander;

/**
 * An expander who expand a Version to his JThequeVersion.
 *
 * @author Baptiste Wicht
 */
public final class VersionExpander implements Expander<OnlineVersion, Version> {
    @Override
    public Version expand(OnlineVersion o) {
        return o.getVersion();
    }
}