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

import org.jtheque.core.able.Core;
import org.jtheque.core.able.Versionable;
import org.jtheque.core.utils.SystemProperty;
import org.jtheque.errors.able.Errors;
import org.jtheque.errors.able.ErrorService;
import org.jtheque.events.able.EventLevel;
import org.jtheque.events.able.Events;
import org.jtheque.events.able.EventService;
import org.jtheque.modules.able.ModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleState;
import org.jtheque.modules.impl.InstallationResult;
import org.jtheque.resources.able.IResourceService;
import org.jtheque.resources.impl.CoreVersion;
import org.jtheque.resources.impl.FileDescriptor;
import org.jtheque.resources.impl.ModuleVersion;
import org.jtheque.ui.able.UIUtils;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.update.impl.versions.IVersionsLoader;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.utils.io.WebUtils;

import org.osgi.framework.BundleException;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Manage the update of the application. This class can go on internet to verify if a more recent version of JTheque is
 * available and download one new version if there is one.
 * <p/>
 * This service manage also the updates of the modules.
 *
 * @author Baptiste Wicht
 */
public final class UpdateService implements IUpdateService {
    @Resource
    private Core core;

    @Resource
    private UIUtils uiUtils;

    @Resource
    private ModuleService moduleService;

    @Resource
    private EventService eventService;

    @Resource
    private ErrorService errorService;

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
        if (isDescriptorNotReachable(Core.DESCRIPTOR_FILE_URL)) {
            return;
        }

        CoreVersion onlineVersion = versionsLoader.getCoreVersion(versionToDownload);

        if (onlineVersion == null) {
            return;
        }

        applyCoreVersion(onlineVersion);

