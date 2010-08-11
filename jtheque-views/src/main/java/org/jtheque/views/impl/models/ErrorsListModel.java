package org.jtheque.views.impl.models;

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

import org.jtheque.errors.*;
import org.jtheque.errors.Error;
import org.jtheque.errors.ErrorListener;
import org.jtheque.ui.utils.models.SimpleListModel;

import java.util.Collection;

/**
 * A List model to display the modules.
 *
 * @author Baptiste Wicht
 */
public final class ErrorsListModel extends SimpleListModel<org.jtheque.errors.Error> implements ErrorListener {
    private static final long serialVersionUID = 3355362999481373712L;

    /**
     * Construct a new ErrorsListModel.
     *
     * @param errorService The error service.
     */
    public ErrorsListModel(ErrorService errorService) {
        super();

        errorService.addErrorListener(this);

        Collection<Error> errorsList = errorService.getErrors();

        for (Error error : errorsList) {
            addElement(error);
        }
    }

    @Override
    public void errorOccurred(Error error) {
        addElement(error);

        fireContentsChanged(this, getSize() - 2, getSize());
    }
}