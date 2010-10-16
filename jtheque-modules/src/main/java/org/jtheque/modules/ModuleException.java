package org.jtheque.modules;

import org.jtheque.i18n.InternationalizableException;
import org.jtheque.utils.StringUtils;

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
 * An exception for a module operation.
 *
 * @author Baptiste Wicht
 */
public class ModuleException extends Exception implements InternationalizableException {
    private static final long serialVersionUID = 6510172212610174776L;

    /**
     * The type of operation.
     *
     * @author Baptiste Wicht
     */
    public enum ModuleOperation {
        START,
        STOP,
        UNINSTALL,
        LOAD,
        INSTALL
    }

    private final ModuleOperation operation;
    private final String i18nMessage;

    /**
     * Create a new ModuleException.
     *
     * @param cause     The cause of the exception.
     * @param operation The operation that caused the error.
     */
    public ModuleException(Throwable cause, ModuleOperation operation) {
        this("", cause, operation);
    }

    /**
     * Create a new ModuleException.
     *
     * @param i18nMessage The i18n message of the error.
     * @param cause       The cause of the exception.
     * @param operation   The operation that caused the error.
     */
    public ModuleException(String i18nMessage, Throwable cause, ModuleOperation operation) {
        super(cause);

        this.i18nMessage = i18nMessage;
        this.operation = operation;
    }

    /**
     * Create a new ModuleException without exception cause.
     *
     * @param i18nMessage The i18n message of the error.
     * @param operation   The operation that caused the error.
     */
    public ModuleException(String i18nMessage, ModuleOperation operation) {
        super();

        this.i18nMessage = i18nMessage;
        this.operation = operation;
    }

    /**
     * Return the operation that caused the exception.
     *
     * @return The operation that caused the exception.
     */
    public ModuleOperation getOperation() {
        return operation;
    }

    /**
     * Indicate if the exception has an i18n message or not.
     *
     * @return {@code true} if the exception has an i18n message otherwise {@code false}.
     */
    public boolean hasI18nMessage() {
        return StringUtils.isNotEmpty(i18nMessage);
    }

    @Override
    public String getI18nMessage() {
        return i18nMessage;
    }
}
