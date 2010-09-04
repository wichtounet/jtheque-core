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

import org.jtheque.errors.Error;
import org.jtheque.errors.Errors;
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
        throw new AssertionError();
    }

    /**
     * Reject the field if empty.
     *
     * @param field  The field to test.
     * @param name   The name of the field.
     * @param errors The errors list.
     */
    public static void rejectIfEmpty(CharSequence field, String name, Collection<Error> errors) {
        if (StringUtils.isEmpty(field)) {
            errors.add(Errors.newI18nError("error.validation.field.empty", new Object[]{name}));
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
    public static void rejectIfLongerThan(CharSequence field, String name, int max, Collection<Error> errors) {
        if (!StringUtils.isEmpty(field) && field.length() > max) {
            errors.add(Errors.newI18nError("error.validation.field.length", new Object[]{name, max}));
        }
    }

    /**
     * Reject the list if nothing is selected.
     *
     * @param list   The list to test.
     * @param name   The name of the field.
     * @param errors The errors list.
     */
    public static void rejectIfNothingSelected(JList list, String name, Collection<Error> errors) {
        if (list.getSelectedIndex() <= -1) {
            errors.add(Errors.newI18nError("error.validation.nothing.selected", new Object[]{name}));
        }
    }

    /**
     * Reject the model if nothing is selected.
     *
     * @param model  The model to test.
     * @param name   The name of the field.
     * @param errors The errors list.
     */
    public static void rejectIfNothingSelected(ComboBoxModel model, String name, Collection<org.jtheque.errors.Error> errors) {
        if (model.getSelectedItem() == null) {
            errors.add(Errors.newI18nError("error.validation.nothing.selected", new Object[]{name}));
        }
    }

    /**
     * Reject the field if not numerical.
     *
     * @param field  The field to test.
     * @param name   The name of the field.
     * @param errors The errors list.
     */
    public static void rejectIfNotNumerical(String field, String name, Collection<Error> errors) {
        try {
            Integer.parseInt(field);
        } catch (NumberFormatException e) {
            errors.add(Errors.newI18nError("error.validation.field.numerical", new Object[]{name}));
        }
    }

    /**
     * Reject the list if empty.
     *
     * @param list   The list to test.
     * @param name   The name of the field.
     * @param errors The errors list.
     */
    public static void rejectIfEmpty(Collection<?> list, String name, Collection<Error> errors) {
        if (list != null && list.isEmpty()) {
            errors.add(Errors.newI18nError("error.validation.field.empty", new Object[]{name}));
        }
    }

    /**
     * Reject the list if there is empty.
     *
     * @param list   The list to test.
     * @param name   The name of the list.
     * @param errors The errors to fill.
     */
    public static void rejectIfEmpty(JList list, String name, Collection<Error> errors) {
        if (list != null && list.getModel().getSize() < 1) {
            errors.add(Errors.newI18nError("error.validation.field.empty", new Object[]{name}));
        }
    }
}