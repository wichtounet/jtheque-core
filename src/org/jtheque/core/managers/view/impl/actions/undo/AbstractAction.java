package org.jtheque.core.managers.view.impl.actions.undo;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;

import javax.swing.Action;
import javax.swing.KeyStroke;
import java.awt.Toolkit;

/**
 * An abstract undo action.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractAction extends JThequeAction {
    /**
     * Construct a new AbstractAction.
     *
     * @param key         The internationalization key.
     * @param icon        The icon name.
     * @param accelerator The accelerator key.
     */
    AbstractAction(String key, String icon, int accelerator) {
        super(key);

        setIcon(Managers.getManager(IResourceManager.class).getIcon(
                Managers.getCore().getImagesBaseName(), icon, ImageType.PNG));

        putValue(Action.ACCELERATOR_KEY
                , KeyStroke.getKeyStroke(accelerator, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }
}
