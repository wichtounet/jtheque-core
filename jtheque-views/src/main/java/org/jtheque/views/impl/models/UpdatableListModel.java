package org.jtheque.views.impl.models;

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

import org.jtheque.update.able.IUpdateService;
import org.jtheque.update.able.Updatable;
import org.jtheque.update.able.UpdatableListener;
import org.jtheque.utils.collections.CollectionUtils;

import javax.swing.DefaultListModel;
import java.util.List;

/**
 * A List model to display the modules.
 *
 * @author Baptiste Wicht
 */
public final class UpdatableListModel extends DefaultListModel implements UpdatableListener {
    private final List<Updatable> updatables;

    /**
     * Construct a new ModuleListModel.
     */
    public UpdatableListModel(IUpdateService updateService) {
        super();

        updatables = CollectionUtils.copyOf(updateService.getUpdatables());

        updateService.addUpdatableListener(this);
    }

    @Override
    public Object getElementAt(int index) {
        return updatables.get(index);
    }

    @Override
    public Object get(int index) {
        return updatables.get(index);
    }

    @Override
    public int getSize() {
        return updatables.size();
    }

    @Override
    public void updatableAdded(Updatable updatable) {
        updatables.add(updatable);
    }
}