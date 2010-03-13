package org.jtheque.core.application;

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
 * Represent the internationalizable properties of an application.
 *
 * @author Baptiste Wicht
 */
public interface ApplicationProperties {
    /**
     * Return the name of the application.
     *
     * @return The name of the application.
     */
    String getName();

    /**
     * Return the author of the application.
     *
     * @return The author of the application.
     */
    String getAuthor();

    /**
     * Return the email of the author of the application.
     *
     * @return The email of the author of the application.
     */
    String getEmail();

    /**
     * Return the site of the application.
     *
     * @return The site of the application.
     */
    String getSite();

    /**
     * Return the copyright of the application.
     *
     * @return The copyright of the application.
     */
    String getCopyright();
}