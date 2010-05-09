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
     * @param languageService
     * @param views
     */
    public MainTabbedPane(ILanguageService languageService, IViews views) {
        super(languageService);

        this.languageService = languageService;
        this.views = views;

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
