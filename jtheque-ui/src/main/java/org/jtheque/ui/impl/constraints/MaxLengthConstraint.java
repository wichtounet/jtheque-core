package org.jtheque.ui.impl.constraints;

import org.jtheque.ui.constraints.Constraint;
import org.jtheque.ui.utils.ValidationUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import java.awt.Component;
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
    private final boolean canBeNull;
    private final boolean numerical;

    /**
     * Construct a new MaxLengthConstraint.
     *
     * @param maxLength The maximum length of the field.
     * @param fieldName The field name.
     * @param canBeNull Can the field be null ?
     * @param numerical Must the field be numerical ?
     */
    public MaxLengthConstraint(int maxLength, String fieldName, boolean canBeNull, boolean numerical) {
        super();

        this.maxLength = maxLength;
        this.fieldName = fieldName;
        this.canBeNull = canBeNull;
        this.numerical = numerical;
    }

    @Override
    public int getMaxLength() {
        return maxLength;
    }

    @Override
    public boolean isLengthControlled() {
        return true;
    }

    @Override
    public void configure(Object component) {
        if (component instanceof JTextField && maxLength > 0) {
            SwingUtils.addFieldLengthLimit((JTextField) component, maxLength);
        }
    }

    @Override
    public void validate(Object field, Collection<org.jtheque.errors.Error> errors) {
        if (field instanceof Component && !((Component) field).isEnabled()) {
            return;
        }

        String str = null;

        if (field instanceof JTextComponent) {
            str = ((JTextComponent) field).getText();
        } else if (field instanceof String) {
            str = (String) field;
        }

        if (!canBeNull) {
            ValidationUtils.rejectIfEmpty(str, fieldName, errors);
        }

        if(numerical){
            ValidationUtils.rejectIfNotNumerical(str, fieldName, errors);
        }

        ValidationUtils.rejectIfLongerThan(str, fieldName, maxLength, errors);
    }
}