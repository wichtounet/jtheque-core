package org.jtheque.errors;

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
 * An error with its message and details internationalized.
 *
 * @author Baptiste Wicht
 */
public final class InternationalizedError extends JThequeError {
    private Object[] messageReplaces;
    private Object[] detailsReplaces;

    /**
     * Construct a new InternationalizedError.
     *
     * @param message The message key.
     */
    public InternationalizedError(String message) {
        super(message);
    }

    /**
     * Construct a new InternationalizedError.
     *
     * @param message  The message key.
     * @param replaces The replaces for the internationalization variable arguments of the message.
     */
    public InternationalizedError(String message, Object... replaces) {
        super(message);

        messageReplaces = replaces.clone();
    }

    /**
     * Construct a new InternationalizedError.
     *
     * @param message The message key.
     * @param details The details key.
     */
    public InternationalizedError(String message, String details) {
        super(message, details);
    }

    /**
     * Construct a new InternationalizedError.
     *
     * @param message  The message key.
     * @param replaces The replaces for the internationalization variable arguments of the message.
     * @param details  The details key.
     */
    public InternationalizedError(String message, Object[] replaces, String details) {
        super(message, details);

        messageReplaces = replaces.clone();
    }

    /**
     * Construct a new InternationalizedError.
     *
     * @param message         The message key.
     * @param replaces        The replaces for the internationalization variable arguments of the message.
     * @param details         The details key.
     * @param replacesDetails The replaces for the internationalization variable arguments of the details.
     */
    public InternationalizedError(String message, Object[] replaces, String details, Object[] replacesDetails) {
        super(message, details);

        messageReplaces = replaces.clone();
        detailsReplaces = replacesDetails.clone();
    }

    public Object[] getMessageReplaces() {
        return messageReplaces;
    }

    public Object[] getDetailsReplaces() {
        return detailsReplaces;
    }
}