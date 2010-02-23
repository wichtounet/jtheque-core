package org.jtheque.core.managers.state;

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

import org.jtheque.core.utils.file.nodes.Node;

import java.util.Collection;

/**
 * A state. It's a persistent object manager by the state manager. This object permit to store certain property to
 * recover it after the application has been exited.
 *
 * @author Baptiste Wicht
 */
public interface IState {
    /**
     * Return the property referenced by a certain key.
     *
     * @param key The property key.
     * @return The property.
     */
    String getProperty(String key);

    /**
     * Return the property referenced by a certain key or a default value if the property doesn't exist.
     *
     * @param key      The property key.
     * @param defaults The default value if we don't find the property.
     * @return The property or the default value if the property doesn't exist.
     */
    String getProperty(String key, String defaults);

    /**
     * Set the property.
     *
     * @param key   The property key.
     * @param value The property value.
     */
    void setProperty(String key, String value);

    /**
     * Return all the properties of the state.
     *
     * @return A List containing all the properties of the state.
     */
    Collection<String> getProperties();

    /**
     * Indicate if the write and read operations are delegated to the state or are standard managed by the
     * state manager.
     *
     * @return true if the operations are delegated else false.
     */
    boolean isDelegated();

    /**
     * Delegate the save.
     *
     * @return A List of Node to write.
     */
    Collection<Node> delegateSave();

    /**
     * Delegate the load.
     *
     * @param nodes The nodes of the state.
     */
    void delegateLoad(Collection<Node> nodes);
}