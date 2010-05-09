package org.jtheque.core.application;

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