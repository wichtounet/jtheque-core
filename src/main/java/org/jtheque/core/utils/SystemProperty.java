package org.jtheque.core.utils;

import org.jtheque.utils.io.FileUtils;

import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedAction;

/*
 * This file is part of JTheque.
 * 	   
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License. 
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
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
                value += "/";
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
    public void set(final String value) {
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
    public String getName() {
        return name;
    }
}