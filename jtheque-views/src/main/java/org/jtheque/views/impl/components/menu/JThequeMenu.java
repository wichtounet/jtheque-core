package org.jtheque.views.impl.components.menu;

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

import org.jtheque.i18n.Internationalizable;
import org.jtheque.i18n.LanguageService;

import javax.swing.JMenu;
import javax.swing.plaf.basic.BasicMenuUI;

/**
 * A JTheque menu.
 *
 * @author Baptiste Wicht
 */
public final class JThequeMenu extends JMenu implements Internationalizable {
    private final String key;

    /**
     * Construct a new JThequeMenu.
     *
     * @param key The internationalization key.
     */
    public JThequeMenu(String key) {
        super(key);

        this.key = key;

        setUI(new BasicMenuUI());
    }

    @Override
    public void refreshText(LanguageService languageService) {
        setText(languageService.getMessage(key));
    }
}