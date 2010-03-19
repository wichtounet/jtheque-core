package org.jtheque.states;

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
public interface IStateService {
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
     * If there is a problem during the creation, the error will be displayed.
     *
     * @param <T> The state type.
     * @param c   The state class.
     *
     * @return The state or null if there was a problem during the creation.
     */
    <T extends IState> T getOrCreateState(Class<T> c);

    /**
     * Create and return a state.
     * If there is a problem during the creation, the error will be displayed.
     *
     * @param <T> The state type.
     * @param c   The state class.
     *
     * @return The state.
     */
    <T extends IState> T createState(Class<T> c);
}