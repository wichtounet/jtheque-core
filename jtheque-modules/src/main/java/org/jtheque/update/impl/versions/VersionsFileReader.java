package org.jtheque.update.impl.versions;

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
import org.jtheque.modules.able.IModuleDescription;
import org.jtheque.modules.able.Module;
import org.jtheque.update.able.Updatable;
import org.jtheque.update.impl.actions.AbstractUpdateAction;
import org.jtheque.update.impl.actions.DeleteAction;
import org.jtheque.update.impl.actions.DownloadAction;
import org.jtheque.update.impl.actions.MoveAction;
import org.jtheque.update.impl.actions.UpdateAction;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.xml.utils.XMLException;
import org.jtheque.xml.utils.XMLReader;

import org.jdom.Element;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A reader for the versions file.
 *
 * @author Baptiste Wicht
 */
public final class VersionsFileReader {
    private final XMLReader reader = new XMLReader();
    private VersionsFile versionsFile;

    /**
     * Read a versions file and return it.
     *
     * @param object The URL of the file.
     * @return The versions file.
     */
    public VersionsFile read(Object object) {
        if (isModule(object)) {
            Module module = (Module) object;

            if (StringUtils.isNotEmpty(module.getUpdateUrl())) {
                return read(module.getUpdateUrl());
            }
        } else if (isUpdatable(object)) {
            Updatable updatable = (Updatable) object;

            return read(updatable.getVersionsFileURL());
        } else if (isCore(object)) {
            return read(ICore.VERSIONS_FILE_URL);
        } else if (object instanceof IModuleDescription) {
            return readURL(((IModuleDescription) object).getVersionsFileURL());
        }

        return null;
    }

    /**
     * Indicate if the object is a module or not.
     *
     * @param object The object to test.
     * @return true if the object is a module else false.
     */
    private static boolean isModule(Object object) {
        return object instanceof Module;
    }

    /**
     * Indicate if the object is an updatable or not.
     *
     * @param object The object to test.
     * @return true if the object is an updatable else false.
     */
    private static boolean isUpdatable(Object object) {
        return object instanceof Updatable;
    }

    /**
     * Indicate if the object is the core or not.
     *
     * @param object The object to test.
     * @return true if the object is the core else false.
     */
    private static boolean isCore(Object object) {
        return object instanceof ICore;
    }

    /**
     * Read the VersionsFile at the url.
     *
     * @param strUrl the url string value.
     * @return the read versions file.
     */
    private VersionsFile read(String strUrl) {
        try {
            return readVersionsFile(new URL(strUrl));
        } catch (MalformedURLException e) {
            LoggerFactory.getLogger(getClass()).error("Unable to read versions file", e);
        }

        return null;
    }

