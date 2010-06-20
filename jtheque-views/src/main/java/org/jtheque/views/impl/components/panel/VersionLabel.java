package org.jtheque.views.impl.components.panel;

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

import org.jtheque.core.able.Versionable;
import org.jtheque.update.able.IUpdateService;

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
     * @param object        The object to display the version for.
     * @param foreground    The foreground color.
     * @param updateService The update service to use.
     */
    public VersionLabel(Versionable object, Color foreground, IUpdateService updateService) {
        super();

        setForeground(foreground);

        setText(updateService.getMostRecentVersion(object).getVersion());
    }
}