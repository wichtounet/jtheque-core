package org.jtheque.ui.able.components;

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

import org.jtheque.i18n.able.Internationalizable;
import org.jtheque.ui.able.ViewComponent;

import javax.swing.JLabel;

public abstract class I18nLabel extends JLabel implements ViewComponent, Internationalizable {
    /**
     * Set the text key of the label.
     *
     * @param textKey  The i18n key of the message to be display in this label.
     * @param replaces The object to use as replacement for the parameters of the message.
     */
    public abstract void setTextKey(String textKey, Object... replaces);
}
