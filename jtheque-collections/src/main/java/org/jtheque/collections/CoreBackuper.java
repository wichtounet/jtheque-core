package org.jtheque.collections;

import org.jtheque.file.able.ModuleBackup;
import org.jtheque.file.able.ModuleBackuper;
import org.jtheque.io.Node;
import org.jtheque.utils.bean.Version;

import java.util.ArrayList;
import java.util.Collection;

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

public class CoreBackuper implements ModuleBackuper {
    private static final String[] DEPENDENCIES = new String[0];

    private static final Version BACKUP_VERSION = new Version("1.0");

    private final IDaoCollections daoCollections;

    public CoreBackuper(IDaoCollections daoCollections) {
        super();

        this.daoCollections = daoCollections;
    }

    @Override
    public String getId() {
        return "jtheque-core-backuper";
    }

    @Override
    public String[] getDependencies() {
        return DEPENDENCIES;
    }

    @Override
    public ModuleBackup backup() {
        ModuleBackup backup = new ModuleBackup();

        backup.setId(getId());
        backup.setVersion(BACKUP_VERSION);

        Collection<Node> nodes = new ArrayList<Node>(10);

        for(org.jtheque.collections.Collection collection : daoCollections.getCollections()){
            Node node = new Node("collection");

            node.addSimpleChildValue("id", collection.getId());
            node.addSimpleChildValue("name", collection.getTitle());
            node.addSimpleChildValue("password", collection.getPassword());

            nodes.add(node);
        }

        backup.setNodes(nodes);

        return backup;
    }

    @Override
    public void restore(ModuleBackup backup) {
        assert getId().equals(backup.getId()) : "This backuper can only restore its own backups";

        for(Node node : backup.getNodes()){
            if("collection".equals(node.getName())){
                org.jtheque.collections.Collection collection = daoCollections.create();

                collection.setTitle(node.getChildValue("title"));
                collection.setPassword(node.getChildValue("password"));

                //Temporary link to restore for others backupers
                collection.getTemporaryContext().setId(node.getChildIntValue("id"));

                daoCollections.create(collection);
            }
        }
    }
}