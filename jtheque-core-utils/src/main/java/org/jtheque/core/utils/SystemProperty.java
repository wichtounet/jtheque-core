package org.jtheque.core.utils;

import org.jtheque.utils.io.FileUtils;

import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedAction;

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
 * A system property enumeration.
 *
 * @author Baptiste Wicht
 */
public enum SystemProperty {
    JAVA_IO_TMP_DIR("java.io.tmpdir") {
        @Override
        public String get() {
            String value = super.get();

            if (!value.endsWith(File.separator)) {
                value += File.separator;
            }

            FileUtils.createIfNotExists(new File(value));

            return value;
        }
    },

    USER_DIR("user.dir") {
        @Override
        public String get() {
            String value = super.get();

            if (!value.endsWith("/")) {
                return value + '/';
            }

            return value;
        }
    },

    JTHEQUE_LOG("jtheque.log");

    private final String name;

    /**
     * Construct a new SystemProperty.
     *
     * @param name the name of the property.
     */
    SystemProperty(String name) {
        this.name = name;
    }

    /**
     * Return the value of the property.
     *
     * @return The value of the property.
     */
    public String get() {
        return AccessController.doPrivileged(new PrivilegedAction<String>() {
            @Override
            public String run() {
                return System.getProperty(getName());
            }
        });
    }

    /**
     * Set the value of the property.
     *
     * @param value The value of the property.
     */
    public final void set(final String value) {
        AccessController.doPrivileged(new PrivilegedAction<String>() {
            @Override
            public String run() {
                return System.setProperty(getName(), value);
            }
        });
    }

    /**
     * Return the name of the property.
     *
     * @return The name of the property.
     */
    public final String getName() {
        return name;
    }
}