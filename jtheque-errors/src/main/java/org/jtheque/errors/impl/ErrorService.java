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

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;
import org.jtheque.errors.able.IError;
import org.jtheque.errors.utils.InternationalizedError;
import org.jtheque.errors.able.IErrorService;
import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.utils.ui.SwingUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * An error manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class ErrorService implements IErrorService {
     private final Collection<IError> errors = new ArrayList<IError>(10);

    private final ILanguageService languageService;

    public ErrorService(ILanguageService languageService) {
        super();

        this.languageService = languageService;
    }

    @Override
    public void addError(IError error) {
        errors.add(error);

	    SwingUtils.inEdt(new DisplayErrorRunnable(error.toErrorInfo(languageService)));
    }

    @Override
    public void displayErrors() {
        for (IError error : errors) {
	        SwingUtils.inEdt(new DisplayErrorRunnable(error.toErrorInfo(languageService)));
        }
    }

    @Override
    public void addInternationalizedError(String messageKey) {
        addError(new InternationalizedError(messageKey));
    }

    @Override
    public void addInternationalizedError(String messageKey, Object... messageReplaces) {
        addError(new InternationalizedError(messageKey, messageReplaces));
    }

	/**
     * A Runnable to display an error.
     *
     * @author Baptiste Wicht
     */
    private static class DisplayErrorRunnable implements Runnable {
        private final ErrorInfo info;

        /**
         * The error info to display.
         *
         * @param info The error info to display.
         */
        DisplayErrorRunnable(ErrorInfo info){
            this.info = info;
        }

        @Override
        public void run() {
            JXErrorPane.showDialog(null, info);
        }
    }
}