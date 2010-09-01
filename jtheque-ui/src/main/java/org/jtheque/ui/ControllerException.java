package org.jtheque.ui;

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
 * A controller exception. This is a runtime exception that can occurs during handle actions from a controller.
 *
 * @author Baptiste Wicht
 */
public final class ControllerException extends RuntimeException {
    private static final long serialVersionUID = -1359873732036438173L;

    /**
     * Construct a new ControllerException with the given message.
     *
     * @param message The error message.
     */
    public ControllerException(String message) {
        super(message);
    }

    /**
     * Construct a new ControllerException with the given message and cause.
     *
     * @param message The error message.
     * @param cause The cause of the error. 
     */
    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}