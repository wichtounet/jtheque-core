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

import org.jtheque.modules.ModuleDescription;
import org.jtheque.modules.Repository;
import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.bean.InternationalString;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;

/**
 * A module repository.
 *
 * @author Baptiste Wicht
 */
@Immutable
final class RepositoryImpl implements Repository {
    private final InternationalString title;
    private final String application;
    private final Collection<ModuleDescription> modules;

    /**
     * Create a new RepositoryImpl.
     *
     * @param title       The title of the repository.
     * @param application The application of the repository.
     * @param modules     The modules of the repository.
     */
    RepositoryImpl(InternationalString title, String application, Collection<ModuleDescription> modules) {
        super();

        this.title = title;
        this.application = application;
        this.modules = CollectionUtils.protectedCopy(modules);
    }

    @Override
    public String getApplication() {
        return application;
    }

    @Override
    public Collection<ModuleDescription> getModules() {
        return modules;
    }

    @Override
    public InternationalString getTitle() {
        return title;
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