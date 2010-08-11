package org.jtheque.ui.components;

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

import org.jtheque.i18n.LanguageService;
import org.jtheque.i18n.Internationalizable;
import org.jtheque.utils.io.SimpleFilter;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * An abstract file chooser.
 *
 * @author Baptiste Wicht
 */
public abstract class FileChooser extends JPanel implements Internationalizable {
    private boolean directoriesOnly;
    private String key;
    private SimpleFilter filter;

    /**
     * Indicate if the search is directory only.
     *
     * @return true if the search is directories only otherwise false.
     */
    protected boolean isDirectoriesOnly() {
        return directoriesOnly;
    }

    /**
     * Return the text key.
     *
     * @return The text key.
     */
    protected String getKey() {
        return key;
    }

    /**
     * Return the file filter.  
     *
     * @return The file filter.
     */
    protected SimpleFilter getFilter() {
        return filter;
    }

    /**
     * Set the text key for the label.
     *
     * @param key The internationalization key.
     */
    public void setTextKey(String key) {
        this.key = key;
    }

    /**
     * Set if the chooser search only for directories or not.
     */
    public void setDirectoriesOnly() {
        directoriesOnly = true;
    }

    /**
     * Set that the chooser search only for files.
     */
    public void setFilesOnly() {
        directoriesOnly = false;
    }

    @Override
    public void refreshText(LanguageService languageService) {
        if (key != null) {
            setText(languageService.getMessage(key));
        }
    }

    /**
     * Set the text of the label.
     *
     * @param text The text of the label.
     */
    public abstract void setText(String text);

    /**
     * Return the path to the file.
     *
     * @return The path to the file.
     */
    public abstract String getFilePath();

    /**
     * Set the path to the file.
     *
     * @param path The path to the file.
     */
    public abstract void setFilePath(String path);

    /**
     * Set the file filter.
     *
     * @param filter The file filter.
     */
    public void setFileFilter(SimpleFilter filter) {
        this.filter = filter;
    }

    /**
     * Return the text field of the file chooser.
     *
     * @return The text field of the file chooser.
     */
    public abstract JTextField getTextField();
}