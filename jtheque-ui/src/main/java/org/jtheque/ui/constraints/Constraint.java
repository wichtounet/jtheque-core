package org.jtheque.ui.constraints;

import java.util.Collection;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * A constraint on an entity property.
 *
 * @author Baptiste Wicht
 */
public interface Constraint {
    /**
     * Return the max length of the value. If the constraint doesn't need to control length, this method
     * return a number with non-sense. 
     *
     * @return The max length of the value.
     */
    int getMaxLength();

    /**
     * Indicate if we must control length or not.
     *
     * @return true if we must control length else false.
     */
    boolean isLengthControlled();

    /**
     * Validate the field.
     *
     * @param field  The field to validate.
     * @param errors The errors list.
     */
    void validate(Object field, Collection<org.jtheque.errors.Error> errors);

    /**
     * Configure the given component. The implementation must not assume the type of component passed as argument.
     *
     * @param component The component to configure.
     */
    void configure(Object component);
}