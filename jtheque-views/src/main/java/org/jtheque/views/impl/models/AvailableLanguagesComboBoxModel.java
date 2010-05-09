package org.jtheque.views.impl.models;

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

import org.jtheque.core.ICore;
import org.jtheque.utils.collections.CollectionUtils;

import javax.swing.DefaultComboBoxModel;
import java.util.List;

/**
 * A combo box model of available languages.
 *
 * @author Baptiste Wicht
 */
public final class AvailableLanguagesComboBoxModel extends DefaultComboBoxModel {
    private final List<String> languages;

    /**
     * Construct a new AvailableLanguagesComboBoxModel.
     * 
     * @param core The core service.
     */
    public AvailableLanguagesComboBoxModel(ICore core) {
        super();

        languages = CollectionUtils.copyOf(core.getPossibleLanguages());
    }

    @Override
    public Object getElementAt(int index) {
        return languages.get(index);
    }

    @Override
    public int getSize() {
        return languages.size();
    }

    /**
     * Return the selected language.
     *
     * @return The selected language.
     */
    public String getSelectedLanguage() {
        return (String) getSelectedItem();
    }
}