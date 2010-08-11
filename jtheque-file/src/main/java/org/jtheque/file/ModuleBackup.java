package org.jtheque.file;

import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.xml.utils.Node;

import java.util.Collection;

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

/**
 * A module backup. It's a backup of all the data of a module.
 *
 * @author Baptiste Wicht
 */
@Immutable
public final class ModuleBackup {
    private final Version version;
    private final String id;
    private final Collection<Node> nodes;

    public ModuleBackup(Version version, String id, Collection<Node> nodes) {
        super();

        this.version = version;
        this.id = id;
        this.nodes = CollectionUtils.protectedCopy(nodes);
    }

    /**
     * Return the id of the module.
     *
     * @return The id of the module.
     */
    public String getId() {
        return id;
    }

    /**
     * Return all the nodes of the backup.
     *
     * @return All the nodes of the backup.
     */
    public Collection<Node> getNodes() {
        return nodes;
    }

    /**
     * Return the version of the backuper.
     *
     * @return The version of the backuper.
     */
    public Version getVersion() {
        return version;
    }
}