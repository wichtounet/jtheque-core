package org.jtheque.views.impl.components.renderers;

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

import org.jtheque.images.able.IImageService;
import org.jtheque.views.impl.ViewsResources;

import org.jdesktop.swingx.error.ErrorInfo;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.util.logging.Level;

/**
 * A renderer to display a module in a list.
 *
 * @author Baptiste Wicht
 */
public final class ErrorListRenderer extends JLabel implements ListCellRenderer {
    private final ImageIcon errorIcon;
    private final ImageIcon warningIcon;

    /**
     * Construct a new ModuleListRenderer.
     *
     * @param imageService The resource service.
     */
    public ErrorListRenderer(IImageService imageService) {
        super();

        errorIcon = imageService.getIcon(ViewsResources.ERROR_ICON);
        warningIcon = imageService.getIcon(ViewsResources.WARNING_ICON);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            setBackground(Color.blue);
            setForeground(Color.white);
        } else {
            setBackground(Color.white);
            setForeground(Color.white);
        }

        ErrorInfo error = (ErrorInfo) value;

        setText(error.getTitle());

        if (error.getErrorLevel() == Level.WARNING) {
            setIcon(warningIcon);
        } else {
            setIcon(errorIcon);
        }

        return this;
    }
}