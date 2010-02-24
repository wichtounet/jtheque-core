package org.jtheque.core.managers.language;

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

import org.jtheque.core.managers.state.AbstractState;

/**
 * The persistent state of the language. This class must be public, it will be accessed with reflection.
 *
 * @author Baptiste Wicht
 */
//Must be public for StateManager
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
        return getProperty("language");
    }

    @Override
    public void setDefaults() {
        setLanguage("fr");
    }
}