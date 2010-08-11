package org.jtheque.resources.impl;

import org.jtheque.errors.ErrorService;
import org.jtheque.errors.Errors;
import org.jtheque.events.EventLevel;
import org.jtheque.events.EventService;
import org.jtheque.events.Events;
import org.jtheque.resources.Resource;
import org.jtheque.resources.ResourceService;
import org.jtheque.states.StateService;
import org.jtheque.utils.SystemProperty;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.WebUtils;
import org.jtheque.utils.ui.SwingUtils;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.slf4j.LoggerFactory;
import org.springframework.osgi.context.BundleContextAware;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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

/**
 * A resource service implementation.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public final class ResourceServiceImpl implements ResourceService, BundleContextAware {
    private final Map<String, ResourceDescriptor> descriptorCache = CollectionUtils.newHashMap(5);
    private final Map<String, Bundle> installedBundles = CollectionUtils.newHashMap(20);

    private final ResourceState resourceState;

    @javax.annotation.Resource
    private ErrorService errorService;

    @javax.annotation.Resource
    private EventService eventService;

    private BundleContext bundleContext;

    /**
     * Construct a new Resource Service.
     *
     * @param stateService The state service.
     */
    public ResourceServiceImpl(StateService stateService) {
        super();

        resourceState = stateService.getState(new ResourceState());
    }

    @Override
    public void addResource(Resource resource) {
        resourceState.addResource(resource);
    }

    @Override
    public Collection<Resource> getResources() {
        return CollectionUtils.protect(resourceState.getResourceSets());
    }

    @Override
    public List<Version> getVersions(String id) {
        List<Version> versions = CollectionUtils.newList(3);

        for (Resource resource : resourceState.getResourceSets()) {
            if (resource.getId().equals(id)) {
                versions.add(resource.getVersion());
            }
        }

        return versions;
    }

    @Override
    public boolean exists(String id) {
        for (Resource resource : resourceState.getResourceSets()) {
            if (resource.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Resource getResource(String id, Version version) {
        for (Resource resource : resourceState.getResourceSets()) {
            if (resource.getId().equals(id) && resource.getVersion().equals(version)) {
                return resource;
            }
        }

        return null;
    }

    @Override
    public Resource getOrDownloadResource(String id, Version version, String url) {
        Resource resource = getResource(id, version);

        if (resource == null) {
            resource = downloadResource(url, version);
            
            resourceState.addResource(resource);
        }

        return resource;
    }

    private Resource downloadResource(String url, Version version) {
        SwingUtils.assertNotEDT("downloadResource(String, Version)");

        if (notReachable(url)) {
            return null;
        }

        if (!descriptorCache.containsKey(url)) {
            descriptorCache.put(url, ResourceDescriptorReader.readResourceDescriptor(url));
        }

        ResourceDescriptor descriptor = descriptorCache.get(url);

        if (descriptor == null) {
            return null;
        }

        Resource cachedResource = getResource(descriptor.getId(), version);

        if (cachedResource != null) {
            return cachedResource;
        }

        for (ResourceVersion resourceVersion : descriptor.getVersions()) {
            if (resourceVersion.getVersion().equals(version)) {
                return downloadResource(descriptor, resourceVersion);
            }
        }

        return null;
    }

    /**
     * Verify that the URL is reachable.
     *
     * @param url The URL to test for reachability.
     *
     * @return {@code true} if the URL is not reachable otherwise {@code false}
     */
    private boolean notReachable(String url) {
        if (!WebUtils.isURLReachable(url)) {
            if (WebUtils.isInternetReachable()) {
                errorService.addError(Errors.newI18nError(
                        "modules.resources.network.resource.title", ArrayUtils.EMPTY_ARRAY,
                        "modules.resources.network.resource", new Object[]{url}));
            } else {
                errorService.addError(Errors.newI18nError(
                        "modules.resources.network.internet.title", ArrayUtils.EMPTY_ARRAY,
                        "modules.resources.network.internet", new Object[]{url}));
            }

            eventService.addEvent(
                    Events.newEvent(EventLevel.ERROR, "System", "events.resources.network", EventService.CORE_EVENT_LOG));

            return true;
        }

        return false;
    }

    /**
     * Download the given resource.
     *
     * @param descriptor      The descriptor of the resource.
     * @param resourceVersion The version to download from.
     *
     * @return The downloaded resource.
     */
    private static Resource downloadResource(AbstractDescriptor descriptor, ResourceVersion resourceVersion) {
        File resourceFolder = getResourceFolder(descriptor.getId(), resourceVersion.getVersion());

        downloadFile(resourceFolder, resourceVersion.getFile(), resourceVersion.getUrl());

        return new ResourceImpl(
                descriptor.getId(),
                resourceVersion.getVersion(),
                resourceVersion.getUrl(),
                resourceVersion.getFile(),
                resourceVersion.isLibrary());
    }

    private static void downloadFile(File resourceFolder, String fileName, String url) {
        File filePath = new File(resourceFolder, fileName);

        try {
            WebUtils.downloadFile(url, filePath.getAbsolutePath());
        } catch (FileException e) {
            LoggerFactory.getLogger(ResourceServiceImpl.class).error(e.getMessage(), e);
        }
    }

    @Override
    public void installResource(Resource resource) {
        SwingUtils.assertNotEDT("installResource(Resource)");

        if (resource.isLibrary()) {
            if (!installedBundles.containsKey(resource.getId())) {
                try {
                    Bundle bundle = bundleContext.installBundle("file:" +
                            getResourceFolder(resource.getId(), resource.getVersion()).getAbsolutePath() +
                            '/' + resource.getFile());

                    installedBundles.put(resource.getId(), bundle);
                } catch (BundleException e) {
                    LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public boolean isNotInstalled(String id, Version version) {
        return getResource(id, version) == null;
    }

    /**
     * Return the resource folder for the given resource.
     *
     * @param id      The id of the resource.
     * @param version The version of the resource.
     *
     * @return The folder of the resource.
     */
    private static File getResourceFolder(String id, Version version) {
        File file = new File(SystemProperty.USER_DIR.get(), "resources/" + id + '/' + version);

        if (!file.mkdirs()) {
            LoggerFactory.getLogger(ResourceServiceImpl.class).error("Unable to create the resource folder {}", file.getAbsolutePath());
        }

        return file;
    }

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }
}