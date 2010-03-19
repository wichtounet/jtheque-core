package org.jtheque.ui.utils.constraints;

import org.jtheque.errors.InternationalizedError;
import org.jtheque.errors.JThequeError;
import org.jtheque.i18n.ILanguageService;
import org.jtheque.ui.ViewsUtilsServices;

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
 * A constraint to indicate that the field cannot be null.
 *
 * @author Baptiste Wicht
 */
public final class NotNullConstraint implements Constraint {
    private final String fieldName;

    /**
     * Construct a new NotNullConstraint.
     *
     * @param fieldName The field name.
     */
    public NotNullConstraint(String fieldName) {
        super();

        this.fieldName = fieldName;
    }

    @Override
    public int maxLength() {
        return -1;
    }

    @Override
    public boolean mustBeNumerical() {
        return false;
    }

    @Override
    public boolean canBeNullOrEmpty() {
        return false;
    }

    @Override
    public boolean mustControlLength() {
        return false;
    }

    @Override
    public void validate(Object field, Collection<JThequeError> errors) {
        if (field == null) {
            errors.add(new InternationalizedError(
                    ViewsUtilsServices.get(ILanguageService.class),
                    "error.validation.field.empty",
                    new Object[]{ViewsUtilsServices.get(ILanguageService.class).getMessage(fieldName)}));
        }
    }
}