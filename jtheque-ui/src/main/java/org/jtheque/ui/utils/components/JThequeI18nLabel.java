package org.jtheque.ui.utils.components;

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

import org.jtheque.i18n.ILanguageService;
import org.jtheque.i18n.Internationalizable;
import org.jtheque.ui.able.ViewComponent;
import org.jtheque.utils.collections.ArrayUtils;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * An internationalizable label.
 *
 * @author Baptiste Wicht
 */
public final class JThequeI18nLabel extends JLabel implements Internationalizable, ViewComponent {
    private String textKey;
    private Object[] replaces;

    /**
     * Construct a new <code>JThequeI18nLabel</code>.
     *
     * @param textKey  The internationalization key.
     * @param replaces The replaces for the message.
     */
    public JThequeI18nLabel(String textKey, Object... replaces) {
        super();

        setTextKey(textKey, replaces);
    }

    /**
     * Construct a new <code>JThequeI18nLabel</code>.
     *
     * @param key  The internationalization key.
     * @param font The font to use.
     */
    public JThequeI18nLabel(String key, Font font) {
        this(key);

        setFont(font);
    }

    /**
     * Construct a new <code>JThequeI18nLabel</code>.
     *
     * @param key        The internationalization key.
     * @param font       The font to use.
     * @param foreground The foreground color.
     */
    public JThequeI18nLabel(String key, Font font, Color foreground) {
        this(key);

        setFont(font);
        setForeground(foreground);
    }

    /**
     * Set the text key of the label.
     *
     * @param textKey  The i18n key of the message to be display in this label.
     * @param replaces The object to use as replacement for the parameters of the message.
     */
    public void setTextKey(String textKey, Object... replaces) {
        this.textKey = textKey;
        this.replaces = ArrayUtils.copyOf(replaces);
    }

    @Override
    public void refreshText(ILanguageService languageService) {
        if (ArrayUtils.isEmpty(replaces)) {
            setText(languageService.getMessage(textKey));
        } else {
            setText(languageService.getMessage(textKey, replaces));
        }
    }

    @Override
    public void paint(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        super.paint(g);
    }

    @Override
    public Object getImpl(){
        return this;
    }
}