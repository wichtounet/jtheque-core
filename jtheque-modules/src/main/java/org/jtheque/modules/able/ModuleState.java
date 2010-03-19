package org.jtheque.modules.able;

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

import org.jtheque.i18n.ILanguageService;
import org.jtheque.modules.impl.ModulesServices;

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
                state = ModulesServices.get(ILanguageService.class).getMessage("modules.state.loaded");

                break;
            case INSTALLED:
                state = ModulesServices.get(ILanguageService.class).getMessage("modules.state.installed");

                break;
            case DISABLED:
                state = ModulesServices.get(ILanguageService.class).getMessage("modules.state.disabled");

                break;
            case UNINSTALLED:
                state = ModulesServices.get(ILanguageService.class).getMessage("modules.state.uninstalled");

                break;
            default:
                state = "Undefined";

                break;
        }

        return state;
    }
}
