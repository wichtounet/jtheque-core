package org.jtheque.core;

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

import org.jtheque.core.application.Application;
import org.jtheque.core.lifecycle.ILifeCycle;
import org.jtheque.core.lifecycle.LifeCycle;
import org.jtheque.events.IEventService;
import org.jtheque.resources.IResourceService;
import org.jtheque.states.IStateService;
import org.jtheque.utils.bean.Version;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The core.
 *
 * @author Baptiste Wicht
 */
public final class Core implements ICore {
    private static final String CORE_MESSAGES_FILE = "http://jtheque.developpez.com/public/messages/core.message";

    private final Collection<String> creditsMessage;

    private Application application;

    private final IFoldersContainer foldersContainer;
    private final IFilesContainer filesContainer;
    private final LifeCycle lifeCycle;
    private final CoreConfiguration configuration;

    private final IResourceService resourceService;

    public Core(IStateService stateService, IEventService eventService, IResourceService resourceService) {
        super();

        this.resourceService = resourceService;

        foldersContainer = new Folders(this);
        filesContainer = new Files(this);
        lifeCycle = new LifeCycle(eventService, this);

        configuration = stateService.getState(new CoreConfiguration());

        creditsMessage = new ArrayList<String>(5);
        creditsMessage.add("about.view.copyright");
    }

    @Override
    public void setApplication(Application application) {
        this.application = application;

        LoggerFactory.getLogger(getClass()).debug("Configuring core with application {}", application);

        resourceService.registerResource(WINDOW_ICON, new FileSystemResource(new File(application.getWindowIcon())));
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
		//Compatible with 2.0.2 and greater versions
        Version version202 = new Version("2.0.2");

        return !(version.equals(version202) || version.isGreaterThan(version202));
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
    public CoreConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public Collection<String> getPossibleLanguages() {
        Collection<String> languagesLong = new ArrayList<String>(2);

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