package org.jtheque.resources.impl;

import org.jtheque.core.utils.SystemProperty;
import org.jtheque.resources.able.IResource;
import org.jtheque.resources.able.IResourceService;
import org.jtheque.states.able.IStateService;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.FileUtils;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.slf4j.LoggerFactory;
import org.springframework.osgi.context.BundleContextAware;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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

public class ResourceService implements IResourceService, BundleContextAware {
    private final List<IResource> resources = new ArrayList<IResource>(10);
    private final Map<String, ResourceDescriptor> descriptorCache = new HashMap<String, ResourceDescriptor>(5);
    private final ResourceState resourceState;
    
    private BundleContext bundleContext;

    public ResourceService(IStateService stateService) {
        super();

        resourceState = stateService.getState(new ResourceState());

        resources.addAll(resourceState.getResources());
    }

    @Override
    public void addResource(Resource resource){
        resources.add(resource);
    }

    @Override
    public List<IResource> getResources(){
        return resources;
    }

    @Override
    public List<Version> getVersions(String resourceName){
        List<Version> versions = new ArrayList<Version>(3);

        for(IResource resource : resources){
            if(resource.getId().equals(resourceName)){
                versions.add(resource.getVersion());
            }
        }

        return versions;
    }

    @Override
    public boolean exists(String resourceName){
        for (IResource resource : resources) {
            if (resource.getId().equals(resourceName)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public IResource getResource(String id, String version) {
        Version searched = new Version(version);

        for (IResource resource : resources) {
            if (resource.getId().equals(id) && resource.getVersion().equals(searched)) {
                return resource;
            }
        }

        return null;
    }

    @Override
    public IResource downloadResource(String url, String version) {
        if(!descriptorCache.containsKey(url)){
            descriptorCache.put(url, new ResourceDescriptorReader().readURL(url));
        }

        ResourceDescriptor descriptor = descriptorCache.get(url);

        if(descriptor == null){
            return null;
        }

        IResource cachedResource = getResource(descriptor.getId(), version);
        if(cachedResource != null){
            return cachedResource;
        }

        for(ResourceVersion resourceVersion : descriptor.getVersions()){
            if(resourceVersion.getVersion().equals(new Version(version))){
                Resource resource = new Resource(descriptor.getId());

                resource.setVersion(resourceVersion.getVersion());

                File resourceFolder = getResourceFolder(resource);

                for(FileDescriptor file : resourceVersion.getFiles()){
                    File filePath = new File(resourceFolder, file.getName());

                    try {
                        FileUtils.downloadFile(file.getUrl(), filePath.getAbsolutePath());
                    } catch (FileException e) {
                        LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
                    }

                    resource.getFiles().add(file.getName());
                }

                for (FileDescriptor library : resourceVersion.getLibraries()) {
                    File filePath = new File(resourceFolder, library.getName());

                    try {
                        FileUtils.downloadFile(library.getUrl(), filePath.getAbsolutePath());
                    } catch (FileException e) {
                        LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
                    }

                    resource.getFiles().add(library.getName());
                }

                resources.add(resource);
                resourceState.addResource(resource);

                return resource;
            }
        }

        return null;
    }

    @Override
    public void installResource(IResource resource) {
        for(Library library : resource.getLibraries()){
            File folder = getResourceFolder(resource);

            try {
                Bundle bundle = bundleContext.installBundle("file:" + folder.getAbsolutePath() + "/" + library.getId());

                library.setBundle(bundle);
            } catch (BundleException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    private static File getResourceFolder(IResource resource) {
        return new File(SystemProperty.USER_DIR.get(), resource.getId() + '/' + resource.getVersion());
    }
}