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
import org.jtheque.core.utils.CoreUtils;

import java.awt.event.ActionEvent;

/**
 * An action to create the collection.
 *
 * @author Baptiste Wicht
 */
public final class CreateAction extends JThequeAction {
    /**
     * Construct a new CreateAction.
     */
    public CreateAction() {
        super("collections.actions.create");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ICollectionView collectionView = CoreUtils.getBean("collectionPane");

        Managers.getCore().getLifeCycleManager().chooseCollection(collectionView.getCollection(), collectionView.getPassword(), true);
    }
}
