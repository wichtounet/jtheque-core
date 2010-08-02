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

import org.jtheque.core.able.*;
import org.jtheque.core.able.CoreConfiguration;
import org.jtheque.core.able.application.Application;
import org.jtheque.core.able.lifecycle.LifeCycle;
import org.jtheque.events.able.EventService;
import org.jtheque.images.able.ImageService;
import org.jtheque.states.able.StateService;
import org.jtheque.utils.annotations.GuardedInternally;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.collections.WeakEventListenerList;

import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.util.Collection;

/**
 * The core.
 *
 * @author Baptiste Wicht
 */
public final class Core implements org.jtheque.core.able.Core {
    private static final String CORE_MESSAGES_FILE = "http://jtheque.baptiste-wicht.com/files/messages/jtheque-core-messages.xml";

    @GuardedInternally
    private final WeakEventListenerList<ApplicationListener> listeners = WeakEventListenerList.create();

    private final Collection<String> creditsMessage;
    private final FoldersContainer foldersContainer;
    private final FilesContainer filesContainer;
    private final org.jtheque.core.able.CoreConfiguration configuration;
    private final ImageService imageService;
    private final LifeCycle lifeCycle;

    private Application application;

    /**
     * Construct a new Core.
     *
     * @param stateService The state service.
     * @param imageService The resource service.
     * @param eventService The event service.
     */
    public Core(StateService stateService, ImageService imageService, EventService eventService) {
        super();

        this.imageService = imageService;
        lifeCycle = new org.jtheque.core.impl.LifeCycle(eventService, this);

        foldersContainer = new Folders(this);
        filesContainer = new Files(this);

        configuration = stateService.getState(new org.jtheque.core.impl.CoreConfiguration());

        creditsMessage = CollectionUtils.newList(5);
        creditsMessage.add("about.view.copyright");
    }

    @Override
    public void setApplication(Application application) {
        this.application = application;

        fireApplicationSetted(application);

        LoggerFactory.getLogger(getClass()).debug("Configuring core with application {}", application);

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

    private void fireApplicationSetted(Application application){
        for(ApplicationListener applicationListener : listeners){
            applicationListener.applicationSetted(application);
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
        return creditsMessage;
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
    public org.jtheque.core.able.lifecycle.LifeCycle getLifeCycle() {
        return lifeCycle;
    }

    @Override
    public CoreConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public Collection<String> getPossibleLanguages() {
        Collection<String> languagesLong = CollectionUtils.newList(2);

        for (String supportedLanguage : application.getSupportedLanguages()) {
            if ("fr".equals(supportedLanguage)) {
                languagesLong.add("Français");
            } else if ("en".equals(supportedLanguage)) {
                languagesLong.add("English");
            } else if ("de".equals(supportedLanguage)) {
                languagesLong.add("Deutsch");
            }
        }

        return languagesLong;
    }
}