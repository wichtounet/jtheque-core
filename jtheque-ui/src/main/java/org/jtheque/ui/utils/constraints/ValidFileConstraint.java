package org.jtheque.ui.utils.constraints;

import org.jtheque.errors.able.IError;
import org.jtheque.errors.utils.InternationalizedError;
import org.jtheque.ui.utils.ValidationUtils;
import org.jtheque.ui.utils.components.FileChooserPanel;

import java.io.File;
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
public final class ValidFileConstraint implements Constraint {
    private final String fieldName;
	private final int maxLength;

    /**
     * Construct a new NotNullConstraint.
     *
     * @param fieldName The field name.
     */
    public ValidFileConstraint(String fieldName) {
        this(fieldName, -1);
    }
    /**
     * Construct a new NotNullConstraint.
     *
     * @param fieldName The field name.
     */
    public ValidFileConstraint(String fieldName, int maxLength) {
        super();

        this.fieldName = fieldName;
	    this.maxLength = maxLength;
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
	    if(field instanceof FileChooserPanel){
		    String filePath = ((FileChooserPanel) field).getFilePath();

		    if(maxLength > 0){
			    ValidationUtils.rejectIfLongerThan(filePath, fieldName, maxLength, errors);
		    }

		    if(errors.isEmpty() && !new File(filePath).exists()){
			    errors.add(new InternationalizedError("error.validation.field.file", fieldName));
		    }
	    }
    }

	@Override
	public void configure(Object component) {
		//Nothing to do here
	}
}