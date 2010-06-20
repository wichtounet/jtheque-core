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
import org.jtheque.core.able.Versionable;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.impl.InstallationResult;
import org.jtheque.resources.able.IResourceService;
import org.jtheque.resources.impl.FileDescriptor;
import org.jtheque.resources.impl.ModuleVersion;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.update.impl.versions.IVersionsLoader;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.FileUtils;

import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Manage the update of the application. This class can go on internet to verify if a more recent version of
 * JTheque is available and download one new version if there is one.
 *
 * This service manage also the updates of the modules. 
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

    @Resource
    private IResourceService resourceService;

    private final IVersionsLoader versionsLoader;

    /**
     * Create a new UpdateService.
     *
     * @param versionsLoader The versions loader.
     */
    public UpdateService(IVersionsLoader versionsLoader) {
        super();

        this.versionsLoader = versionsLoader;
    }

    @Override
    public void updateCore(Version versionToDownload) {
        update(core, versionToDownload);
    }

    @Override
    public InstallationResult install(String url) {
        InstallationResult result = new InstallationResult();

        try {
            ModuleVersion moduleVersion = versionsLoader.getMostRecentModuleVersion(url);

            applyModuleVersion(moduleVersion);

            result.setJarFile(moduleVersion.getModuleFile());
            result.setInstalled(true);
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            result.setInstalled(false);
        }

        return result;
    }

    @Override
    public void update(Versionable object, Version version) {
        ModuleVersion onlineVersion = versionsLoader.getModuleVersion(version, object);

        if (onlineVersion == null) {
            return;
        }

        if (onlineVersion.getCoreVersion().isGreaterThan(ICore.VERSION)) {
            uiUtils.displayI18nText("modules.message.versionproblem");
        } else {
            applyModuleVersion(onlineVersion);

            uiUtils.displayI18nText("message.application.updated");
        }
    }

    private void applyModuleVersion(ModuleVersion moduleVersion) {
        try {
            if(StringUtils.isNotEmpty(moduleVersion.getModuleFile())){
                FileUtils.downloadFile(moduleVersion.getModuleURL(),
                        new File(core.getFolders().getModulesFolder(), moduleVersion.getModuleFile()).getAbsolutePath());
            }
            
            downloadResources(moduleVersion.getFiles());
            downloadResources(moduleVersion.getLibraries());
        } catch (FileException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
    }

    private void downloadResources(Iterable<FileDescriptor> resources) {
        for (FileDescriptor descriptor : resources) {
            if (!resourceService.isInstalled(descriptor.getName(), descriptor.getVersion())) {
                resourceService.downloadResource(descriptor.getUrl(), descriptor.getVersion());
            }
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
        return isUpToDate(core);
    }

    /**
     * Indicate if all modules are up to date.
     *
     * @return true if all modules are up to date else false.
     */
    private boolean isAModuleNotUpToDate() {
        for (Module module : moduleService.getModules()) {
            if (!isUpToDate(module)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isUpToDate(Versionable object) {
        for (Version version : versionsLoader.getVersions(object)) {
            if (version.isGreaterThan(object.getVersion())) {
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
    public Version getMostRecentVersion(Versionable object) {
        return versionsLoader.getMostRecentVersion(object);
    }

    @Override
    public Collection<Version> getVersions(Versionable object) {
        return versionsLoader.getVersions(object);
    }

    @Override
    public Version getMostRecentCoreVersion() {
        return getMostRecentVersion(core);
    }
}