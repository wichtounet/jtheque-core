package org.jtheque.core;

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

import org.jtheque.core.application.Application;
import org.jtheque.utils.bean.Version;

import java.util.Collection;

/**
 * A core specification.
 *
 * @author Baptiste Wicht
 */
public interface Core {
    Version VERSION = Version.get("2.1.0");

    String DESCRIPTOR_FILE_URL = "http://jtheque.baptiste-wicht.com/files/descriptors/jtheque-core.xml";
    String HELP_URL = "http://github.com/wichtounet/jtheque-core/";
    String BUG_TRACKER_URL = "http://github.com/wichtounet/jtheque-core/issues/";

    String WINDOW_ICON = "jtheque-core-window-icon";

    /**
     * Launch the JTheque Core.
     *
     * @param application The application to launch.
     */
    void launchApplication(Application application);

    /**
     * Return the messages file url for the core.
     *
     * @return The URL to the messages files of the core.
     */
    String getCoreMessageFileURL();

    /**
     * Return the folders of the application.
     *
     * @return The folders of the application.
     */
    FoldersContainer getFolders();

    /**
     * Return the files of the application.
     *
     * @return The files of the application.
     */
    FilesContainer getFiles();

    /**
     * Add an internationalized credits message.
     *
     * @param key The internationalized key.
     */
    void addCreditsMessage(String key);

    /**
     * Return all the credits message.
     *
     * @return A List containing all the credits message.
     */
    Collection<String> getCreditsMessage();

    /**
     * Indicate if the current version of the core is compatible with an other version.
     *
     * @param version The version to test the compatibility with.
     *
     * @return true if the current version is compatible with the other version.
     */
    boolean isNotCompatibleWith(Version version);

    /**
     * Return the current application.
     *
     * @return The current application.
     */
    Application getApplication();

    /**
     * Return the configuration of the core.
     *
     * @return The configuration of the core.
     */
    CoreConfiguration getConfiguration();

    /**
     * Return all the supported languages.
     *
     * @return A List containing all the supported languages.
     */
    Collection<String> getPossibleLanguages();

    /**
     * Add application listener.
     *
     * @param listener The application listener.
     */
    void addApplicationListener(ApplicationListener listener);

    /**
     * Remove the application title listener.
     *
     * @param listener The application listener.
     */
    void removeApplicationListener(ApplicationListener listener);
}