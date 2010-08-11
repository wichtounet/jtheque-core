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

import org.jtheque.errors.Error;
import org.jtheque.errors.ErrorListener;
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
public final class ErrorService implements org.jtheque.errors.ErrorService {
    private final Collection<Error> errors = CollectionUtils.newConcurrentList();

    @GuardedInternally
    private final WeakEventListenerList<ErrorListener> listeners = WeakEventListenerList.create();

    @Override
    public Collection<Error> getErrors() {
        return CollectionUtils.protect(errors);
    }

    @Override
    public void addError(org.jtheque.errors.Error error) {
        errors.add(error);

        fireErrorOccurred(error);
    }

    @Override
    public void addErrorListener(ErrorListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeErrorListener(ErrorListener listener) {
        listeners.remove(listener);
    }

    /**
     * Fire an error occurred event.
     *
     * @param error The new error.
     */
    private void fireErrorOccurred(Error error) {
        for (ErrorListener listener : listeners) {
            listener.errorOccurred(error);
        }
    }
}