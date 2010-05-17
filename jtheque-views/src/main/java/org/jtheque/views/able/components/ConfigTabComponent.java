package org.jtheque.views.able.components;

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

import org.jtheque.ui.utils.constraints.Constraint;

import javax.swing.JComponent;
import java.util.Map;

/**
 * A config panel.
 *
 * @author Baptiste Wicht
 */
public interface ConfigTabComponent {
    /**
     * Return the title of the config tab.
     *
     * @return The title of the tab.
     */
    String getTitleKey();

    /**
     * Apply all the changes.
     */
    void apply();

    /**
     * Cancel all the changes.
     */
    void cancel();

    /**
     * Return the implementation of the config tab.
     *
     * @return The implementation of the config tab.
     */
    JComponent getComponent();

	Map<Object, Constraint> getConstraints();
}