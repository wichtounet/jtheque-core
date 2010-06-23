package org.jtheque.errors.able;

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

import org.jtheque.i18n.able.ILanguageService;

import org.jdesktop.swingx.error.ErrorInfo;

/**
 * An error. That error must be convertible to an error info to be managed by the error service.
 *
 * @author Baptiste Wicht
 */
public interface IError {
    /**
     * Convert the action to an error info bean.
     *
     * @param languageService The language service to internationalize the error.
     *
     * @return The ErrorInfo corresponding to the IError.
     */
    ErrorInfo toErrorInfo(ILanguageService languageService);
}