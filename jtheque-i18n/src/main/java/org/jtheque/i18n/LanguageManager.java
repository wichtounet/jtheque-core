package org.jtheque.i18n;

import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.logging.ILoggingManager;
import org.jtheque.states.IStateManager;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.collections.ArrayUtils;
import org.osgi.framework.BundleContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.NoSuchMessageException;
import org.springframework.osgi.context.BundleContextAware;

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
public final class LanguageManager implements ILanguageManager, ApplicationContextAware, BundleContextAware {
    private final Map<String, Locale> languages;
    private Locale locale = Locale.getDefault();

    private final Set<Internationalizable> internationalizables;

    private LanguageState state;

    private static final String[] ZERO_LENGTH_ARRAY = new String[0];

    private ApplicationContext applicationContext;
    private BundleContext bundleContext;

    /**
     * Construct a new ResourceManager.
     */
    public LanguageManager() {
        super();

        languages = new HashMap<String, Locale>(2);

        languages.put("fr", Locale.FRENCH);
        languages.put("en", Locale.ENGLISH);
        languages.put("de", Locale.GERMAN);

        internationalizables = new HashSet<Internationalizable>(100);
    }

    /**
     * Return the resource bundle.
     *
     * @return The resource bundle.
     */
    private EditableResourceBundle getResourceBundle() {
        return (EditableResourceBundle) applicationContext.getBean("messageSource");
    }

    public void init(){
        state = applicationContext.getBean(IStateManager.class).getOrCreateState(LanguageState.class);

        locale = languages.get(state.getLanguage());

        if (locale == null) {
            applicationContext.getBean(ILoggingManager.class).getLogger(getClass()).error("Unable to get the locale");

            locale = Locale.FRENCH;
        }

        Locale.setDefault(locale);
    }

    @Override
    public void addBaseName(String baseName) {
        if (baseName != null) {
            getResourceBundle().addBaseName(baseName);
        }
    }

    @Override
    public void removeBaseName(String baseName) {
        getResourceBundle().removeBaseName(baseName);
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

        refreshInternationalizables();
    }

    /**
     * Refresh all the internationalizables elements.
     */
    private void refreshInternationalizables() {
        for (Internationalizable internationalizable : internationalizables) {
            internationalizable.refreshText();
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
    public String getMessage(String key) {
        if (StringUtils.isEmpty(key)) {
            return StringUtils.EMPTY_STRING;
        }

        String message;

        try {
            message = applicationContext.getMessage(key, ArrayUtils.ZERO_LENGTH_ARRAY, locale);
        } catch (NoSuchMessageException e) {
            message = key;

            applicationContext.getBean(ILoggingManager.class).getLogger(getClass()).warn(
                    "No message found for {} with locale {}", key, locale.getDisplayName());
        }

        return message;
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
            message = applicationContext.getMessage(key, replaces, locale);
        } catch (NoSuchMessageException e) {
            message = key;

            OSGiUtils.getService(bundleContext, ILoggingManager.class).getLogger(getClass()).error(
                    "No message found for {} with locale {}", key, locale.getDisplayName());
        }

        return message;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }
}
