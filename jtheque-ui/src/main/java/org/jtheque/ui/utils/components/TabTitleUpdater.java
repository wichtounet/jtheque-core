package org.jtheque.ui.utils.components;

import org.jtheque.i18n.ILanguageService;
import org.jtheque.i18n.Internationalizable;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
