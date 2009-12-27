package org.jtheque.core.managers.patch;

import org.jtheque.core.managers.IManager;

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
 * A manager for the patch the modules, the core and the application can add to the application and which are launched
 * before the others things when the application launch.
 *
 * @author Baptiste Wicht
 */
public interface IPatchManager extends IManager {
    /**
     * Set updated.
     *
     * @param updated true if the application has been updated else false.
     */
    void setUpdated(boolean updated);

    /**
     * Register an online patch.
     *
     * @param patch The online patch to register.
     */
    void registerOnlinePatch(OnlinePatch patch);

    /**
     * Register a patch.
     *
     * @param p The patch to register.
     */
    void registerPatch(Patch p);

    /**
     * Apply the patches if needed.
     *
     * @return The patches if needed.
     */
    boolean applyPatchesIfNeeded();

    /**
     * Indicate if a patch of a certain name is applied or not.
     *
     * @param name The name of the patch.
     * @return true if the patch has been applied else false.
     */
    boolean isApplied(String name);
}