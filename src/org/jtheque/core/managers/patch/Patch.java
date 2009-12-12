package org.jtheque.core.managers.patch;

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

/**
 * A patch.
 *
 * @author Baptiste Wicht
 */
public interface Patch {
    /**
     * Indicate if the patch must be applied for a specific version of JTheque.
     *
     * @param version The version of JTheque.
     * @return true if the patch must be applied for this version else false.
     */
    boolean mustBeAppliedFor(Version version);

    /**
     * Indicate if the patch has already been applied or not.
     *
     * @return true if the patch has already been applied.
     */
    boolean hasAlreadyBeenApplied();

    /**
     * Apply the patch.
     *
     * @return The apply code.
     */
    int apply();

    /**
     * Return the name of the patch. .
     *
     * @return The name of the patch.
     */
    String getName();
}