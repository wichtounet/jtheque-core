package org.jtheque.core.managers.module.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
 * An annotation to describe a module.
 *
 * @author Baptiste Wicht
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Module {
    /**
     * The identity of the module. This identity must be unique.
     *
     * @return A String identity for the module.
     */
    String id();

    /**
     * Return the i18n base name for the module.
     *
     * @return The i18n base name for the module.
     */
    String i18n();

    /**
     * Return the version of the module.
     *
     * @return The version of the module.
     */
    String version();

    /**
     * Return the needed core version of the module.
     *
     * @return The needed core version of the module.
     */
    String core();

    /**
     * Return the name of the jar file of the module.
     *
     * @return The name of the jar file of the module.
     */
    String jarFile();

    /**
     * The URL of the module.
     *
     * @return URL url of the module.
     */
    String url() default "";

    /**
     * The update URL.
     *
     * @return the update URL.
     */
    String updateURL() default "";

    /**
     * The dependency of the module.
     *
     * @return the dependency of the module.
     */
    String[] dependencies() default {};

    /**
     * The date of the module.
     *
     * @return the date of the module.
     */
    String date() default "";

    /**
     * The message's file URL.
     *
     * @return the message's files URL.
     */
    String messageFileURL() default "";
}