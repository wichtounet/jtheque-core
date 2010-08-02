package org.jtheque.resources.impl;

import org.jtheque.core.utils.SystemProperty;
import org.jtheque.errors.able.ErrorService;
import org.jtheque.errors.able.Errors;
import org.jtheque.events.able.EventLevel;
import org.jtheque.events.able.EventService;
import org.jtheque.events.able.Events;
import org.jtheque.resources.able.IResource;
import org.jtheque.resources.able.IResourceService;
import org.jtheque.resources.able.SimpleResource;
import org.jtheque.states.able.StateService;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.WebUtils;
import org.jtheque.utils.ui.SwingUtils;

import org.osgi.framework.BundleContext;
import org.slf4j.LoggerFactory;
import org.springframework.osgi.context.BundleContextAware;

import java.io.File;
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
public class ResourceService implements IResourceService, BundleContextAware {
    private final List<IResource> resources = CollectionUtils.newList();
    private final Map<String, ResourceDescriptor> descriptorCache = CollectionUtils.newHashMap(5);
    private final ResourceState resourceState;

    private BundleContext bundleContext;
    private final ErrorService errorService;
    private final EventService eventService;

    /**
     * Construct a new Resource Service.
     *
     * @param stateService The state service.
     * @param errorService The error service.
     * @param eventService The event service.
     */
    public ResourceService(StateService stateService, ErrorService errorService, EventService eventService) {
        super();

        this.errorService = errorService;
        this.eventService = eventService;

        resourceState = stateService.getState(new ResourceState());

        resources.addAll(resourceState.getResources());
    }

    @Override
    public void addResource(Resource resource) {
        resources.add(resource);
    }

    @Override
    public List<IResource> getResources() {
        return resources;
    }

    @Override
    public List<Version> getVersions(String resourceName) {
        List<Version> versions = CollectionUtils.newList(3);

        for (IResource resource : resources) {
            if (resource.getId().equals(resourceName)) {
                versions.add(resource.getVersion());
            }
        }

        return versions;
    }

    @Override
    public boolean exists(String resourceName) {
        for (IResource resource : resources) {
            if (resource.getId().equals(resourceName)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public IResource getResource(String id, Version version) {
        for (IResource resource : resources) {
            if (resource.getId().equals(id) && resource.getVersion().equals(version)) {
                return resource;
            }
        }

        return null;
    }

    @Override
    public IResource downloadResource(String url, Version version) {
        SwingUtils.assertNotEDT("downloadResource(String, Version)");

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

            return null;
        }

        if (!descriptorCache.containsKey(url)) {
            descriptorCache.put(url, DescriptorReader.readResourceDescriptor(url));
        }

        ResourceDescriptor descriptor = descriptorCache.get(url);

        if (descriptor == null) {
            return null;
        }

        IResource cachedResource = getResource(descriptor.getId(), version);
        if (cachedResource != null) {
            return cachedResource;
        }

        for (ResourceVersion resourceVersion : descriptor.getVersions()) {
            if (resourceVersion.getVersion().equals(version)) {
                return downloadResource(url, descriptor, resourceVersion);
            }
        }

        return null;
    }

    /**
     * Download the given resource.
     *
     * @param url The url of the resource.
     * @param descriptor The descriptor of the resource.
     * @param resourceVersion The version to download from.
     *
     * @return The downloaded resource.
     */
    private IResource downloadResource(String url, AbstractDescriptor descriptor, ResourceVersion resourceVersion) {
        Resource resource = new Resource(descriptor.getId());

        resource.setVersion(resourceVersion.getVersion());
        resource.setUrl(url);

        File resourceFolder = getResourceFolder(resource);
        resourceFolder.mkdirs();

        for (FileDescriptor file : resourceVersion.getFiles()) {
            downloadFile(resourceFolder, file);

            resource.getResources().add(new FileResource(file.getName(), resourceFolder));
        }

        for (FileDescriptor library : resourceVersion.getLibraries()) {
            downloadFile(resourceFolder, library);

            resource.getResources().add(new LibraryResource(library.getName(), resourceFolder));
        }

        resources.add(resource);
        resourceState.addResource(resource);

        return resource;
    }

    /**
     * Download the file descriptor.
     *
     * @param resourceFolder The folder to put the resource into.
     * @param fileDescriptor The file descriptor of the resource.
     */
    private void downloadFile(File resourceFolder, FileDescriptor fileDescriptor) {
        File filePath = new File(resourceFolder, fileDescriptor.getName());

        try {
            WebUtils.downloadFile(fileDescriptor.getUrl(), filePath.getAbsolutePath());
        } catch (FileException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
    }

    @Override
    public void installResource(IResource resource) {
        SwingUtils.assertNotEDT("installResource(IResource)");

        for (SimpleResource r : resource.getResources()) {
            r.install(bundleContext);
        }
    }

    @Override
    public boolean isNotInstalled(String id, Version version) {
        return getResource(id, version) == null;
    }

    @Override
    public IResource getOrDownloadResource(String id, Version version, String url) {
        IResource resource = getResource(id, version);

        if (resource == null) {
            resource = downloadResource(url, version);
        }

        return resource;
    }

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    /**
     * Return the resource folder for the given resource.
     *
     * @param resource The resource to get the folder for.
     *
     * @return The folder. 
     */
    private static File getResourceFolder(IResource resource) {
        return new File(SystemProperty.USER_DIR.get(), "resources/" + resource.getId() + '/' + resource.getVersion());
    }
}