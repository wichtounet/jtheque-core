package org.jtheque.resources.impl;

import org.jtheque.resources.able.IResource;
import org.jtheque.utils.bean.Version;

import java.util.ArrayList;
import java.util.List;

public class Resource implements IResource {
    private final String id;
    private final List<String> files;
    private final List<Library> libraries;

    private Version version;
    private String url;
    private boolean installed;

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

    public void addFile(String text) {
        files.add(text);
    }

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

    public void setVersion(Version version) {
        this.version = version;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean isInstalled() {
        return installed;
    }

    public void setInstalled(boolean installed) {
        this.installed = installed;
    }
}