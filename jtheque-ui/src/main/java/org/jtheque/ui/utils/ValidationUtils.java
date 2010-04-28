package org.jtheque.ui.utils;

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

import org.jtheque.errors.InternationalizedError;
import org.jtheque.errors.JThequeError;
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
    public static void rejectIfEmpty(CharSequence field, String name, Collection<JThequeError> errors) {
        if (StringUtils.isEmpty(field)) {
            errors.add(new InternationalizedError("error.validation.field.empty",name));
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
    public static void rejectIfLongerThan(CharSequence field, String name, int max, Collection<JThequeError> errors) {
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
    public static void rejectIfNothingSelected(JList list, String name, Collection<JThequeError> errors) {
        if (list.getSelectedIndex() <= -1) {
            errors.add(new InternationalizedError("error.validation.nothing.selected",new Object[]{name}));
        }
    }

    /**
     * Reject the model if nothing is selected.
     *
     * @param model  The model to test.
     * @param name   The name of the field.
     * @param errors The errors list.
     */
    public static void rejectIfNothingSelected(ComboBoxModel model, String name, Collection<JThequeError> errors) {
        if (model.getSelectedItem() == null) {
            errors.add(new InternationalizedError("error.validation.nothing.selected",name));
        }
    }

    /**
     * Reject the field if not numerical.
     *
     * @param field  The field to test.
     * @param name   The name of the field.
     * @param errors The errors list.
     */
    public static void rejectIfNotNumerical(String field, String name, Collection<JThequeError> errors) {
        try {
            Integer.parseInt(field);
        } catch (NumberFormatException e) {
            errors.add(new InternationalizedError("error.validation.field.numerical",name));
        }
    }

    /**
     * Reject the list if empty.
     *
     * @param list   The list to test.
     * @param name   The name of the field.
     * @param errors The errors list.
     */
    public static void rejectIfEmpty(Collection<?> list, String name, Collection<JThequeError> errors) {
        if (list != null && list.isEmpty()) {
            errors.add(new InternationalizedError("error.validation.field.empty",name));
        }
    }

    /**
     * Reject the list if there is empty.
     *
     * @param list   The list to test.
     * @param name   The name of the list.
     * @param errors The errors to fill.
     */
    public static void rejectIfEmpty(JList list, String name, Collection<JThequeError> errors) {
        if (list != null && list.getModel().getSize() < 1) {
            errors.add(new InternationalizedError("error.validation.field.empty",name));
        }
    }
}