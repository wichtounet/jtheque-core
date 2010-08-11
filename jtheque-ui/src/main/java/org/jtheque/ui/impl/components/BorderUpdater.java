package org.jtheque.ui.impl.components;

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

import javax.swing.border.TitledBorder;

/**
 * A border title updater.
 *
 * @author Baptiste Wicht
 */
public final class BorderUpdater implements Internationalizable {
    private final TitledBorder border;
    private final String key;

    /**
     * Construct a new BorderUpdater for a specific border with a internationalization key.
     *
     * @param border The titled border.
     * @param key    The internationalization key.
     */
    public BorderUpdater(TitledBorder border, String key) {
        super();

        this.border = border;
        this.key = key;
    }

    @Override
    public void refreshText(LanguageService languageService) {
        border.setTitle(languageService.getMessage(key));
    }
}
