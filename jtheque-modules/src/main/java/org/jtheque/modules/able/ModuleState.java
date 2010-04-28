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

    public String getKey(){
        switch (this) {
            case LOADED:
                return "modules.state.loaded";
            case JUST_INSTALLED:
            case INSTALLED:
                return "modules.state.installed";
            case DISABLED:
                return "modules.state.disabled";
            case UNINSTALLED:
                return "modules.state.uninstalled";
        }

        return "";
    }
}
