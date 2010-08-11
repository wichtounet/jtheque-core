package org.jtheque.ui.impl.constraints;

import org.jtheque.errors.Errors;
import org.jtheque.errors.Error;

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
 * A constraint to indicate that the field cannot be null.
 *
 * @author Baptiste Wicht
 */
public final class NotNullConstraint extends NonLengthConstraint {
    /**
     * Construct a new NotNullConstraint.
     *
     * @param fieldName The field name.
     */
    public NotNullConstraint(String fieldName) {
        super(fieldName);
    }

    @Override
    public void validate(Object field, Collection<Error> errors) {
        if (field == null) {
            errors.add(Errors.newI18nError("error.validation.field.empty", new Object[]{getFieldName()}));
        }
    }
}