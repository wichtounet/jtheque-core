package org.jtheque.ui.utils.actions;

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
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.collections.ArrayUtils;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

/**
 * An action of JTheque.
 *
 * @author Baptiste Wicht
 */
public abstract class JThequeAction extends AbstractAction implements Internationalizable {
    private final transient String key;
    private final transient Object[] replaces;

    private static final Object[] EMPTY_REPLACES = {};

    /**
     * Construct a new JThequeAction. 
     */
    protected JThequeAction() {
        this("", EMPTY_REPLACES);
    }

    /**
     * Construct a new JThequeAction.
     *
     * @param key The internationalization key.
     */
    protected JThequeAction(String key) {
        this(key, EMPTY_REPLACES);
    }

    /**
     * Construct a new JThequeAction.
     *
     * @param key      The internationalization key.
     * @param replaces The replacements on the message resource.
     */
    protected JThequeAction(String key, Object... replaces) {
        super();

        this.key = key;
        this.replaces = ArrayUtils.copyOf(replaces);

        setText(key);
    }

    /**
     * Set the text of the action.
     *
     * @param text The text.
     */
    public final void setText(String text) {
        putValue(NAME, text);
    }

    /**
     * Set the icon of te action.
     *
     * @param icon The icon.
     */
    public final void setIcon(ImageIcon icon) {
        putValue(SMALL_ICON, icon);
    }

    @Override
    public final void refreshText(LanguageService languageService) {
        if(StringUtils.isNotEmpty(key)){
            if (replaces == null) {
                setText(languageService.getMessage(key));
            } else {
                setText(languageService.getMessage(key, replaces));
            }
        }
    }
}