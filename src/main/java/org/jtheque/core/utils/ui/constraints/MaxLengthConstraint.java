package org.jtheque.core.utils.ui.constraints;

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.utils.ui.ValidationUtils;

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