package org.jtheque.ui.utils.windows;

import org.jtheque.utils.ui.SwingUtils;

import org.jdesktop.jxlayer.JXLayer;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Component;

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

public class WindowHelper {
    private WindowHelper() {
        super();
    }

    public static void applyGlassPane(final Component glassPane, final JXLayer<JComponent> content) {
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                if (glassPane == null) {
                    JPanel glasspane = content.createGlassPane();

                    glasspane.setVisible(false);
                    glasspane.setOpaque(false);

                    content.setGlassPane(glasspane);
                } else {
                    content.setGlassPane((JPanel) glassPane);

                    glassPane.setVisible(true);
                    glassPane.repaint();

                    SwingUtilities.updateComponentTreeUI(glassPane);
                }

                SwingUtilities.updateComponentTreeUI(content);
            }
        });
    }
}