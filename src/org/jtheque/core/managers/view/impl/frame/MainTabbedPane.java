package org.jtheque.core.managers.view.impl.frame;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.language.TabTitleUpdater;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.components.TabComponent;
import org.jtheque.core.managers.view.impl.components.LayerTabbedPane;
import org.jtheque.core.managers.view.listeners.TabEvent;
import org.jtheque.core.managers.view.listeners.TabListener;
import org.jtheque.utils.collections.CollectionUtils;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/**
 * The main tabbed pane component.
 *
 * @author Baptiste Wicht
 */
public final class MainTabbedPane extends LayerTabbedPane implements TabListener {
    /**
     * Construct a new MainTabbedPane.
     */
    public MainTabbedPane() {
        super();

        setTabPlacement(JTabbedPane.TOP);

        List<TabComponent> components = CollectionUtils.copyOf(Managers.getManager(IViewManager.class).getTabComponents());
        Collections.sort(components, new PositionComparator());

        Map<JComponent, String> cs = new HashMap<JComponent, String>(components.size());

        for (TabComponent component : components) {
            addLayeredTab(Managers.getManager(ILanguageManager.class).getMessage(component.getTitleKey()), component.getComponent());
            cs.put(component.getComponent(), component.getTitleKey());
        }

        Managers.getManager(IViewManager.class).addTabListener(this);

        Managers.getManager(ILanguageManager.class).addInternationalizable(new TabTitleUpdater(this, cs));
    }

    @Override
    public void tabAdded() {
        if (Managers.getManager(IViewManager.class).isTabMainComponent()) {
            removeAll();

            List<TabComponent> components = CollectionUtils.copyOf(Managers.getManager(IViewManager.class).getTabComponents());

            Collections.sort(components, new PositionComparator());

            for (TabComponent component : components) {
                addLayeredTab(Managers.getManager(ILanguageManager.class).getMessage(component.getTitleKey()), component.getComponent());
            }

            Managers.getManager(IViewManager.class).refresh(this);
        }
    }

    @Override
    public void tabRemoved(TabEvent event) {
        if (Managers.getManager(IViewManager.class).isTabMainComponent()) {
            removeTabAt(indexOfTab(Managers.getManager(ILanguageManager.class).getMessage(event.getComponent().getTitleKey())));

            Managers.getManager(IViewManager.class).refresh(this);
        }
    }

    /**
     * A comparator to sort the TabComponent by position.
     *
     * @author Baptiste Wicht
     */
    private static final class PositionComparator implements Comparator<TabComponent>, Serializable {
        private static final long serialVersionUID = -4510118352701529569L;

        @Override
        public int compare(TabComponent component, TabComponent other) {
            return component.getPosition().compareTo(other.getPosition());
        }
    }
}
