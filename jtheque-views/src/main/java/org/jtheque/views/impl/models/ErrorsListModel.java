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

import org.jtheque.errors.able.ErrorListener;
import org.jtheque.errors.able.IError;
import org.jtheque.errors.able.IErrorService;
import org.jtheque.ui.utils.models.SimpleListModel;

import java.util.List;

/**
 * A List model to display the modules.
 *
 * @author Baptiste Wicht
 */
public final class ErrorsListModel extends SimpleListModel<IError> implements ErrorListener {
    private static final long serialVersionUID = 3355362999481373712L;

    /**
     * Construct a new ErrorsListModel.
     *
     * @param errorService The error service.
     */
    public ErrorsListModel(IErrorService errorService) {
        super();

        errorService.addErrorListener(this);

        List<IError> errorsList = errorService.getErrors();

        for (IError error : errorsList) {
            addElement(error);
        }
    }

    @Override
    public void errorOccurred(IError error) {
        addElement(error);

        fireContentsChanged(this, getSize() - 2, getSize());
    }
}