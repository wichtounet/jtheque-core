package org.jtheque.core.managers.view.impl.components.filthy;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.ViewDefaults;
import org.jtheque.core.utils.ui.Borders;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.text.JTextComponent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

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
 * An abstract filthy field.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractFilthyField extends JPanel {
    /**
     * Construct a new AbstractFilthyField.
     */
    public AbstractFilthyField() {
        super();

        IResourceManager resources = Managers.getManager(IResourceManager.class);

        Color inputColor = resources.getColor("filthyInputColor");
        Color inputBorderColor = resources.getColor("filthyInputBorderColor");

        setLayout(new BorderLayout());
        setBackground(inputColor);
        setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(inputBorderColor, 2),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)));

        initComponent();
    }

    /**
     * Init the component.
     */
    abstract void initComponent();

    /**
     * Make a component filthy.
     *
     * @param field The field to make filthy.
     */
    static void makeFilthy(JTextComponent field) {
        ViewDefaults defaults = Managers.getManager(IViewManager.class).getViewDefaults();

        Font inputFont = defaults.getFilthyInputFont();

        Color inputTextColor = defaults.getFilthyForegroundColor();
        Color inputSelectionColor = defaults.getFilthyForegroundColor();
        Color inputSelectedTextColor = defaults.getFilthyBackgroundColor();

        field.setOpaque(false);
        field.setBorder(Borders.EMPTY_BORDER);
        field.setForeground(inputTextColor);
        field.setSelectedTextColor(inputSelectedTextColor);
        field.setSelectionColor(inputSelectionColor);
        field.setCaretColor(inputSelectionColor);
        field.setFont(inputFont);
    }
}