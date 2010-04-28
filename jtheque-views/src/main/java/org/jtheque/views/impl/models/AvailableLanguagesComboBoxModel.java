package org.jtheque.views.impl.models;

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