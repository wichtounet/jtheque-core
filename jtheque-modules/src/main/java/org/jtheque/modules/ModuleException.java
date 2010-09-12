package org.jtheque.modules;

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

public class ModuleException extends Exception {
    private static final long serialVersionUID = 6510172212610174776L;

    public enum ModuleOperation {
        START,
        STOP,
        UNINSTALL,
        LOAD,
        INSTALL
    }

    private final ModuleOperation operation;
    private final String i18nMessage;

    public ModuleException(Throwable cause, ModuleOperation operation) {
        super(cause);

        this.operation = operation;

        i18nMessage = "";
    }

    public ModuleException(String i18nMessage, Throwable cause, ModuleOperation operation){
        super(cause);

        this.i18nMessage = i18nMessage;
        this.operation = operation;
    }

    public ModuleException(String i18nMessage, ModuleOperation operation) {
        super();

        this.i18nMessage = i18nMessage;
        this.operation = operation;
    }

    public ModuleOperation getOperation() {
        return operation;
    }

    public boolean hasI18nMessage(){
        return StringUtils.isNotEmpty(i18nMessage);
    }

    public String getI18nMessage() {
        return i18nMessage;
    }
}
