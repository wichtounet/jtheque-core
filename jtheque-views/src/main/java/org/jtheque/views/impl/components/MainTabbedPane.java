package org.jtheque.views.impl.components;

import org.jtheque.i18n.ILanguageService;
import org.jtheque.ui.utils.components.TabTitleUpdater;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.IViews;
import org.jtheque.views.able.components.MainComponent;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.JComponent;
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
public final class MainTabbedPane extends LayerTabbedPane {
    @Resource
    private ILanguageService languageService;

    @Resource
    private IViews views;

    /**
     * Construct a new MainTabbedPane.
     */
    public MainTabbedPane() {
        super();

        setTabPlacement(TOP);
    }

    @PostConstruct
    public void build(){
        List<MainComponent> components = CollectionUtils.copyOf(views.getMainComponents());
        Collections.sort(components, new PositionComparator());

        Map<JComponent, String> cs = new HashMap<JComponent, String>(components.size());

        for (MainComponent component : components) {
            addLayeredTab(languageService.getMessage(component.getTitleKey()), component.getComponent());

            cs.put(component.getComponent(), component.getTitleKey());
        }

        languageService.addInternationalizable(new TabTitleUpdater(this, cs));
    }

    public void refreshComponents() {
        removeAll();

        List<MainComponent> components = CollectionUtils.copyOf(views.getMainComponents());

        Collections.sort(components, new PositionComparator());

        for (MainComponent component : components) {
            addLayeredTab(languageService.getMessage(component.getTitleKey()), component.getComponent());
        }

        SwingUtils.refresh(this);
    }

    public void removeMainComponent(MainComponent component) {
        removeTabAt(indexOfTab(languageService.getMessage(component.getTitleKey())));

        SwingUtils.refresh(this);
    }

    /**
     * A comparator to sort the MainComponent by position.
     *
     * @author Baptiste Wicht
     */
    private static final class PositionComparator implements Comparator<MainComponent>, Serializable {
        @Override
        public int compare(MainComponent component, MainComponent other) {
            return component.getPosition().compareTo(other.getPosition());
        }
    }
}
