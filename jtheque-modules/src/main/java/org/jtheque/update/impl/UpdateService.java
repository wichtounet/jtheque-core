package org.jtheque.update.impl;

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

import org.jtheque.core.able.ICore;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.impl.InstallationResult;
import org.jtheque.states.able.IStateService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.update.impl.actions.UpdateAction;
import org.jtheque.update.impl.versions.IVersionsLoader;
import org.jtheque.update.impl.versions.InstallVersion;
import org.jtheque.update.impl.versions.OnlineVersion;
import org.jtheque.utils.bean.Version;

import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Manage the org.jtheque.update of the application. This class can go on internet to verify if a more recent version of
 * JTheque is available and download one new version if there is one.
 *
 * @author Baptiste Wicht
 */
public final class UpdateService implements IUpdateService {
    @Resource
    private ICore core;

    @Resource
    private IUIUtils uiUtils;

    @Resource
    private IModuleService moduleService;

    private final IVersionsLoader versionsLoader;

    /**
     * Create a new UpdateService.
     *
     * @param stateService   The state service.
     * @param versionsLoader The versions loader.
     */
    public UpdateService(IStateService stateService, IVersionsLoader versionsLoader) {
        super();

        this.versionsLoader = versionsLoader;
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
    public Version getMostRecentCoreVersion() {
        return getMostRecentVersion(core);
    }
}