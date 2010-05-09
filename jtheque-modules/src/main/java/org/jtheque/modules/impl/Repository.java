package org.jtheque.modules.impl;

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

import org.jtheque.utils.bean.InternationalString;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A module repository.
 *
 * @author Baptiste Wicht
 */
public final class Repository {
    private InternationalString title;
    private String application;
    private final Collection<ModuleDescription> modules = new ArrayList<ModuleDescription>(10);

    /**
     * Return the title of the repository.
     *
     * @return The title of the repository.
     */
    public InternationalString getTitle() {
        return title;
    }

    /**
     * Set the title of the repository.
     *
     * @param title The title of the repository.
     */
    public void setTitle(InternationalString title) {
        this.title = title;
    }

    /**
     * Return the application name.
     *
     * @return The application name.
     */
    public String getApplication() {
        return application;
    }

    /**
     * Set the application name.
     *
     * @param application The application name.
     */
    public void setApplication(String application) {
        this.application = application;
    }

    /**
     * Return all the modules of the repository.
     *
     * @return A List containing the description of the modules.
     */
    public Collection<ModuleDescription> getModules() {
        return modules;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "title=" + title +
                ", application='" + application + '\'' +
                ", modules=" + modules +
                '}';
    }
}