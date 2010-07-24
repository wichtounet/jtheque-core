package org.jtheque.ui.impl.constraints;

import org.jtheque.ui.able.constraints.Constraint;

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

public abstract class NonLengthConstraint implements Constraint {
    private final String fieldName;

    /**
     * Construct a new NonLengthConstraint.
     *
     * @param fieldName The field name.
     */
    public NonLengthConstraint(String fieldName) {
        super();

        this.fieldName = fieldName;
    }

    protected String getFieldName() {
        return fieldName;
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
    public void configure(Object component) {
        //Nothing to configure here
    }
}
