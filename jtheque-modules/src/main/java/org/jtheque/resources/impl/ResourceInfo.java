package org.jtheque.resources.impl;

import org.jtheque.utils.bean.Version;

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

public class ResourceInfo {
    private final String id;
    private final List<Version> versions;

    public ResourceInfo(String id, List<Version> versions) {
        super();

        this.id = id;
        this.versions = versions;
    }

    public String getId() {
        return id;
    }

    public List<Version> getVersions() {
        return versions;
    }
}