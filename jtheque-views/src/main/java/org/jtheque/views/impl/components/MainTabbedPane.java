package org.jtheque.views.impl.components;

import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.i18n.able.Internationalizable;
import org.jtheque.ui.able.components.LayerTabbedPane;
import org.jtheque.utils.bean.Numbers;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.IViews;
import org.jtheque.views.able.components.MainComponent;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

/**
 * The main tabbed pane component.
 *
 * @author Baptiste Wicht
 */
public final class MainTabbedPane extends LayerTabbedPane {
    private final ILanguageService languageService;
    private final IViews views;

    /**
     * Construct a new MainTabbedPane.
     *
     * @param languageService The language service.
     * @param views           The views.
     */
    public MainTabbedPane(ILanguageService languageService, IViews views) {
        super(languageService);

        this.languageService = languageService;
        this.views = views;

        setTabPlacement(TOP);
    }

    /**
     * Build the main tabbed pane. Called by Spring.
     */
    @PostConstruct
    public void build() {
        List<MainComponent> components = CollectionUtils.copyOf(views.getMainComponents());
        Collections.sort(components, new PositionComparator());

        Map<JComponent, String> cs = new HashMap<JComponent, String>(components.size());

        for (MainComponent component : components) {
            addLayeredTab(languageService.getMessage(component.getTitleKey()), component.getImpl());

            cs.put(component.getImpl(), component.getTitleKey());
        }

        languageService.addInternationalizable(new TabTitleUpdater(cs));
    }

    /**
     * Refresh all components.
     */
    public void refreshComponents() {
        removeAll();

        List<MainComponent> components = CollectionUtils.copyOf(views.getMainComponents());

        Collections.sort(components, new PositionComparator());

        for (MainComponent component : components) {
            addLayeredTab(languageService.getMessage(component.getTitleKey()), component.getImpl());
        }

        SwingUtils.refresh(this);
    }

    /**
     * Remove the specified main component.
     *
     * @param component The component to remove.
     */
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
        private static final long serialVersionUID = -8093634913175450541L;

        @Override
        public int compare(MainComponent component, MainComponent other) {
            return Numbers.compare(component.getPosition(), other.getPosition());
        }
    }

    /**
     * A tab title updater to keep the tab title up to date with the current locale.
     *
     * @author Baptiste Wicht
     */
    private final class TabTitleUpdater implements Internationalizable {
        private final Map<JComponent, String> components;

        /**
         * Construct a new TabTitleUpdater.
         *
         * @param components The components of the tabbed pane.
         */
        private TabTitleUpdater(Map<JComponent, String> components) {
            super();

            this.components = new HashMap<JComponent, String>(components);
        }

        @Override
        public void refreshText(ILanguageService languageService) {
            for (Entry<JComponent, String> entry : components.entrySet()) {
                for (int i = 0; i < getTabCount(); i++) {
                    if (entry.getKey().equals(getTabComponentAt(i))) {
                        setTitleAt(i, languageService.getMessage(entry.getValue()));

                        break;
                    }
                }
            }
        }
    }
}
