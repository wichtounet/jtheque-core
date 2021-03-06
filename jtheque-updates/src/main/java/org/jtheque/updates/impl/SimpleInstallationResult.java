package org.jtheque.updates.impl;

import org.jtheque.updates.InstallationResult;
import org.jtheque.utils.annotations.Immutable;

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
 * The result of a module installation.
 *
 * @author Baptiste Wicht
 */
@Immutable
final class SimpleInstallationResult implements InstallationResult {
    private final boolean installed;
    private final String jarFile;

    /**
     * Construct a new SimpleInstallationResult.
     *
     * @param installed Indicate if the installation has succeeded.
     * @param jarFile   The name of the JAR file.
     */
    SimpleInstallationResult(boolean installed, String jarFile) {
        super();

        this.installed = installed;
        this.jarFile = jarFile;
    }

    @Override
    public boolean isInstalled() {
        return installed;
    }

    @Override
    public String getJarFile() {
        return jarFile;
    }
}