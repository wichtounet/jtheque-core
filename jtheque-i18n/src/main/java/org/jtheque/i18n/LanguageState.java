package org.jtheque.i18n;

import org.jtheque.states.AbstractState;
import org.jtheque.states.State;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.Version;

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

/**
 * The persistent state of the language. This class must be public, it will be accessed with reflection.
 *
 * @author Baptiste Wicht
 */
@State(id = "jtheque-i18n-configuration")
public final class LanguageState extends AbstractState {
    /**
     * Set the language.
     *
     * @param language The language.
     */
    public void setLanguage(String language) {
        setProperty("language", language);
    }

    /**
     * Return the language of the state.
     *
     * @return The language.
     */
    public String getLanguage() {
        return getProperty("language", "fr");
    }

    public Version getResourceVersion(String resource){
        String property = getProperty(resource + "_version");

        if(StringUtils.isEmpty(property)){
            return null;
        } else {
            return new Version(property);
        }
    }

    public void setResourceVersion(String resource, Version version){
        setProperty(resource + "_version", version.toString());
    }
}