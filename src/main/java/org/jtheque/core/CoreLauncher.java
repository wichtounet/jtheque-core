package org.jtheque.core;

import org.jtheque.core.managers.core.Core;
import org.jtheque.core.managers.core.application.Application;
import org.jtheque.core.managers.core.application.XMLApplicationReader;
import org.jtheque.core.utils.SystemProperty;

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
 * A simple launcher for XML applications.
 *
 * @author Baptiste Wicht
 */
public final class CoreLauncher {
    /**
     * Utility class, not instanciable. 
     */
    private CoreLauncher() {
        super();
    }

    /**
     * Launch the application. The application is read from the "application.xml" file at the same location than the
     * core.
     *
     * @param args No args will be read.
     */
    public static void main(String[] args){
        SystemProperty.USER_DIR.set("N:\\Programmation\\WorkDirectory\\JTheque\\Applications\\JTheque Movies Windows\\core\\");

        Application application = new XMLApplicationReader().readApplication(SystemProperty.USER_DIR.get() + "application.xml");

        Core.getInstance().launchJThequeCore(application);
    }
}