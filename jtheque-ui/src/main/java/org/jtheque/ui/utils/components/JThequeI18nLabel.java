package org.jtheque.ui.utils.components;

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

import org.jtheque.i18n.ILanguageManager;
import org.jtheque.i18n.Internationalizable;
import org.jtheque.ui.able.ViewComponent;
import org.jtheque.ui.ViewsUtilsServices;
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

        ViewsUtilsServices.get(ILanguageManager.class).addInternationalizable(this);
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

        refreshText();
    }

    @Override
    public void refreshText() {
        if (ArrayUtils.isEmpty(replaces)) {
            setText(ViewsUtilsServices.get(ILanguageManager.class).getMessage(textKey));
        } else {
            setText(ViewsUtilsServices.get(ILanguageManager.class).getMessage(textKey, replaces));
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