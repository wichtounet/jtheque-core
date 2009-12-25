package org.jtheque.core.managers.core.application;

import org.jtheque.core.utils.SystemProperty;
import org.jtheque.utils.collections.ArrayUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

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
 * Properties contained in a i18n base name in the "i18n" folder of the core folder.
 *
 * @author Baptiste Wicht
 */
public final class I18nAplicationProperties implements ApplicationProperties {
    private final ReloadableResourceBundleMessageSource resourceBundle;

    /**
     * Construct a new I18nAplicationProperties.
     */
    public I18nAplicationProperties(){
        super();

        resourceBundle = new ReloadableResourceBundleMessageSource();

        resourceBundle.setBasename("file:" + SystemProperty.USER_DIR.get() + "i18n/application");
    }

    @Override
    public String getName(){
        return getMessage("name");
    }

    @Override
    public String getAuthor(){
        return getMessage("author");
    }

    @Override
    public String getEmail(){
        return getMessage("email");
    }

    @Override
    public String getSite(){
        return getMessage("site");
    }

    @Override
    public String getCopyright(){
        return getMessage("copyright");
    }

    /**
     * Return the message with the specified key in the application resource bundle.
     *
     * @param key The key of the message.
     *
     * @return The String value of the message with the specified key.
     */
    private String getMessage(String key){
        return resourceBundle.getMessage(key, ArrayUtils.ZERO_LENGTH_ARRAY, Locale.getDefault());
    }
}
