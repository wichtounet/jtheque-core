package org.jtheque.views.impl.actions.module.repository;

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

import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.able.panel.IRepositoryView;

import java.awt.event.ActionEvent;

/**
 * Expand the selected module.
 *
 * @author Baptiste Wicht
 */
public final class ExpandRepositoryModuleAction extends JThequeAction {
    private final IRepositoryView repositoryView;

    /**
     * Construct a new ExpandRepositoryModuleAction.
     */
    public ExpandRepositoryModuleAction(IRepositoryView repositoryView) {
        super("repository.actions.expand");

        this.repositoryView = repositoryView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repositoryView.expandSelectedModule();
    }
}