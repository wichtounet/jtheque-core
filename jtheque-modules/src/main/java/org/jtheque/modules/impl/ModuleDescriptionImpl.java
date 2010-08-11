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
import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.bean.InternationalString;
import org.jtheque.utils.bean.Version;

/**
 * An online description of a module.
 *
 * @author Baptiste Wicht
 */
@Immutable
public final class ModuleDescriptionImpl implements ModuleDescription {
    private final String id;
    private final String name;
    private final InternationalString description;
    private final String url;
    private final Version coreVersion;

    public ModuleDescriptionImpl(String id, String name, InternationalString description, String url, Version coreVersion) {
        super();

        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.coreVersion = coreVersion;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public InternationalString getDescription() {
        return description;
    }

    @Override
    public String getDescriptorURL() {
        return url;
    }

    @Override
    public Version getCoreVersion() {
        return coreVersion;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Version getVersion() {
        return Version.get("0.0");
    }

    @Override
    public String toString() {
        return "ModuleDescription{" +
                "name='" + name + '\'' +
                ", description=" + description +
                ", url='" + url + '\'' +
                ", core=" + coreVersion +
                '}';
    }
}