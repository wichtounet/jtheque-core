package org.jtheque.core.managers.error;

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

import java.util.Collection;

/**
 * An error manager. It seems a manager who enable module to display some errors in a simple way.
 *
 * @author Baptiste Wicht
 */
public interface IErrorManager {
    /**
     * Add an error.
     *
     * @param error A new error.
     */
    void addError(JThequeError error);

    /**
     * Return all the errors.
     *
     * @return A List containing all the errors.
     */
    Collection<JThequeError> getErrors();

    /**
     * Add a startup error.
     *
     * @param error An error who occurred at the startup of the application.
     */
    void addStartupError(JThequeError error);

    /**
     * Display the errors.
     */
    void displayErrors();

    /**
     * Add an internationalizable error.
     *
     * @param messageKey the internationalized key.
     */
    void addInternationalizedError(String messageKey);

    /**
     * Add an internationalizable error.
     *
     * @param messageKey      the internationalized key.
     * @param messageReplaces the object to use in replaces.
     */
    void addInternationalizedError(String messageKey, Object... messageReplaces);
}