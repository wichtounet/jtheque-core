package org.jtheque.core.managers.view.impl.actions.module.repository;

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

import org.jtheque.core.managers.view.able.update.IRepositoryView;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * Expand the selected module.
 *
 * @author Baptiste Wicht
 */
public final class ExpandRepositoryModuleAction extends JThequeAction {
    private static final long serialVersionUID = 8858330756505259791L;

    @Resource
    private IRepositoryView repositoryView;

    /**
     * Construct a new ExpandRepositoryModuleAction.
     */
    public ExpandRepositoryModuleAction() {
        super("repository.actions.expand");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repositoryView.expandSelectedModule();
    }
}