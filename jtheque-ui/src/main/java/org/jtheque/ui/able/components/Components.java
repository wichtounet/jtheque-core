package org.jtheque.ui.able.components;

import org.jtheque.ui.impl.components.FileChooserPanel;
import org.jtheque.ui.impl.components.IconListRenderer;
import org.jtheque.ui.impl.components.JThequeI18nLabel;
import org.jtheque.ui.impl.components.JThequeLabel;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;

public final class Components {
    private Components() {
        throw new AssertionError();
    }

    public static JLabel newLabel(String text) {
        return new JThequeLabel(text);
    }

    public static I18nLabel newI18nLabel(String key, Object... replaces) {
        return new JThequeI18nLabel(key, replaces);
    }

    /**
     * Create an i18n checkbox.
     *
     * @param key The i18n key of the checkbox.
     *
     * @return A new JThequeCheckBox
     */
    public static JThequeCheckBox newCheckBox(String key) {
        return new JThequeCheckBox(key);
    }

    public static FileChooser newFileChooserPanel() {
        return new FileChooserPanel();
    }

    public static ListCellRenderer newIconListRenderer(Icon labelIcon) {
        return new IconListRenderer(labelIcon);
    }

    public static <T extends JComponent> CardPanel<T> newCardPanel() {
        return new CardPanel<T>();
    }
}