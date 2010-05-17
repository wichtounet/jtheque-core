package org.jtheque.ui.utils.components;

import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.i18n.able.Internationalizable;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import java.util.HashMap;
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
 * A tab title updater to keep the tab title up to date with the current locale.
 *
 * @author Baptiste Wicht
 */
public final class TabTitleUpdater implements Internationalizable {
    private final JTabbedPane tab;
    private final Map<JComponent, String> components;

    /**
     * Construct a new TabTitleUpdater.
     *
     * @param tab        The tabbed pane.
     * @param components The components of the tabbed pane.
     */
    public TabTitleUpdater(JTabbedPane tab, Map<JComponent, String> components) {
        super();

        this.tab = tab;
        this.components = new HashMap<JComponent, String>(components);
    }

    @Override
    public void refreshText(ILanguageService languageService) {
        for (Entry<JComponent, String> entry : components.entrySet()) {
            for (int i = 0; i < tab.getTabCount(); i++) {
                if (entry.getKey().equals(tab.getTabComponentAt(i))) {
                    tab.setTitleAt(i, languageService.getMessage(entry.getValue()));

                    break;
                }
            }
        }
    }
}
