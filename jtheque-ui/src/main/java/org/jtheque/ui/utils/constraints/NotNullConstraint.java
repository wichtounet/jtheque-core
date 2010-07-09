package org.jtheque.ui.utils.constraints;

import org.jtheque.errors.able.IError;
import org.jtheque.errors.utils.Errors;

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
    public boolean mustControlLength() {
        return false;
    }

    @Override
    public void validate(Object field, Collection<IError> errors) {
        if (field == null) {
            errors.add(Errors.newI18nError("error.validation.field.empty", new Object[]{fieldName}));
        }
    }

    @Override
    public void configure(Object component) {
        //Nothing to configure here
    }
}