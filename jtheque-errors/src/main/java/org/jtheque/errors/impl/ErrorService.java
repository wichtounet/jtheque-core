package org.jtheque.errors.impl;

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

import org.jtheque.core.utils.WeakEventListenerList;
import org.jtheque.errors.able.ErrorListener;
import org.jtheque.errors.able.IError;
import org.jtheque.errors.able.IErrorService;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;

/**
 * An error service implementation.
 *
 * @author Baptiste Wicht
 */
public final class ErrorService implements IErrorService {
    private final Collection<IError> errors = CollectionUtils.newList();
    private final WeakEventListenerList eventListenerList = new WeakEventListenerList();

    @Override
    public void addError(IError error) {
        errors.add(error);

        fireErrorOccurred(error);
    }

    @Override
    public Collection<IError> getErrors() {
        return CollectionUtils.protect(errors);
    }

    @Override
    public void addErrorListener(ErrorListener listener) {
        eventListenerList.add(ErrorListener.class, listener);
    }

    @Override
    public void removeErrorListener(ErrorListener listener) {
        eventListenerList.remove(ErrorListener.class, listener);
    }

    /**
     * Fire an error occurred event.
     *
     * @param error The new error.
     */
    private void fireErrorOccurred(IError error) {
        for (ErrorListener listener : eventListenerList.getListeners(ErrorListener.class)) {
            listener.errorOccurred(error);
        }
    }
}