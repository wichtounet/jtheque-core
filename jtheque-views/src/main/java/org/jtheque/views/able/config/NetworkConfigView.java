package org.jtheque.views.able.config;

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

import javax.swing.JCheckBox;
import javax.swing.JComponent;

/**
 * A config view for network specification.
 *
 * @author Baptiste Wicht
 */
public interface NetworkConfigView {
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
