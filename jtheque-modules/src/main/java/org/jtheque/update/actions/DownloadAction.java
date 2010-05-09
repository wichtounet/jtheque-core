package org.jtheque.update.actions;

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

import org.jtheque.utils.OSUtils;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.FileUtils;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * An org.jtheque.update action that download a file.
 *
 * @author Baptiste Wicht
 */
public final class DownloadAction extends AbstractUpdateAction {
    private String url;
    private String os;

    @Override
    public void execute() {
        if (canBeExecutedOnThisOS()) {
            try {
                if(!new File(getDestination()).delete()){
                    setFile("dljt_" + getFile());
                }

                FileUtils.downloadFile(url, getDestination());
            } catch (FileException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }
        }
    }

    /**
     * Test if this action can be executed in this operating system or not.
     *
     * @return <code>true</code> if this action can be executed in this operating system else <code>false</code>.
     */
    private boolean canBeExecutedOnThisOS() {
        if (StringUtils.isNotEmpty(os)) {
            if ("linux".equalsIgnoreCase(os)) {
                return OSUtils.isLinux();
            } else if ("mac".equalsIgnoreCase(os)) {
                return OSUtils.isMac();
            } else if ("windows".equalsIgnoreCase(os)) {
                return OSUtils.isWindows();
            }
        }

        return true;
    }

    /**
     * Set the URL of the file to download.
     *
     * @param url The URL.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Set the OS constraint of the action.
     *
     * @param os The OS constraint of the action.
     */
    public void setOs(String os) {
        this.os = os;
    }
}