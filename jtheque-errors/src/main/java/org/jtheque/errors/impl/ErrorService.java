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

import org.jtheque.errors.able.ErrorListener;
import org.jtheque.errors.able.IError;
import org.jtheque.errors.able.IErrorService;
import org.jtheque.utils.annotations.GuardedInternally;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.collections.WeakEventListenerList;

import java.util.Collection;

/**
 * An error service implementation.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public final class ErrorService implements IErrorService {
    private final Collection<IError> errors = CollectionUtils.newList();

    @GuardedInternally
    private final WeakEventListenerList<ErrorListener> listeners = WeakEventListenerList.create();

    @Override
    public Collection<IError> getErrors() {
        return CollectionUtils.protect(errors);
    }

    @Override
    public synchronized void addError(IError error) {
        errors.add(error);

        fireErrorOccurred(error);
    }

    @Override
    public synchronized void addErrorListener(ErrorListener listener) {
        listeners.add(listener);
    }

    @Override
    public synchronized void removeErrorListener(ErrorListener listener) {
        listeners.remove(listener);
    }

    /**
     * Fire an error occurred event.
     *
     * @param error The new error.
     */
    private void fireErrorOccurred(IError error) {
        for (ErrorListener listener : listeners) {
            listener.errorOccurred(error);
        }
    }
}