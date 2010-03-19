package org.jtheque.update.actions;

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

import org.jtheque.errors.InternationalizedError;
import org.jtheque.i18n.ILanguageService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.update.UpdateServices;
import org.jtheque.utils.OSUtils;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.FileUtils;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * An update action that download a file.
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

                if(e.getCause() instanceof FileNotFoundException){
                    UpdateServices.get(IUIUtils.class).getDelegate().displayError(
                            new InternationalizedError(UpdateServices.get(ILanguageService.class), "error.update.download", e.getMessage()));
                }
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