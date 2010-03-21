package org.jtheque.update;

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

    private IVersionsLoader versionsLoader;

    private final WeakEventListenerList listeners = new WeakEventListenerList();
    
    public UpdateService() {
        super();

        updatables = new ArrayList<Updatable>(10);

        state = UpdateServices.get(IStateService.class).getOrCreateState(UpdatableState.class);

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
        for (OnlineVersion onlineVersion : versionsLoader.getOnlineVersions(UpdateServices.get(ICore.class))) {
            if (onlineVersion.getVersion().equals(versionToDownload)) {
                applyOnlineVersion(onlineVersion);

                break;
            }
        }

        UpdateServices.get(IUIUtils.class).displayI18nText("message.application.updated");

        UpdateServices.get(ICore.class).getLifeCycle().restart();
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
            UpdateServices.get(IUIUtils.class).displayI18nText("modules.message.versionproblem");
        } else {
            //Download all files
            for (UpdateAction action : onlineVersion.getActions()) {
                action.execute();
            }

            UpdateServices.get(IUIUtils.class).displayI18nText("message.application.updated");

            UpdateServices.get(ICore.class).getLifeCycle().restart();
        }
    }

    @Override
    public Collection<Version> getKernelVersions() {
        return getVersions(UpdateServices.get(ICore.class));
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

        for (Module module : UpdateServices.get(IModuleService.class).getModules()) {
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

        fireUpdatableAdded();
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
    private void fireUpdatableAdded() {
        for (UpdatableListener l : listeners.getListeners(UpdatableListener.class)) {
            l.updatableAdded();
        }
    }

    /**
     * Set the versions loader. This is not for use, this is only for Spring injection.
     *
     * @param versionsLoader The versions loader implementation.
     */
    public void setVersionsLoader(IVersionsLoader versionsLoader) {
        this.versionsLoader = versionsLoader;
    }
}