package org.jtheque.lifecycle.application;

import org.jtheque.core.application.ApplicationProperties;
import org.jtheque.core.Folders;
import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.collections.ArrayUtils;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

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
 * Properties contained in a i18n base name in the "i18n" folder of the core folder.
 *
 * @author Baptiste Wicht
 */
@Immutable
final class I18nApplicationProperties implements ApplicationProperties {
    private final ReloadableResourceBundleMessageSource resourceBundle;

    /**
     * Construct a new I18nApplicationProperties.
     */
    I18nApplicationProperties() {
        super();

        resourceBundle = new ReloadableResourceBundleMessageSource();

        resourceBundle.setAlwaysUseMessageFormat(false);
        resourceBundle.setUseCodeAsDefaultMessage(true);
        resourceBundle.setBasename("file:" + Folders.getApplicationFolder().getAbsolutePath() + "/i18n/application");
    }

    @Override
    public String getName() {
        return getMessage("name");
    }

    @Override
    public String getAuthor() {
        return getMessage("author");
    }

    @Override
    public String getEmail() {
        return getMessage("email");
    }

    @Override
    public String getSite() {
        return getMessage("site");
    }

    @Override
    public String getCopyright() {
        return getMessage("copyright");
    }

    /**
     * Return the message with the specified key in the application resource bundle.
     *
     * @param key The key of the message.
     *
     * @return The String value of the message with the specified key.
     */
    private String getMessage(String key) {
        return resourceBundle.getMessage(key, ArrayUtils.EMPTY_ARRAY, Locale.getDefault());
    }
}
