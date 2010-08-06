package org.jtheque.core.impl;

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

import org.jtheque.core.able.ApplicationListener;
import org.jtheque.core.able.Core;
import org.jtheque.core.able.CoreConfiguration;
import org.jtheque.core.able.FilesContainer;
import org.jtheque.core.able.FoldersContainer;
import org.jtheque.core.able.application.Application;
import org.jtheque.images.able.ImageService;
import org.jtheque.states.able.StateService;
import org.jtheque.utils.annotations.GuardedInternally;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.collections.WeakEventListenerList;

import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.util.Collection;

/**
 * The core implementation.
 *
 * @author Baptiste Wicht
 */
public final class CoreImpl implements Core {
    private static final String CORE_MESSAGES_FILE = "http://jtheque.baptiste-wicht.com/files/messages/jtheque-core-messages.xml";

    @GuardedInternally
    private final WeakEventListenerList<ApplicationListener> listeners = WeakEventListenerList.create();

    @GuardedInternally
    private final Collection<String> creditsMessage = CollectionUtils.newConcurrentList();

    @GuardedInternally
    //Because cannot be modified
    private Collection<String> languages;

    @GuardedInternally
    private final FoldersContainer foldersContainer;

    @GuardedInternally
    private final FilesContainer filesContainer;

    @GuardedInternally
    private final CoreConfiguration configuration;

    private final ImageService imageService;

    private volatile Application application;

    /**
     * Construct a new Core.
     *
     * @param stateService The state service.
     * @param imageService The resource service.
     */
    public CoreImpl(StateService stateService, ImageService imageService) {
        super();

        this.imageService = imageService;

        foldersContainer = new Folders(this);
        filesContainer = new Files(this);

        configuration = stateService.getState(new CoreConfigurationImpl());

        creditsMessage.add("about.view.copyright");
    }

    @Override
    public void launchApplication(Application application) {
        if (this.application != null) {
            throw new IllegalStateException("The application is already launched");
        }

        this.application = application;

        Collection<String> languagesLong = CollectionUtils.newList(3);

        for (String supportedLanguage : application.getSupportedLanguages()) {
            if ("fr".equals(supportedLanguage)) {
                languagesLong.add("Français");
            } else if ("en".equals(supportedLanguage)) {
                languagesLong.add("English");
            } else if ("de".equals(supportedLanguage)) {
                languagesLong.add("Deutsch");
            }
        }

        languages = CollectionUtils.protect(languagesLong);

        fireApplicationLaunched(application);

        imageService.registerResource(WINDOW_ICON, new FileSystemResource(new File(application.getWindowIcon())));
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener listener) {
        listeners.remove(listener);
    }

    /**
     * Fire an application launched event.
     *
     * @param application The new application. 
     */
    private void fireApplicationLaunched(Application application) {
        for (ApplicationListener applicationListener : listeners) {
            applicationListener.applicationLaunched(application);
        }
    }

    @Override
    public String getCoreMessageFileURL() {
        return CORE_MESSAGES_FILE;
    }

    @Override
    public void addCreditsMessage(String key) {
        creditsMessage.add(key);
    }

    @Override
    public Collection<String> getCreditsMessage() {
        return CollectionUtils.protect(creditsMessage);
    }

    @Override
    public boolean isNotCompatibleWith(Version version) {
        //Compatible with 2.1.0 and greater
        return !(version.equals(VERSION) || version.isGreaterThan(VERSION));
    }

    @Override
    public Application getApplication() {
        return application;
    }

    @Override
    public FoldersContainer getFolders() {
        return foldersContainer;
    }

    @Override
    public FilesContainer getFiles() {
        return filesContainer;
    }

    @Override
    public CoreConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public Collection<String> getPossibleLanguages() {
        if (languages == null) {
            throw new IllegalStateException("The application has not been launched");
        }

        return languages;
    }
}