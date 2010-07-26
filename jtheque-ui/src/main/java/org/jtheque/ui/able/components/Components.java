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
    /**
     * Utility class, not instantiable.
     */
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

    /**
     * Create a new file chooser.
     *
     * @return A new FileChooser.
     */
    public static FileChooser newFileChooser() {
        return new FileChooserPanel();
    }

    /**
     * Create a list renderer that display a simple icon for each element.
     *
     * @param labelIcon The icon to display on each element.
     *
     * @return The created ListCellRenderer.
     */
    public static ListCellRenderer newIconListRenderer(Icon labelIcon) {
        return new IconListRenderer(labelIcon);
    }

    /**
     * Create a card panel.
     *
     * @param <T> The type of components contained in the card panel.
     *
     * @return A new CardPanel. 
     */
    public static <T extends JComponent> CardPanel<T> newCardPanel() {
        return new CardPanel<T>();
    }
}