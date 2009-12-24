package org.jtheque.core.managers.module.beans;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;

/**
 * The state of a module.
 *
 * @author Baptiste Wicht
 */
public enum ModuleState {
    LOADED,
    INSTALLED,
    JUST_INSTALLED,
    DISABLED,
    UNINSTALLED;

    /**
     * Return the enum value from the ordinal value.
     *
     * @param ordinal The ordinal value.
     * @return The enum value corresponding to the ordinal value.
     */
    public static ModuleState valueOf(int ordinal) {
        ModuleState state = LOADED;

        for (ModuleState s : values()) {
            if (s.ordinal() == ordinal) {
                state = s;
                break;
            }
        }

        return state;
    }

    @Override
    public String toString() {
        String state;

        switch (valueOf(ordinal())) {
            case LOADED:
                state = Managers.getManager(ILanguageManager.class).getMessage("modules.state.loaded");

                break;
            case INSTALLED:
                state = Managers.getManager(ILanguageManager.class).getMessage("modules.state.installed");

                break;
            case DISABLED:
                state = Managers.getManager(ILanguageManager.class).getMessage("modules.state.disabled");

                break;
            case UNINSTALLED:
                state = Managers.getManager(ILanguageManager.class).getMessage("modules.state.uninstalled");

                break;
            default:
                state = "Undefined";

                break;
        }

        return state;
    }
}
