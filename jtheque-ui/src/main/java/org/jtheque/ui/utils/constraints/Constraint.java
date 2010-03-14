package org.jtheque.ui.utils.constraints;

import org.jtheque.errors.JThequeError;

import java.util.Collection;

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
 * A constraint on an entity property.
 *
 * @author Baptiste Wicht
 */
public interface Constraint {
    /**
     * Return the max length of the value.
     *
     * @return The max length of the value.
     */
    int maxLength();

    /**
     * Indicate if the value must be numerical or not.
     *
     * @return true if teh value must be numerical else false.
     */
    boolean mustBeNumerical();

    /**
     * Indicate if the value can be null or not.
     *
     * @return true if the value can be null else false.
     */
    boolean canBeNullOrEmpty();

    /**
     * Indicate if we must control length or not.
     *
     * @return true if we must control length else false.
     */
    boolean mustControlLength();

    /**
     * Validate the field.
     *
     * @param field  The field to validate.
     * @param errors The errors list.
     */
    void validate(Object field, Collection<JThequeError> errors);
}