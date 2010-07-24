package org.jtheque.ui.able.constraints;

import org.jtheque.ui.impl.constraints.AtLeastOneConstraint;
import org.jtheque.ui.impl.constraints.MaxLengthConstraint;
import org.jtheque.ui.impl.constraints.NotNullConstraint;
import org.jtheque.ui.impl.constraints.ValidFileConstraint;

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
 * Simple factory class to create Constraint.
 *
 * @author Baptiste Wicht
 */
public final class Constraints {
    /**
     * Utility class, not instantiable.
     */
    private Constraints() {
        throw new AssertionError();
    }

    /**
     * Construct a new Constraint to validate that at least one object is in the given field.
     *
     * @param fieldName The field name.
     *
     * @return The created constraint.
     */
    public static Constraint atLeastOne(String fieldName) {
        return new AtLeastOneConstraint(fieldName);
    }

    /**
     * Construct a new Constraint that validate the field is not null.
     *
     * @param fieldName The field name.
     *
     * @return The created constraint.
     */
    public static Constraint notNull(String fieldName) {
        return new NotNullConstraint(fieldName);
    }

    /**
     * Construct a new Constraint that validate that the file exists.
     *
     * @param fieldName The field name.
     *
     * @return The created constraint.
     */
    public static Constraint validFile(String fieldName) {
        return validFile(fieldName, -1);
    }

    /**
     * Construct a new Constraint that validate that the file exists and that the file path is not longer than the given
     * max length.
     *
     * @param fieldName The field name.
     * @param maxLength The max length of the field.
     *
     * @return The created constraint.
     */
    public static Constraint validFile(String fieldName, int maxLength) {
        return new ValidFileConstraint(fieldName, maxLength);
    }

    /**
     * Construct a new MaxLengthConstraint.
     *
     * @param maxLength The maximum length of the field.
     * @param fieldName The field name.
     * @param canBeNull Can the field be null ?
     * @param numerical Must the field be numerical ?
     *
     * @return The created constraint.
     */
    public static Constraint max(int maxLength, String fieldName, boolean canBeNull, boolean numerical) {
        return new MaxLengthConstraint(maxLength, fieldName, canBeNull, numerical);
    }
}