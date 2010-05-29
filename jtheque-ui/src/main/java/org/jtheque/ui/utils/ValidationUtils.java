package org.jtheque.ui.utils;

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

import org.jtheque.errors.able.IError;
import org.jtheque.errors.utils.InternationalizedError;
import org.jtheque.utils.StringUtils;

import javax.swing.ComboBoxModel;
import javax.swing.JList;

import java.util.Collection;

/**
 * Validation utils.
 *
 * @author Baptiste Wicht
 */
public final class ValidationUtils {
    /**
     * Create a new ValidationUtils.
     */
    private ValidationUtils() {
        super();
    }

    /**
     * Reject the field if empty.
     *
     * @param field  The field to test.
     * @param name   The name of the field.
     * @param errors The errors list.
     */
    public static void rejectIfEmpty(CharSequence field, String name, Collection<IError> errors) {
        if (StringUtils.isEmpty(field)) {
            errors.add(new InternationalizedError("error.validation.field.empty", name));
        }
    }

    /**
     * Reject the field if longer than a max.
     *
     * @param field  The field to test.
     * @param name   The name of the field.
     * @param max    The max length of the field.
     * @param errors The errors list.
     */
    public static void rejectIfLongerThan(CharSequence field, String name, int max, Collection<IError> errors) {
        if (!StringUtils.isEmpty(field) && field.length() > max) {
            errors.add(new InternationalizedError("error.validation.field.lenght", name, max));
        }
    }

    /**
     * Reject the list if nothing is selected.
     *
     * @param list   The list to test.
     * @param name   The name of the field.
     * @param errors The errors list.
     */
    public static void rejectIfNothingSelected(JList list, String name, Collection<IError> errors) {
        if (list.getSelectedIndex() <= -1) {
            errors.add(new InternationalizedError("error.validation.nothing.selected", new Object[]{name}));
        }
    }

    /**
     * Reject the model if nothing is selected.
     *
     * @param model  The model to test.
     * @param name   The name of the field.
     * @param errors The errors list.
     */
    public static void rejectIfNothingSelected(ComboBoxModel model, String name, Collection<IError> errors) {
        if (model.getSelectedItem() == null) {
            errors.add(new InternationalizedError("error.validation.nothing.selected", name));
        }
    }

    /**
     * Reject the field if not numerical.
     *
     * @param field  The field to test.
     * @param name   The name of the field.
     * @param errors The errors list.
     */
    public static void rejectIfNotNumerical(String field, String name, Collection<IError> errors) {
        try {
            Integer.parseInt(field);
        } catch (NumberFormatException e) {
            errors.add(new InternationalizedError("error.validation.field.numerical", name));
        }
    }

    /**
     * Reject the list if empty.
     *
     * @param list   The list to test.
     * @param name   The name of the field.
     * @param errors The errors list.
     */
    public static void rejectIfEmpty(Collection<?> list, String name, Collection<IError> errors) {
        if (list != null && list.isEmpty()) {
            errors.add(new InternationalizedError("error.validation.field.empty", name));
        }
    }

    /**
     * Reject the list if there is empty.
     *
     * @param list   The list to test.
     * @param name   The name of the list.
     * @param errors The errors to fill.
     */
    public static void rejectIfEmpty(JList list, String name, Collection<IError> errors) {
        if (list != null && list.getModel().getSize() < 1) {
            errors.add(new InternationalizedError("error.validation.field.empty", name));
        }
    }
}