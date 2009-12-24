package org.jtheque.core.utils.ui.constraints;

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.utils.ui.DocumentLengthFilterAvert;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
 * A constraint manager.
 *
 * @author Baptiste Wicht
 */
public final class ConstraintManager {
    private static final Map<String, Constraint> CONSTRAINTS = new HashMap<String, Constraint>(20);

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
     * Return the constraint for a specific field.
     *
     * @param fieldName The name of the field to get the constraint for.
     * @return The constraint for the field.
     */
    public static Constraint getConstraint(String fieldName) {
        return CONSTRAINTS.get(fieldName);
    }

    /**
     * Validate the field with the constraint.
     *
     * @param fieldName The name of the field.
     * @param field     The field.
     * @param errors    The errors list to fill.
     */
    public static void validate(String fieldName, Object field, Collection<JThequeError> errors) {
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