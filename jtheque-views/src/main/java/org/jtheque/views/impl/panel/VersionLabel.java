package org.jtheque.views.impl.panel;

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

import org.jtheque.update.IUpdateManager;
import org.jtheque.views.ViewsServices;

import javax.swing.JLabel;
import java.awt.Color;

/**
 * A label to display the version of the kernel.
 *
 * @author Baptiste Wicht
 */
public final class VersionLabel extends JLabel {
    /**
     * Construct a new KernelVersionLabel.
     *
     * @param object     The object to display the version for.
     * @param foreground The foreground color.
     */
    public VersionLabel(Object object, Color foreground) {
        super();

        setForeground(foreground);

        setText(ViewsServices.get(IUpdateManager.class).getMostRecentVersion(object).getVersion());
    }
}