package org.jtheque.core.managers.core;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.core.application.Application;
import org.jtheque.core.managers.core.io.Files;
import org.jtheque.core.managers.core.io.Folders;
import org.jtheque.core.managers.core.io.IFilesContainer;
import org.jtheque.core.managers.core.io.IFoldersContainer;
import org.jtheque.core.managers.error.IErrorManager;
import org.jtheque.core.managers.error.InternationalizedError;
import org.jtheque.core.managers.lifecycle.ILifeCycleManager;
import org.jtheque.core.managers.lifecycle.JThequeCoreTimer;
import org.jtheque.core.managers.lifecycle.LifeCycleManager;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.state.IStateManager;
import org.jtheque.core.managers.state.StateException;
import org.jtheque.utils.bean.Version;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The core.
 *
 * @author Baptiste Wicht
 */
public final class Core implements ICore {
    private static final String CORE_MESSAGES_FILE = "http://jtheque.developpez.com/public/messages/core.message";
    private static final Version VERSION = new Version("2.0.2");

    private static final ICore CORE = new Core();

    private final Collection<String> creditsMessage;

    private ILifeCycleManager lifeCycleManager;

    private Application application;

    private final IFoldersContainer foldersContainer;
    private final IFilesContainer filesContainer;

    private CoreConfiguration configuration;

    /**
     * Construct a new core.
     */
    private Core() {
        super();

        foldersContainer = new Folders();
        filesContainer = new Files();

        creditsMessage = new ArrayList<String>(5);

        creditsMessage.add("about.view.copyright");
    }

    @Override
    public Version getCoreCurrentVersion() {
        return VERSION;
    }

    /**
     * Return the unique instance of the class.
     *
     * @return The singleton instance of the class.
     */
    public static ICore getInstance() {
        return CORE;
    }

    @Override
    public void launchJThequeCore(Application application) {
        this.application = application;

        JThequeCoreTimer.start();

        Thread.currentThread().setName("JTheque Main Thread");

        lifeCycleManager = new LifeCycleManager();
        lifeCycleManager.initCycles();
        lifeCycleManager.launchNextPhase();
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
        return !(version.equals(VERSION) || VERSION.isGreaterThan(version));//Only compatible with next versions
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
    public ILifeCycleManager getLifeCycleManager() {
        return lifeCycleManager;
    }

    @Override
    public String getImagesBaseName() {
        return IMAGES_BASE_NAME;
    }

    @Override
    public CoreConfiguration getConfiguration() {
        if (configuration == null) {
            configuration = Managers.getManager(IStateManager.class).getState(CoreConfiguration.class);

            if (configuration == null) {
                initConfiguration();
            }
        }

        return configuration;
    }

    /**
     * Init the core configuration.
     */
    private void initConfiguration() {
        try {
            configuration = Managers.getManager(IStateManager.class).createState(CoreConfiguration.class);
        } catch (StateException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
            configuration = new CoreConfiguration();
            Managers.getManager(IErrorManager.class).addError(new InternationalizedError("error.loading.configuration"));
        }

        configuration.setDefaults();
    }
}