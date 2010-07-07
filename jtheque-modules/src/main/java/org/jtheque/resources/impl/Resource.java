package org.jtheque.resources.impl;

import org.jtheque.resources.able.IResource;
import org.jtheque.utils.bean.Version;

import java.util.ArrayList;
import java.util.List;

/**
 * A resource implementation.
 *
 * @author Baptiste Wicht
 */
public class Resource implements IResource {
    private final String id;
    private final List<String> files;
    private final List<Library> libraries;

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

        files = new ArrayList<String>(5);
        libraries = new ArrayList<Library>(5);
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * Add a file to the resource.
     *
     * @param name The name of the file.
     */
    public void addFile(String name) {
        files.add(name);
    }

    /**
     * Add a library to the resource.
     *
     * @param library The library to add.
     */
    public void addLibrary(Library library) {
        libraries.add(library);
    }

    @Override
    public List<String> getFiles() {
        return files;
    }

    @Override
    public List<Library> getLibraries() {
        return libraries;
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