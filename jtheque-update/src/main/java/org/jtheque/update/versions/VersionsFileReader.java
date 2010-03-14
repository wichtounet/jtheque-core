package org.jtheque.update.versions;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jdom.Element;
import org.jtheque.core.ICore;
import org.jtheque.io.XMLReader;
import org.jtheque.io.XMLException;
import org.jtheque.logging.ILoggingManager;
import org.jtheque.modules.able.Module;
import org.jtheque.update.Updatable;
import org.jtheque.update.UpdateServices;
import org.jtheque.update.actions.AbstractUpdateAction;
import org.jtheque.update.actions.DeleteAction;
import org.jtheque.update.actions.DownloadAction;
import org.jtheque.update.actions.MoveAction;
import org.jtheque.update.actions.UpdateAction;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.io.FileUtils;

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
            UpdateServices.get(ILoggingManager.class).getLogger(getClass()).error(e, "Unable to read versions file");
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
            UpdateServices.get(ILoggingManager.class).getLogger(getClass()).error(e, "Unable to read versions file");
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