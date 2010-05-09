package org.jtheque.update;

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

import org.jtheque.core.ICore;
import org.jtheque.core.utils.WeakEventListenerList;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.impl.InstallationResult;
import org.jtheque.states.IStateService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.update.actions.UpdateAction;
import org.jtheque.update.versions.IVersionsLoader;
import org.jtheque.update.versions.InstallVersion;
import org.jtheque.update.versions.OnlineVersion;
import org.jtheque.utils.bean.Version;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Manage the org.jtheque.update of the application. This class can go on internet to verify if a more recent
 * version of JTheque is available and download one new version if there is one.
 *
 * @author Baptiste Wicht
 */
public final class UpdateService implements IUpdateService {
    private final Collection<Updatable> updatables;
    private final UpdatableState state;
    private final WeakEventListenerList listeners = new WeakEventListenerList();
    private final ICore core;
    private final IUIUtils uiUtils;
    private final IModuleService moduleService;
    private final IVersionsLoader versionsLoader;

    public UpdateService(IStateService stateService, ICore core, IUIUtils uiUtils, IModuleService moduleService, IVersionsLoader versionsLoader) {
        super();

        this.core = core;
        this.uiUtils = uiUtils;
        this.moduleService = moduleService;
        this.versionsLoader = versionsLoader;

        updatables = new ArrayList<Updatable>(10);

        state = stateService.getState(new UpdatableState());

        for (Updatable updatable : updatables) {
            readUpdatableVersion(updatable);
        }
    }

    /**
     * Read the updatable version of the updatable.
     *
     * @param updatable The updatable to fill with the version.
     */
    private void readUpdatableVersion(Updatable updatable) {
        Version version = state.getVersion(updatable.getName());

        if (version == null) {
            updatable.setVersion(updatable.getDefaultVersion());
        } else {
            updatable.setVersion(version);
        }
    }

    @Override
    public void update(Updatable updatable, Version versionToDownload) {
        //Download all files
        for (OnlineVersion onlineVersion : versionsLoader.getOnlineVersions(updatable)) {
            if (onlineVersion.getVersion().equals(versionToDownload)) {
                applyOnlineVersion(onlineVersion);

                break;
            }
        }

        updatable.setVersion(new Version(versionToDownload.getVersion()));
        state.setVersion(updatable.getName(), updatable.getVersion());
        updatable.setUpdated();
    }

    @Override
    public void update(Version versionToDownload) {
        for (OnlineVersion onlineVersion : versionsLoader.getOnlineVersions(core)) {
            if (onlineVersion.getVersion().equals(versionToDownload)) {
                applyOnlineVersion(onlineVersion);

                break;
            }
        }

            uiUtils.displayI18nText("message.application.updated");

        core.getLifeCycle().restart();
    }

    /**
     * Apply the online version.
     *
     * @param onlineVersion The online version to apply.
     */
    private static void applyOnlineVersion(OnlineVersion onlineVersion) {
        for (UpdateAction action : onlineVersion.getActions()) {
            action.execute();
        }
    }

    @Override
    public InstallationResult install(String versionFileURL) {
        InstallationResult result = new InstallationResult();

        try {
            InstallVersion version = versionsLoader.getInstallVersion(versionFileURL);

            for (UpdateAction action : version.getActions()) {
                action.execute();
            }

            result.setJarFile(version.getJarFile());
            result.setName(version.getTitle());
            result.setInstalled(true);
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            result.setInstalled(false);
        }

        return result;
    }

    @Override
    public void update(Module module, Version version) {
        OnlineVersion onlineVersion = versionsLoader.getOnlineVersion(version, module);

        if (onlineVersion.getCoreVersion().isGreaterThan(ICore.VERSION)) {
            uiUtils.displayI18nText("modules.message.versionproblem");
        } else {
            //Download all files
            for (UpdateAction action : onlineVersion.getActions()) {
                action.execute();
            }

            uiUtils.displayI18nText("message.application.updated");

            core.getLifeCycle().restart();
        }
    }

    @Override
    public Collection<Version> getKernelVersions() {
        return getVersions(core);
    }

    @Override
    public List<String> getPossibleUpdates() {
        List<String> messages = new ArrayList<String>(2);

        if (!isCurrentVersionUpToDate()) {
            messages.add("dialogs.propose.update");
        }

        if (!isAModuleNotUpToDate()) {
            messages.add("dialogs.propose.module.update");
        }

        return messages;
    }

    @Override
    public boolean isCurrentVersionUpToDate() {
        boolean upToDate = true;

        for (Version version : getKernelVersions()) {
            if (!ICore.VERSION.isGreaterThan(version)) {
                upToDate = false;
                break;
            }
        }

        return upToDate;
    }

    /**
     * Indicate if all modules are up to date.
     *
     * @return true if all modules are up to date else false.
     */
    private boolean isAModuleNotUpToDate() {
        boolean notUpToDate = false;

        for (Module module : moduleService.getModules()) {
            if (!isUpToDate(module)) {
                notUpToDate = true;
                break;
            }
        }

        return notUpToDate;
    }

    @Override
    public boolean isUpToDate(Object object) {
        for (Version version : versionsLoader.getVersions(object)) {
            if (!versionsLoader.getVersion(object).isGreaterThan(version)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void updateToMostRecentVersion(Module module) {
        Version maxVersion = null;

        for (Version version : versionsLoader.getVersions(module)) {
            if (maxVersion == null || version.isGreaterThan(maxVersion)) {
                maxVersion = version;
            }
        }

        update(module, maxVersion);
    }

    @Override
    public Version getMostRecentVersion(Object object) {
        return versionsLoader.getMostRecentVersion(object);
    }

    @Override
    public Collection<Version> getVersions(Object object) {
        return versionsLoader.getVersions(object);
    }

    @Override
    public void registerUpdatable(Updatable updatable) {
        updatables.add(updatable);

        fireUpdatableAdded(updatable);
    }

    @Override
    public Collection<Updatable> getUpdatables() {
        return updatables;
    }

    @Override
    public void addUpdatableListener(UpdatableListener listener) {
        listeners.add(UpdatableListener.class, listener);
    }

    @Override
    public void removeUpdatableListener(UpdatableListener listener) {
        listeners.remove(UpdatableListener.class, listener);
    }

    /**
     * Fire an updatable added event.
     */
    private void fireUpdatableAdded(Updatable updatable) {
        for (UpdatableListener l : listeners.getListeners(UpdatableListener.class)) {
            l.updatableAdded(updatable);
        }
    }

    @Override
    public Version getMostRecentCoreVersion() {
        return getMostRecentVersion(core);
    }
}