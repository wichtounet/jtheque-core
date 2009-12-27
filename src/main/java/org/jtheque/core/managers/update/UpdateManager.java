package org.jtheque.core.managers.update;

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

import org.jtheque.core.managers.AbstractManager;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.module.IModuleManager;
import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.patch.IPatchManager;
import org.jtheque.core.managers.patch.OnlinePatch;
import org.jtheque.core.managers.update.actions.UpdateAction;
import org.jtheque.core.managers.update.versions.IVersionsLoader;
import org.jtheque.core.managers.update.versions.InstallVersion;
import org.jtheque.core.managers.update.versions.OnlineVersion;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.utils.bean.Version;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Manage the update of the application. This class can go on internet to verify if a more recent
 * version of JTheque is available and download one new version if there is one.
 *
 * @author Baptiste Wicht
 */
public final class UpdateManager extends AbstractManager implements IUpdateManager {
    private final Collection<Updatable> updatables;

    private UpdatableState state;

    private IVersionsLoader versionsLoader;

    /**
     * Private constructor, the instance of the class is accessible by getInstance() method.
     */
    public UpdateManager() {
        super();

        updatables = new ArrayList<Updatable>(10);
    }

    @Override
    public void init() {
        try {
            state = getStates().getOrCreateState(UpdatableState.class);
        } catch (Exception e) {
            getLogger().error(e);

            state = new UpdatableState();
        }

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
        for (OnlineVersion onlineVersion : versionsLoader.getOnlineVersions(Managers.getCore())) {
            if (onlineVersion.getVersion().equals(versionToDownload)) {
                applyOnlineVersion(onlineVersion);

                break;
            }
        }

        Managers.getManager(IPatchManager.class).setUpdated(true);

        Managers.getManager(IViewManager.class).displayI18nText("message.application.updated");

        Managers.getCore().getLifeCycleManager().restart();
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

        for (OnlinePatch patch : onlineVersion.getPatches()) {
            Managers.getManager(IPatchManager.class).registerOnlinePatch(patch);
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

            registerPatches(result, version);

            result.setJarFile(version.getJarFile());
            result.setName(version.getTitle());
            result.setInstalled(true);
        } catch (Exception e) {
            getLogger().error(e);
            result.setInstalled(false);
        }

        return result;
    }

    /**
     * Register the patches of the new installation.
     *
     * @param result  The new installation result.
     * @param version The installed version.
     */
    private static void registerPatches(InstallationResult result, OnlineVersion version) {
        if (!version.getPatches().isEmpty()) {
            for (OnlinePatch patch : version.getPatches()) {
                Managers.getManager(IPatchManager.class).registerOnlinePatch(patch);
            }

            result.setMustRestart();
        }
    }

    @Override
    public void update(ModuleContainer module, Version version) {
        OnlineVersion onlineVersion = versionsLoader.getOnlineVersion(version, module);

        if (onlineVersion.getCoreVersion().isGreaterThan(Managers.getCore().getCoreCurrentVersion())) {
            Managers.getManager(IViewManager.class).displayI18nText("modules.message.versionproblem");
        } else {
            //Download all files
            for (UpdateAction action : onlineVersion.getActions()) {
                action.execute();
            }

            Managers.getManager(IViewManager.class).displayI18nText("message.application.updated");

            Managers.getCore().getLifeCycleManager().restart();
        }
    }

    @Override
    public Collection<Version> getKernelVersions() {
        return getVersions(Managers.getCore());
    }

    @Override
    public void verifyingUpdate() {
        if (Managers.getCore().getConfiguration().verifyUpdateOnStartup()) {
            if (!isCurrentVersionUpToDate()) {
                proposeUpdateToUser("dialogs.propose.update", "dialogs.propose.update.title");
            }

            if (!isAModuleNotUpToDate()) {
                proposeUpdateToUser("dialogs.propose.module.update", "dialogs.propose.module.update.title");
            }
        }
    }

    /**
     * Propose an update to the user.
     *
     * @param dialogKey      The i18n key of the message.
     * @param dialogKeyTitle The i18n key of the title.
     */
    private static void proposeUpdateToUser(String dialogKey, String dialogKeyTitle) {
        boolean yes = Managers.getManager(IViewManager.class).askI18nUserForConfirmation(
                dialogKey, dialogKeyTitle);

        if (yes) {
            Managers.getManager(IViewManager.class).getViews().getModuleView().display();
        }
    }

    @Override
    public boolean isCurrentVersionUpToDate() {
        boolean upToDate = true;

        for (Version version : getKernelVersions()) {
            if (!Managers.getCore().getApplication().getVersion().isGreaterThan(version)) {
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

        for (ModuleContainer module : Managers.getManager(IModuleManager.class).getModules()) {
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
    public void updateToMostRecentVersion(ModuleContainer module) {
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
        getListeners().add(UpdatableListener.class, listener);
    }

    @Override
    public void removeUpdatableListener(UpdatableListener listener) {
        getListeners().remove(UpdatableListener.class, listener);
    }

    /**
     * Fire an updatable added event.
     */
    private static void fireUpdatableAdded() {
        UpdatableListener[] listeners = getListeners().getListeners(UpdatableListener.class);

        for (UpdatableListener l : listeners) {
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