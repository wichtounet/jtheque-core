package org.jtheque.ui.able.components;

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

import org.jtheque.utils.io.SimpleFilter;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Created by IntelliJ IDEA. User: wichtounet Date: Jul 13, 2010 Time: 2:27:49 PM To change this template use File |
 * Settings | File Templates.
 */
public abstract class FileChooser extends JPanel {
    /**
     * Set the text of the label.
     *
     * @param text The text of the label.
     */
    public abstract void setText(String text);

    /**
     * Set the text key for the label.
     *
     * @param key The internationalization key.
     */
    public abstract void setTextKey(String key);

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
    public abstract void setFileFilter(SimpleFilter filter);

    /**
     * Set if the chooser search only for directories or not.
     */
    public abstract void setDirectoriesOnly();

    /**
     * Set that the chooser search only for files.
     */
    public abstract void setFilesOnly();

    /**
     * Return the text field of the file chooser.
     *
     * @return The text field of the file chooser.
     */
    public abstract JTextField getTextField();
}