        core.getLifeCycle().restart();
    }

    @Override
    public InstallationResult install(String url) {
        InstallationResult result = new InstallationResult();

        if (WebUtils.isURLReachable(url)) {
            try {
                ModuleVersion moduleVersion = versionsLoader.getMostRecentModuleVersion(url);

                applyModuleVersion(moduleVersion);

                result.setJarFile(moduleVersion.getModuleFile());
                result.setInstalled(true);
            } catch (Exception e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
                result.setInstalled(false);
            }
        } else {
            addNotReachableError(url);

            eventService.addEvent(
                    Events.newEvent(EventLevel.ERROR, "System", "events.updates.network", EventService.CORE_EVENT_LOG));

            result.setInstalled(false);
        }

        return result;
    }

    /**
     * Add a not reachable error to the errors.
     *
     * @param url the not reachable URL.
     */
    private void addNotReachableError(String url) {
        if (WebUtils.isInternetReachable()) {
            errorService.addError(Errors.newI18nError(
                    "modules.updates.network.resource.title", ArrayUtils.EMPTY_ARRAY,
                    "modules.updates.network.resource", new Object[]{url}));
        } else {
            errorService.addError(Errors.newI18nError(
                    "modules.updates.network.internet.title", ArrayUtils.EMPTY_ARRAY,
                    "modules.updates.network.internet", new Object[]{url}));
        }
    }

    @Override
    public void update(Module module, Version version) {
        if (isDescriptorNotReachable(module.getDescriptorURL())) {
            return;
        }

        ModuleVersion onlineVersion = versionsLoader.getModuleVersion(version, module);

        if (onlineVersion == null) {
            return;
        }

        if (onlineVersion.getCoreVersion().isGreaterThan(Core.VERSION)) {
            uiUtils.displayI18nText("modules.message.versionproblem");
        } else {
            applyModuleVersion(onlineVersion);

            try {
                boolean restart = false;

                if (module.getState() == ModuleState.STARTED) {
                    moduleService.stopModule(module);

                    restart = true;
                }

                module.getBundle().update(FileUtils.asInputStream(
                        new File(core.getFolders().getModulesFolder(), onlineVersion.getModuleFile())));

                if (restart) {
                    moduleService.startModule(module);
                }
            } catch (BundleException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            } catch (FileNotFoundException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }

            uiUtils.displayI18nText("message.application.updated");
        }
    }

    /**
     * Indicate if the descriptor is not reachable.
     *
     * @param url The descriptor's url to test for reachability.
     *
     * @return true if the descriptor is not reachable else false.
     */
    private boolean isDescriptorNotReachable(String url) {
        if (WebUtils.isURLReachable(url)) {
            return false;
        }

        addNotReachableError(url);

        eventService.addEvent(
                Events.newEvent(EventLevel.ERROR, "System", "events.updates.network", EventService.CORE_EVENT_LOG));

        return true;
    }

    /**
     * Apply the module version.
     *
     * @param moduleVersion The module version to apply.
     */
    private void applyModuleVersion(ModuleVersion moduleVersion) {
        try {
            if (StringUtils.isNotEmpty(moduleVersion.getModuleFile())) {
                WebUtils.downloadFile(moduleVersion.getModuleURL(),
                        new File(core.getFolders().getModulesFolder(), moduleVersion.getModuleFile()).getAbsolutePath());
            }

            downloadResources(moduleVersion.getFiles());
            downloadResources(moduleVersion.getLibraries());
        } catch (FileException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
    }

    /**
     * Apply the given core version.
     *
     * @param coreVersion The core version to apply.
     */
    private void applyCoreVersion(CoreVersion coreVersion) {
        File bundlesFolder = new File(SystemProperty.USER_DIR.get(), "bundles");

        Set<File> currentBundles = ArrayUtils.asSet(bundlesFolder.listFiles());

        //Make the diffs and download the new bundles
        for (FileDescriptor newBundle : coreVersion.getBundles()) {
            File f = new File(bundlesFolder, newBundle.getName());

            if (currentBundles.contains(f)) {
                currentBundles.remove(f);
            } else {
                try {
                    WebUtils.downloadFile(newBundle.getUrl(), f.getAbsolutePath());
                } catch (FileException e) {
                    LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
                }
            }
        }

        //Delete the remaining files
        for (File f : currentBundles) {
            FileUtils.delete(f);
        }
    }

    /**
     * Download the resources.
     *
     * @param resources The resources to download.
     */
    private void downloadResources(Iterable<FileDescriptor> resources) {
        for (FileDescriptor descriptor : resources) {
            if (resourceService.isNotInstalled(descriptor.getName(), descriptor.getVersion())) {
                resourceService.downloadResource(descriptor.getUrl(), descriptor.getVersion());
            }
        }
    }

    @Override
    public Collection<Version> getKernelVersions() {
        if (isDescriptorNotReachable(Core.DESCRIPTOR_FILE_URL)) {
            return CollectionUtils.emptyList();
        }

        return versionsLoader.getCoreVersions();
    }

    @Override
    public List<String> getPossibleUpdates() {
        if (!WebUtils.isInternetReachable()) {
            errorService.addError(Errors.newI18nError("internet.necessary"));

            return CollectionUtils.emptyList();
        }

        List<String> messages = CollectionUtils.newList(2);

        if (isAModuleNotUpToDate()) {
            messages.add("dialogs.propose.module.update");
        }

        if (!isCurrentVersionUpToDate()) {
            messages.add("dialogs.propose.update");
        }

        return messages;
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
    public boolean isCurrentVersionUpToDate() {
        if (isDescriptorNotReachable(Core.DESCRIPTOR_FILE_URL)) {
            return true;
        }

        return isUpToDate(Core.VERSION, versionsLoader.getCoreVersions());
    }

    @Override
    public boolean isUpToDate(Module object) {
        if (isDescriptorNotReachable(object.getDescriptorURL())) {
            return true;
        }

        return isUpToDate(object.getVersion(), versionsLoader.getVersions(object));

    }

    /**
     * Indicate if the given version is up to date with the available versions.
     *
     * @param version  The version to test.
     * @param versions The available versions.
     *
     * @return true if the version is up to date else false.
     */
    private static boolean isUpToDate(Version version, Iterable<Version> versions) {
        for (Version v : versions) {
            if (v.isGreaterThan(version)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void updateToMostRecentVersion(Module module) {
        if (isDescriptorNotReachable(module.getDescriptorURL())) {
            return;
        }

        Version maxVersion = null;

        for (Version version : versionsLoader.getVersions(module)) {
            if (maxVersion == null || version.isGreaterThan(maxVersion)) {
                maxVersion = version;
            }
        }

        update(module, maxVersion);
    }

    @Override
    public Version getMostRecentCoreVersion() {
        if (isDescriptorNotReachable(Core.DESCRIPTOR_FILE_URL)) {
            return null;
        }

        return versionsLoader.getMostRecentCoreVersion();
    }

    @Override
    public Version getMostRecentVersion(Versionable object) {
        if (isDescriptorNotReachable(object.getDescriptorURL())) {
            return null;
        }

        return versionsLoader.getMostRecentVersion(object);
    }

    @Override
    public Collection<Version> getVersions(Module object) {
        if (isDescriptorNotReachable(object.getDescriptorURL())) {
            return null;
        }

        return versionsLoader.getVersions(object);
    }
}