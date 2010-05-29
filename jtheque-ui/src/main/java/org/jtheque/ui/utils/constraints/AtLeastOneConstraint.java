package org.jtheque.ui.utils.constraints;

import org.jtheque.errors.able.IError;
import org.jtheque.errors.utils.InternationalizedError;

import javax.swing.ComboBoxModel;
import javax.swing.JList;

import java.awt.ItemSelectable;
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
public final class AtLeastOneConstraint implements Constraint {
    private final String fieldName;

    /**
     * Construct a new NotNullConstraint.
     *
     * @param fieldName The field name.
     */
    public AtLeastOneConstraint(String fieldName) {
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
        int count = 0;

        if (field instanceof ItemSelectable) {
            count = ((ItemSelectable) field).getSelectedObjects().length;
        } else if (field instanceof ComboBoxModel) {
            count = ((ComboBoxModel) field).getSelectedItem() == null ? 0 : 1;
        } else if (field instanceof JList) {
            count = ((JList) field).getSelectedValues().length;
        }

        if (count <= 0) {
            errors.add(new InternationalizedError("error.validation.field.empty", fieldName));
        }
    }

    @Override
    public void configure(Object component) {
        //Nothing to configure here
    }
}