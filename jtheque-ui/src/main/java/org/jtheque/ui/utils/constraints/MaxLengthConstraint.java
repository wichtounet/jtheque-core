package org.jtheque.ui.utils.constraints;

import org.jtheque.errors.JThequeError;
import org.jtheque.ui.utils.ValidationUtils;

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
 * A constraint to set a max length.
 *
 * @author Baptiste Wicht
 */
public final class MaxLengthConstraint implements Constraint {
    private final int maxLength;
    private final String fieldName;
    private final boolean canBenNull;
    private final boolean numerical;

    /**
     * Construct a new MaxLengthConstraint.
     *
     * @param maxLength  The maximum length of the field.
     * @param fieldName  The field name.
     * @param canBenNull Can the field be null ?
     * @param numerical  Must the field be numerical ?
     */
    public MaxLengthConstraint(int maxLength, String fieldName, boolean canBenNull, boolean numerical) {
        super();

        this.maxLength = maxLength;
        this.fieldName = fieldName;
        this.canBenNull = canBenNull;
        this.numerical = numerical;
    }

    @Override
    public int maxLength() {
        return maxLength;
    }

    @Override
    public boolean mustBeNumerical() {
        return numerical;
    }

    @Override
    public boolean canBeNullOrEmpty() {
        return canBenNull;
    }

    @Override
    public boolean mustControlLength() {
        return true;
    }

    @Override
    public void validate(Object field, Collection<JThequeError> errors) {
        CharSequence str = (CharSequence) field;

        if (!canBenNull) {
            ValidationUtils.rejectIfEmpty(str, fieldName, errors);
        }

        ValidationUtils.rejectIfLongerThan(str, fieldName, maxLength, errors);
    }
}