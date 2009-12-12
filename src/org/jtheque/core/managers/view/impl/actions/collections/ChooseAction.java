package org.jtheque.core.managers.view.impl.actions.collections;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.able.ICollectionView;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to choose a collection.
 *
 * @author Baptiste Wicht
 */
public final class ChooseAction extends JThequeAction {
    @Resource
    private ICollectionView collectionView;

    /**
     * Construct a new ChooseAction.
     */
    public ChooseAction() {
        super("collections.actions.choose");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Managers.getCore().getLifeCycleManager().chooseCollection(collectionView.getCollection(), collectionView.getPassword(), false);
    }
}
