package org.jtheque.i18n.impl;

import org.jtheque.i18n.I18NResource;
import org.jtheque.i18n.I18NResourceFactory;
import org.jtheque.i18n.Internationalizable;
import org.jtheque.i18n.LanguageService;
import org.jtheque.states.StateService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.SystemProperty;
import org.jtheque.utils.annotations.GuardedInternally;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.CopyException;
import org.jtheque.utils.io.FileUtils;

import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
 * A language service implementation.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public final class LanguageServiceImpl implements LanguageService {
    private static final String[] ZERO_LENGTH_ARRAY = new String[0];

    @GuardedInternally
    private final Map<String, String> baseNames = CollectionUtils.newConcurrentMap(10);
    
    @GuardedInternally
    private final Set<Internationalizable> internationalizables = CollectionUtils.newConcurrentSet();

    @GuardedInternally
    private final JThequeResourceBundle resourceBundle = new JThequeResourceBundle();

    @GuardedInternally
    private final LanguageState state;

    private volatile Locale locale = Locale.getDefault();

    private final Object lock = new Object();

    /**
     * Construct a new ResourceManager.
     *
     * @param stateService The state service.
     */
    public LanguageServiceImpl(StateService stateService) {
        super();

        state = stateService.getState(new LanguageState());

        init();
    }

    /**
     * Init the language service.
     */
    private void init() {
        locale = toLocale(state.getLanguage());

        Locale.setDefault(locale);

        registerResource("core_messages", Version.get("1.0"),
                I18NResourceFactory.fromResource(getClass(), "org/jtheque/i18n/messages_de.properties"),
                I18NResourceFactory.fromResource(getClass(), "org/jtheque/i18n/messages_en.properties"),
                I18NResourceFactory.fromResource(getClass(), "org/jtheque/i18n/messages_fr.properties"));

        registerResource("core_dialogs", Version.get("1.0"),
                I18NResourceFactory.fromResource(getClass(), "org/jtheque/i18n/dialogs_de.properties"),
                I18NResourceFactory.fromResource(getClass(), "org/jtheque/i18n/dialogs_en.properties"),
                I18NResourceFactory.fromResource(getClass(), "org/jtheque/i18n/dialogs_fr.properties"));

        registerResource("core_i18n", Version.get("1.0"),
                I18NResourceFactory.fromResource(getClass(), "org/jtheque/i18n/core_de.properties"),
                I18NResourceFactory.fromResource(getClass(), "org/jtheque/i18n/core_en.properties"),
                I18NResourceFactory.fromResource(getClass(), "org/jtheque/i18n/core_fr.properties"));

        registerResource("core_errors", Version.get("1.0"),
                I18NResourceFactory.fromResource(getClass(), "org/jtheque/i18n/errors_de.properties"),
                I18NResourceFactory.fromResource(getClass(), "org/jtheque/i18n/errors_en.properties"),
                I18NResourceFactory.fromResource(getClass(), "org/jtheque/i18n/errors_fr.properties"));
    }

    @Override
    public void registerResource(String name, Version version, I18NResource... resources) {
        synchronized (this) {
            Version previousVersion = state.getResourceVersion(name);

            File folder = new File(SystemProperty.USER_DIR.get(), "i18n");

            FileUtils.createIfNotExists(folder);

            if (previousVersion == null || version.isGreaterThan(previousVersion)) {
                state.setResourceVersion(name, version);

                for (I18NResource resource : resources) {
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

            String baseName = "file:" + folder + '/' + getBaseName(resources[0]);

            resourceBundle.addBaseName(baseName);

            baseNames.put(name, baseName);
        }
    }

    @Override
    public void releaseResource(String name) {
        if (baseNames.containsKey(name)) {
            resourceBundle.removeBaseName(baseNames.get(name));
        }
    }

    @Override
    public void setCurrentLanguage(String language) {
        String shortForm = toShortForm(language);

        synchronized (lock) {
            state.setLanguage(shortForm);

            setCurrentLocale(toLocale(shortForm));
        }
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
        return locale.getDisplayLanguage();
    }

    @Override
    public String[] getLinesMessage(String key) {
        String message = getMessage(key);

        if (StringUtils.isEmpty(message)) {
            return ZERO_LENGTH_ARRAY;
        }

        return StringUtils.getLines(message);
    }

    @Override
    public String getMessage(String key, Object... replaces) {
        if (StringUtils.isEmpty(key)) {
            return StringUtils.EMPTY_STRING;
        }

        return resourceBundle.getMessage(key, replaces, locale);
    }

    /**
     * Return the base name of the i18n resource.
     *
     * @param resource The resource to compute the base name for.
     *
     * @return The base name of the i18n resource.
     */
    private static String getBaseName(I18NResource resource) {
        String name = resource.getFileName();

        if (name.contains("_")) {
            return name.substring(0, name.indexOf('_'));
        }

        return name.substring(0, name.indexOf('.'));
    }

    /**
     * Convert the language to his short form.
     *
     * @param language The language to convert.
     *
     * @return The short form of the language.
     */
    private static String toShortForm(CharSequence language) {
        if ("Français".equals(language) || "fr".equals(language)) {
            return "fr";
        } else if ("Deutsch".equals(language) || "de".equals(language)) {
            return "de";
        }

        return "en";
    }

    private static Locale toLocale(String shortForm) {
        if ("fr".equals(shortForm)) {
            return Locale.FRENCH;
        } else if ("de".equals(shortForm)) {
            return Locale.GERMAN;
        } else if ("en".equals(shortForm)) {
            return Locale.ENGLISH;
        }

        LoggerFactory.getLogger(LanguageServiceImpl.class).error("Unable to get the locale");

        return Locale.ENGLISH;
    }
}