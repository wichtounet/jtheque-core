package org.jtheque.core.managers.state;

import org.jtheque.core.managers.ManagerException;

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
 * A state manager. It seems a manager who's responsible for the persistence of the states.
 *
 * @author Baptiste Wicht
 */
public interface IStateManager {
    /**
     * Register a new state.
     *
     * @param state The state to register.
     */
    void registerState(IState state);

    /**
     * Return the state of a certain class.
     *
     * @param <T> The state type.
     * @param c   The state class.
     * @return The state.
     */
    <T extends IState> T getState(Class<T> c);

    /**
     * Return the state of a certain class. If the state has not been yet created, it would be created.
     *
     * @param <T> The state type.
     * @param c   The state class.
     * @return The state.
     * @throws StateException If an error occurs during the state getting.
     */
    <T extends IState> T getOrCreateState(Class<T> c) throws StateException;

    /**
     * Create and return a state.
     *
     * @param <T> The state type.
     * @param c   The state class.
     * @return The state.
     * @throws StateException If an error occurs during the state creation.
     */
    <T extends IState> T createState(Class<T> c) throws StateException;

    /**
     * Load the states.
     *
     * @throws ManagerException If an error occurs during the state loading.
     */
    void loadStates() throws ManagerException;
}