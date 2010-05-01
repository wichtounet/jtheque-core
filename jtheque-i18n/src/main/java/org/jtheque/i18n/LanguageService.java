package org.jtheque.i18n;

import org.jtheque.core.utils.SystemProperty;
import org.jtheque.states.IStateService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.io.CopyException;
import org.jtheque.utils.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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
 * @author Baptiste Wicht
 */
public final class LanguageService implements ILanguageService {
    private final Map<String, Locale> languages;
    private Locale locale = Locale.getDefault();

    private final Set<Internationalizable> internationalizables;

    private final LanguageState state;

    private static final String[] ZERO_LENGTH_ARRAY = new String[0];

    private final JThequeResourceBundle resourceBundle;

    /**
     * Construct a new ResourceManager.
     */
    public LanguageService(IStateService stateService) {
        super();

        languages = new HashMap<String, Locale>(3);

        languages.put("fr", Locale.FRENCH);
        languages.put("en", Locale.ENGLISH);
        languages.put("de", Locale.GERMAN);

        internationalizables = new HashSet<Internationalizable>(100);

        resourceBundle = new JThequeResourceBundle();
        resourceBundle.setCacheSeconds(-1);
        resourceBundle.setDefaultEncoding("UTF-8");
        resourceBundle.setUseCodeAsDefaultMessage(true);

        state = stateService.getState(new LanguageState());
    }

    @PostConstruct
    private void init(){
        locale = languages.get(state.getLanguage());

        if (locale == null) {
            LoggerFactory.getLogger(getClass()).error("Unable to get the locale");

            locale = Locale.FRENCH;
        }

        Locale.setDefault(locale);

        registerResource("core_messages", new Version("1.0"),
                I18nResource.fromResource(getClass(), "org/jtheque/i18n/messages_de.properties"),
                I18nResource.fromResource(getClass(), "org/jtheque/i18n/messages_en.properties"),
                I18nResource.fromResource(getClass(), "org/jtheque/i18n/messages_fr.properties"));

        registerResource("core_dialogs", new Version("1.0"),
                I18nResource.fromResource(getClass(), "org/jtheque/i18n/dialogs_de.properties"),
                I18nResource.fromResource(getClass(), "org/jtheque/i18n/dialogs_en.properties"),
                I18nResource.fromResource(getClass(), "org/jtheque/i18n/dialogs_fr.properties"));

        registerResource("core_i18n", new Version("1.0"),
                I18nResource.fromResource(getClass(), "org/jtheque/i18n/core_de.properties"),
                I18nResource.fromResource(getClass(), "org/jtheque/i18n/core_en.properties"),
                I18nResource.fromResource(getClass(), "org/jtheque/i18n/core_fr.properties"));

        registerResource("core_errors", new Version("1.0"),
                I18nResource.fromResource(getClass(), "org/jtheque/i18n/errors_de.properties"),
                I18nResource.fromResource(getClass(), "org/jtheque/i18n/errors_en.properties"),
                I18nResource.fromResource(getClass(), "org/jtheque/i18n/errors_fr.properties"));
    }

    @Override
    public void registerResource(String name, Version version, I18nResource... resources){
        Version previousVersion = state.getResourceVersion(name);

        File folder = new File(SystemProperty.USER_DIR.get(), "i18n");

        FileUtils.createIfNotExists(folder);

        if(previousVersion == null || version.isGreaterThan(previousVersion)){
            state.setResourceVersion(name, version);

            for(I18nResource resource : resources){
                File resourceFile = new File(folder, resource.getFileName());

                FileUtils.delete(resourceFile);

                try {
                    FileUtils.copy(resource.getResource().getInputStream(), resourceFile);
                } catch (CopyException e) {
                    LoggerFactory.getLogger(getClass()).error("Unable to copy resource {}", resource);
                    LoggerFactory.getLogger(getClass()).error("Cause exception", e);
                } catch (IOException e) {
                    LoggerFactory.getLogger(getClass()).error("Unable to copy resource {}", resource);
                    LoggerFactory.getLogger(getClass()).error("Cause exception", e);
                }
            }
        }

        resourceBundle.addBaseName("file:" + folder + getI18nResource(resources[0]));
    }

    private String getI18nResource(I18nResource resource) {
        if(resource.getFileName().contains("_")){
            return resource.getFileName().substring(0, resource.getFileName().indexOf('_'));
        }

        return resource.getFileName().substring(0, resource.getFileName().indexOf('.'));
    }

    @Override
    public void setCurrentLanguage(String language) {
        String shortForm = convertToShortForm(language);

        state.setLanguage(shortForm);

        setCurrentLocale(languages.get(shortForm));
    }

    /**
     * Convert the language to his short form.
     *
     * @param language The language to convert.
     * @return The short form of the language.
     */
    private static String convertToShortForm(CharSequence language) {
        if (StringUtils.isEmpty(language)) {
            return "en";
        }

        if ("Français".equals(language) || "fr".equals(language)) {
            return "fr";
        } else if ("Deutsch".equals(language) || "de".equals(language)) {
            return "de";
        }

        return "en";
    }

    /**
     * Set the current locale.
     *
     * @param locale The new current locale.
     */
    private void setCurrentLocale(Locale locale) {
        this.locale = locale;

        Locale.setDefault(locale);

        refreshAll();
    }

    @Override
    public void refreshAll() {
        for (Internationalizable internationalizable : internationalizables) {
            internationalizable.refreshText(this);
        }
    }

    @Override
    public void addInternationalizable(Internationalizable internationalizable) {
        internationalizables.add(internationalizable);
    }

    @Override
    public Locale getCurrentLocale() {
        return locale;
    }

    @Override
    public String getCurrentLanguage() {
        String language = null;

        for (Map.Entry<String, Locale> entry : languages.entrySet()) {
            if (entry.getValue().equals(locale)) {
                language = entry.getKey();
                break;
            }
        }

        return language;
    }

    @Override
    public String[] getLinesMessage(String key) {
        String message = getMessage(key);

        if (StringUtils.isEmpty(message)) {
            return ZERO_LENGTH_ARRAY;
        }

        Collection<String> tokens = new ArrayList<String>(5);

        Scanner scanner = new Scanner(message);

        while (scanner.hasNextLine()) {
            tokens.add(scanner.nextLine());
        }

        return tokens.toArray(new String[tokens.size()]);
    }

    @Override
    public String getMessage(String key, Object... replaces) {
        if (StringUtils.isEmpty(key)) {
            return StringUtils.EMPTY_STRING;
        }

        String message;

        try {
            message = resourceBundle.getMessage(key, replaces, locale);
        } catch (NoSuchMessageException e) {
            message = key;

            LoggerFactory.getLogger(getClass()).warn("No message found for {} with locale {}", key, locale.getDisplayName());
        }

        return message;
    }
}