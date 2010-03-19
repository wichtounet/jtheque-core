package org.jtheque.views.impl.models;

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

import org.jtheque.update.IUpdateService;
import org.jtheque.update.Updatable;
import org.jtheque.update.UpdatableListener;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.views.ViewsServices;

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
    public UpdatableListModel() {
        super();

        updatables = CollectionUtils.copyOf(ViewsServices.get(IUpdateService.class).getUpdatables());

        ViewsServices.get(IUpdateService.class).addUpdatableListener(this);
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
    public void updatableAdded() {
        updatables.clear();
        updatables.addAll(ViewsServices.get(IUpdateService.class).getUpdatables());
    }
}