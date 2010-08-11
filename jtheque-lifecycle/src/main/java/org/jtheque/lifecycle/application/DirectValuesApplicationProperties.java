package org.jtheque.lifecycle.application;

import org.jtheque.core.application.ApplicationProperties;
import org.jtheque.utils.annotations.Immutable;
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
@Immutable
final class DirectValuesApplicationProperties implements ApplicationProperties {
    private final InternationalString author;
    private final InternationalString name;
    private final InternationalString site;
    private final InternationalString email;
    private final InternationalString copyright;

    DirectValuesApplicationProperties(InternationalString author, InternationalString name,
                                             InternationalString site, InternationalString email,
                                             InternationalString copyright) {
        super();

        this.author = author;
        this.name = name;
        this.site = site;
        this.email = email;
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