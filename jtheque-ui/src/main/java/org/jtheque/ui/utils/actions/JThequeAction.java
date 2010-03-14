package org.jtheque.ui.utils.actions;

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
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.ui.ViewsUtilsServices;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

/**
 * An action of JTheque.
 *
 * @author Baptiste Wicht
 */
public abstract class JThequeAction extends AbstractAction implements Internationalizable {
    private final String key;
    private Object[] replaces;

    private static final Object[] EMPTY_REPLACES = {};

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

        ViewsUtilsServices.get(ILanguageManager.class).addInternationalizable(this);

        this.key = key;

        if (ArrayUtils.isEmpty(replaces)) {
            setTextKey(key);
        } else {
            this.replaces = ArrayUtils.copyOf(replaces);

            setTextKey(key, replaces);
        }
    }

    /**
     * Set the text key.
     *
     * @param key The internationalization key.
     */
    final void setTextKey(String key) {
        setText(ViewsUtilsServices.get(ILanguageManager.class).getMessage(key));
    }

    /**
     * Set the text key.
     *
     * @param key      The internationalization key.
     * @param replaces The replacements on the message resource.
     */
    final void setTextKey(String key, Object... replaces) {
        setText(ViewsUtilsServices.get(ILanguageManager.class).getMessage(key, replaces));
    }

    /**
     * Set the text of the action.
     *
     * @param text The text.
     */
    public final void setText(String text) {
        putValue(Action.NAME, text);
    }

    /**
     * Set the icon of te action.
     *
     * @param icon The icon.
     */
    public final void setIcon(ImageIcon icon) {
        putValue(Action.SMALL_ICON, icon);
    }

    @Override
    public final void refreshText() {
        if (replaces == null) {
            setTextKey(key);
        } else {
            setTextKey(key, replaces);
        }
    }
}