    /**
     * Read the the version's file at the specified URL.
     *
     * @param versionsFileURL The URL of the versions file.
     * @return The version's file or null if the URL is invalid.
     */
    public VersionsFile readURL(String versionsFileURL) {
        try {
            return readVersionsFile(new URL(versionsFileURL));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    /**
     * Read a versions file and return it.
     *
     * @param url The URL of the file.
     * @return The versions file.
     */
    private VersionsFile readVersionsFile(URL url) {
        versionsFile = new VersionsFile();
        List<OnlineVersion> onlineVersions = new ArrayList<OnlineVersion>(10);

        try {
            reader.openURL(url);

            readInstallVersion();

            for (Object currentNode : reader.getNodes("jt-version", reader.getRootElement())) {
                readOnlineVersion(currentNode, onlineVersions);
            }

            Collections.sort(onlineVersions);

            versionsFile.setVersions(onlineVersions);
        } catch (XMLException e) {
            LoggerFactory.getLogger(getClass()).error("Unable to read versions file", e);
        } finally {
            FileUtils.close(reader);
        }

        return versionsFile;
    }

    /**
     * Read the install version.
     *
     * @throws XMLException If an error occurs during the xml reading.
     */
    private void readInstallVersion() throws XMLException {
        InstallVersion installVersion = new InstallVersion();

        Object installVersionNode = reader.getNode("install", reader.getRootElement());

        readOnlineVersion(installVersionNode, installVersion);

        installVersion.setJarFile(reader.readString("jar-file", installVersionNode));
        installVersion.setTitle(reader.readString("title", installVersionNode));

        versionsFile.setInstallVersion(installVersion);
    }

    /**
     * Fill an online version.
     *
     * @param currentNode    The current node.
     * @param onlineVersions The list of all online versions.
     * @throws XMLException If an error occurs during the reading process.
     */
    private void readOnlineVersion(Object currentNode, Collection<OnlineVersion> onlineVersions) throws XMLException {
        OnlineVersion onlineVersion = new OnlineVersion();

        readOnlineVersion(currentNode, onlineVersion);

        onlineVersions.add(onlineVersion);
    }

    /**
     * Read the online version.
     *
     * @param currentNode   The node to read from.
     * @param onlineVersion The online version to fill.
     * @throws XMLException If an error occurs during the reading process.
     */
    private void readOnlineVersion(Object currentNode, OnlineVersion onlineVersion) throws XMLException {
        readVersionNumber(currentNode, onlineVersion);
        readCoreVersion(currentNode, onlineVersion);
        readActions(currentNode, onlineVersion);
    }

    /**
     * Read the version number.
     *
     * @param currentNode   The node to read from.
     * @param onlineVersion The online version to fill.
     * @throws XMLException If an error occurs during the reading process.
     */
    private void readVersionNumber(Object currentNode, OnlineVersion onlineVersion) throws XMLException {
        onlineVersion.setVersion(new Version(reader.readString("nom", currentNode)));
    }

    /**
     * Read the actions of the online version.
     *
     * @param currentNode   The node to read from.
     * @param onlineVersion The online version to fill.
     * @throws XMLException If an error occurs during the reading process.
     */
    private void readActions(Object currentNode, OnlineVersion onlineVersion) throws XMLException {
        Collection<Element> nodes = reader.getNodes("actions/*", currentNode);

        onlineVersion.setActions(new ArrayList<UpdateAction>(nodes.size()));

        for (Element node : nodes) {
            String name = node.getName();

            if ("add".equals(name)) {
                AbstractUpdateAction action = readDownloadAction(node);

                onlineVersion.getActions().add(action);
            } else if ("delete".equals(name)) {
                AbstractUpdateAction action = readDeleteAction(node);

                onlineVersion.getActions().add(action);
            } else if ("move".equals(name)) {
                AbstractUpdateAction action = readMoveAction(node);

                onlineVersion.getActions().add(action);
            }
        }
    }

    /**
     * Read the download action.
     *
     * @param node The node to read from.
     * @return the read DownloadAction.
     * @throws XMLException If an error occurs during the reading process.
     */
    private AbstractUpdateAction readDownloadAction(Object node) throws XMLException {
        DownloadAction action = new DownloadAction();

        action.setOs(reader.readString("@os", node));
        action.setFolder(reader.readString("folder", node));
        action.setFile(reader.readString("file", node));
        action.setUrl(reader.readString("url", node));

        return action;
    }

    /**
     * Read a delete action.
     *
     * @param node The node to read from.
     * @return The delete action.
     * @throws XMLException If an error occurs during the reading process.
     */
    private AbstractUpdateAction readDeleteAction(Object node) throws XMLException {
        AbstractUpdateAction action = new DeleteAction();

        action.setFolder(reader.readString("folder", node));
        action.setFile(reader.readString("file", node));

        return action;
    }

    /**
     * Read a move action.
     *
     * @param node The node to read from.
     * @return The move action.
     * @throws XMLException If an error occurs during the reading process.
     */
    private AbstractUpdateAction readMoveAction(Object node) throws XMLException {
        MoveAction action = new MoveAction();

        action.setSourceFile(reader.readString("src-file", node));
        action.setSourceFolder(reader.readString("src-folder", node));
        action.setFile(reader.readString("dest-file", node));
        action.setFolder(reader.readString("dest-folder", node));

        return action;
    }

    /**
     * Read the needed version of the core.
     *
     * @param currentNode   The node to read from.
     * @param onlineVersion The online version to fill.
     * @throws XMLException If an error occurs during the reading process.
     */
    private void readCoreVersion(Object currentNode, OnlineVersion onlineVersion) throws XMLException {
        if (StringUtils.isEmpty(reader.readString("core", currentNode))) {
            onlineVersion.setCoreVersion(ICore.VERSION);
        } else {
            onlineVersion.setCoreVersion(new Version(reader.readString("core", currentNode)));
        }
    }
}