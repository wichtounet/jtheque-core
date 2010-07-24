package org.jtheque.ui.impl.components.filthy;

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

import org.jtheque.ui.able.components.Borders;
import org.jtheque.ui.able.components.CardPanel;
import org.jtheque.utils.ui.GraphicsUtils;

import javax.swing.JComponent;

import java.awt.Graphics;

/**
 * A filthy panel. It's an opaque panel with rounded rectangle borders.
 *
 * @author Baptiste Wicht
 */
public final class FilthyCardPanel<T extends JComponent> extends CardPanel<T> {
    /**
     * Construct a new <code>FilthyPanel</code>.
     */
    public FilthyCardPanel() {
        super();

        setOpaque(false);
        setBorder(Borders.createEmptyBorder(10));
    }

    @Override
    public void paint(Graphics g) {
        GraphicsUtils.setFilthyHints(g);

        super.paint(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        GraphicsUtils.paintFilthyPanel(g, getWidth(), getHeight());
    }
}