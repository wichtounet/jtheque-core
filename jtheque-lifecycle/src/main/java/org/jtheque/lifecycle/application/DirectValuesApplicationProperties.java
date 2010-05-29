package org.jtheque.lifecycle.application;

import org.jtheque.core.able.application.ApplicationProperties;
import org.jtheque.utils.bean.InternationalString;

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
 * Internationalizable properties directly configured in the application.xml file.
 *
 * @author Baptiste Wicht
 */
public final class DirectValuesApplicationProperties implements ApplicationProperties {
    private InternationalString author;
    private InternationalString name;
    private InternationalString site;
    private InternationalString email;
    private InternationalString copyright;

    /**
     * Set the author values.
     *
     * @param author The author values.
     */
    void setAuthor(InternationalString author) {
        this.author = author;
    }

    /**
     * Set the name values.
     *
     * @param name The name values.
     */
    void setName(InternationalString name) {
        this.name = name;
    }

    /**
     * Set the site values.
     *
     * @param site The site values.
     */
    void setSite(InternationalString site) {
        this.site = site;
    }

    /**
     * Set the email values.
     *
     * @param email The email values.
     */
    void setEmail(InternationalString email) {
        this.email = email;
    }

    /**
     * Set the copyright values.
     *
     * @param copyright The copyright values.
     */
    void setCopyright(InternationalString copyright) {
        this.copyright = copyright;
    }

    @Override
    public String getName() {
        return name.toString();
    }

    @Override
    public String getAuthor() {
        return author.toString();
    }

    @Override
    public String getEmail() {
        return email.toString();
    }

    @Override
    public String getSite() {
        return site.toString();
    }

    @Override
    public String getCopyright() {
        return copyright.toString();
    }
}