package org.jtheque.core.managers.view.impl.components.renderers;

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

import org.jtheque.core.managers.view.impl.components.panel.ModulePanel;

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * A renderer to display a module in a list.
 *
 * @author Baptiste Wicht
 */
public final class ModuleRepositoryListRenderer implements ListCellRenderer {
    private final Map<Integer, ModulePanel> panels;

    /**
     * Construct a new ModuleListRenderer.
     */
    public ModuleRepositoryListRenderer() {
        super();

        panels = new HashMap<Integer, ModulePanel>(10);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (panels.get(index) == null) {
            panels.put(index, new ModulePanel(value, isSelected));
        } else {
            panels.get(index).updateUI(isSelected);
        }

        return panels.get(index);
    }

}