package org.jtheque.collections.impl;

import org.jtheque.collections.able.DaoCollections;
import org.jtheque.collections.able.DataCollection;
import org.jtheque.file.able.ModuleBackup;
import org.jtheque.file.able.ModuleBackuper;
import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.ArrayUtils;
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
 * A backuper for the core. This class is immutable.
 *
 * @author Baptiste Wicht
 */
@Immutable
final class CollectionsBackuper implements ModuleBackuper {
    private static final String[] DEPENDENCIES = new String[0];

    private final DaoCollections daoCollections;

    /**
     * Construct a new CollectionsBackuper.
     *
     * @param daoCollections The dao collections.
     */
    CollectionsBackuper(DaoCollections daoCollections) {
        super();

        this.daoCollections = daoCollections;
    }

    @Override
    public String getId() {
        return "jtheque-core-backuper";
    }

    @Override
    public String[] getDependencies() {
        return ArrayUtils.copyOf(DEPENDENCIES);
    }

    @Override
    public ModuleBackup backup() {
        Collection<DataCollection> dataCollections = daoCollections.getCollections();

        Collection<Node> nodes = CollectionUtils.newList(dataCollections.size());

        for (DataCollection collection : dataCollections) {
            Node node = new Node("collection");

            node.addSimpleChildValue("id", collection.getId());
            node.addSimpleChildValue("name", collection.getTitle());
            node.addSimpleChildValue("password", collection.getPassword());

            nodes.add(node);
        }

        return new ModuleBackup(Version.get("1.0"), getId(), nodes);
    }

    @Override
    public void restore(ModuleBackup backup) {
        assert getId().equals(backup.getId()) : "This backuper can only restore its own backups";

        for (Node node : backup.getNodes()) {
            if ("collection".equals(node.getName())) {
                DataCollection collection = daoCollections.create();

                collection.setTitle(node.getChildValue("title"));
                collection.setPassword(node.getChildValue("password"));

                //Temporary link to restore for others backupers
                collection.getTemporaryContext().setId(node.getChildIntValue("id"));

                daoCollections.save(collection);
            }
        }
    }
}