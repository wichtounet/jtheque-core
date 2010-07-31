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

import org.jtheque.core.able.ApplicationListener;
import org.jtheque.core.able.ICore;
import org.jtheque.core.able.ICoreConfiguration;
import org.jtheque.core.able.IFilesContainer;
import org.jtheque.core.able.IFoldersContainer;
import org.jtheque.core.able.application.Application;
import org.jtheque.core.able.lifecycle.ILifeCycle;
import org.jtheque.core.utils.WeakEventListenerList;
import org.jtheque.events.able.EventService;
import org.jtheque.images.able.ImageService;
import org.jtheque.states.able.IStateService;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;

import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.util.Collection;

/**
 * The core.
 *
 * @author Baptiste Wicht
 */
public final class Core implements ICore {
    private static final String CORE_MESSAGES_FILE = "http://jtheque.baptiste-wicht.com/files/messages/jtheque-core-messages.xml";

    private final WeakEventListenerList listeners = new WeakEventListenerList();

    private final Collection<String> creditsMessage;
    private final IFoldersContainer foldersContainer;
    private final IFilesContainer filesContainer;
    private final ICoreConfiguration configuration;
    private final ImageService imageService;
    private final ILifeCycle lifeCycle;

    private Application application;

    /**
     * Construct a new Core.
     *
     * @param stateService The state service.
     * @param imageService The resource service.
     * @param eventService The event service.
     */
    public Core(IStateService stateService, ImageService imageService, EventService eventService) {
        super();

        this.imageService = imageService;
        lifeCycle = new LifeCycle(eventService, this);

        foldersContainer = new Folders(this);
        filesContainer = new Files(this);

        configuration = stateService.getState(new CoreConfiguration());

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
        listeners.add(ApplicationListener.class, listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener listener) {
        listeners.remove(ApplicationListener.class, listener);
    }

    private void fireApplicationSetted(Application application){
        ApplicationListener[] applicationListeners = listeners.getListeners(ApplicationListener.class);

        for(ApplicationListener applicationListener : applicationListeners){
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
    public IFoldersContainer getFolders() {
        return foldersContainer;
    }

    @Override
    public IFilesContainer getFiles() {
        return filesContainer;
    }

    @Override
    public ILifeCycle getLifeCycle() {
        return lifeCycle;
    }

    @Override
    public ICoreConfiguration getConfiguration() {
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