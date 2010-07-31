package org.jtheque.ui.able.components;

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

import org.jtheque.i18n.able.LanguageService;
import org.jtheque.i18n.able.Internationalizable;
import org.jtheque.utils.collections.ArrayUtils;

import javax.swing.JCheckBox;

import java.awt.Color;

/**
 * A internationalizable checkbox.
 *
 * @author Baptiste Wicht
 */
public class JThequeCheckBox extends JCheckBox implements Internationalizable {
    private final String textKey;
    private final Object[] textReplaces;

    private static final Object[] EMPTY_REPLACES = new Object[0];

    /**
     * Construct a new JThequeCheckBox.
     *
     * @param key The internationalization key.
     */
    public JThequeCheckBox(String key) {
        super();

        setBackground(Color.white);

        textKey = key;
        textReplaces = EMPTY_REPLACES;
    }

    /**
     * Construct a new JThequeCheckBox.
     *
     * @param key      The internationalization key.
     * @param replaces The i18n replaces.
     */
    public JThequeCheckBox(String key, Object[] replaces) {
        super();

        setBackground(Color.white);

        textKey = key;
        textReplaces = ArrayUtils.copyOf(replaces);
    }

    @Override
    public void refreshText(LanguageService languageService) {
        setText(languageService.getMessage(textKey, textReplaces));
    }
}