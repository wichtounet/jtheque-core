package org.jtheque.resources.impl;

import org.jtheque.resources.able.IResource;
import org.jtheque.resources.able.SimpleResource;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.List;

/**
 * A resource implementation.
 *
 * @author Baptiste Wicht
 */
public class Resource implements IResource {
    private final String id;
    private final List<SimpleResource> resources = CollectionUtils.newList(10);

    private Version version;
    private String url;
    private boolean installed;

    /**
     * Construct a new Resource.
     *
     * @param id The id of the resource.
     */
    public Resource(String id) {
        super();

        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * Add a simple resource to the resource.
     *
     * @param simpleResource The simple resource to add. 
     */
    public void addSimpleResource(SimpleResource simpleResource) {
        resources.add(simpleResource);
    }

    @Override
    public List<SimpleResource> getResources() {
        return resources;
    }

    @Override
    public Version getVersion() {
        return version;
    }

    /**
     * Set the version of the of the resource.
     *
     * @param version The version of the resource. 
     */
    public void setVersion(Version version) {
        this.version = version;
    }

    @Override
    public String getUrl() {
        return url;
    }

    /**
     * Set the URL of the resource.
     *
     * @param url The url of the resource. 
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean isInstalled() {
        return installed;
    }

    /**
     * Set the installed status of the resource.
     *
     * @param installed A boolean tag indicating if the resource is installed or not. 
     */
    public void setInstalled(boolean installed) {
        this.installed = installed;
    }
}