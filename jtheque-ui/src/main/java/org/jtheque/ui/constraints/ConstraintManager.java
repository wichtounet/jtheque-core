package org.jtheque.ui.constraints;

import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.DocumentLengthFilterAvert;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

import java.util.Collection;
import java.util.Map;

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
 * A constraint manager.
 *
 * @author Baptiste Wicht
 */
public final class ConstraintManager {
    private static final Map<String, Constraint> CONSTRAINTS = CollectionUtils.newHashMap(20);

    /**
     * Construct a new ConstraintManager. This class isn't instanciable.
     */
    private ConstraintManager() {
        super();
    }

    /**
     * Add a constraint.
     *
     * @param fieldName  The name of the field.
     * @param constraint The constraint.
     */
    public static void addConstraint(String fieldName, Constraint constraint) {
        CONSTRAINTS.put(fieldName, constraint);
    }

    /**
     * Validate the field with the constraint.
     *
     * @param fieldName The name of the field.
     * @param field     The field.
     * @param errors    The errors list to fill.
     */
    public static void validate(String fieldName, Object field, Collection<org.jtheque.errors.Error> errors) {
        if (CONSTRAINTS.containsKey(fieldName)) {
            CONSTRAINTS.get(fieldName).validate(field, errors);
        }
    }

    /**
     * Configure a JTextField with the constraint.
     *
     * @param field     The field to configure.
     * @param fieldName The name of the field.
     */
    public static void configure(JTextField field, String fieldName) {
        if (CONSTRAINTS.containsKey(fieldName) && CONSTRAINTS.get(fieldName).mustControlLength()) {
            DocumentFilter filter = new DocumentLengthFilterAvert(CONSTRAINTS.get(fieldName).maxLength(), field);
            Document document = field.getDocument();
            ((AbstractDocument) document).setDocumentFilter(filter);
        }
    }
}