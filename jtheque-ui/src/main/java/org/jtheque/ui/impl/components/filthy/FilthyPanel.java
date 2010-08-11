package org.jtheque.ui.impl.components.filthy;

import org.jtheque.ui.components.Borders;
import org.jtheque.utils.ui.GraphicsUtils;

import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.LayoutManager;

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

/**
 * A filthy panel. It's an opaque panel with rounded rectangle borders.
 *
 * @author Baptiste Wicht
 */
public final class FilthyPanel extends JPanel {
    /**
     * Construct a new <code>FilthyPanel</code>.
     */
    public FilthyPanel() {
        this(new FlowLayout());
    }

    /**
     * Construct a new <code>FilthyPanel</code>.
     *
     * @param layoutManager The layout manager to use.
     */
    public FilthyPanel(LayoutManager layoutManager) {
        super(layoutManager);

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