package org.jtheque.modules.utils;

import org.jtheque.utils.bean.Version;

import java.util.ArrayList;
import java.util.List;

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

public class I18NDescription {
    private final String name;
    private final Version version;
    private final List<String> resources;

    public I18NDescription(String name, Version version) {
        super();

        this.name = name;
        this.version = version;

        resources = new ArrayList<String>(5);
    }

    public String getName() {
        return name;
    }

    public Version getVersion() {
        return version;
    }

    public List<String> getResources() {
        return resources;
    }
}