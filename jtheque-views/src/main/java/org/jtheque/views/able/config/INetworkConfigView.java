package org.jtheque.views.able.config;

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

import javax.swing.JCheckBox;
import javax.swing.JComponent;

/**
 * A config view for network specification.
 *
 * @author Baptiste Wicht
 */
public interface INetworkConfigView {
    /**
     * Return the text field for the port number of the proxy.
     *
     * @return The text field for the port number of the proxy.
     */
    JComponent getFieldPort();

    /**
     * Return the text field for the address of the proxy.
     *
     * @return The text field for the address of the proxy.
     */
    JComponent getFieldAddress();

    /**
     * Return the check box who indicates if we use or not a proxy.
     *
     * @return The check box indicating whether we use a proxy or not.
     */
    JCheckBox getBoxProxy();
}
