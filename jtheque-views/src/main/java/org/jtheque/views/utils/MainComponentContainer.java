package org.jtheque.views.utils;

import org.jtheque.views.components.MainComponent;

import javax.swing.JComponent;

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
 * A simple main component container.
 *
 * @author Baptiste Wicht
 */
public class MainComponentContainer implements MainComponent {
    private final JComponent component;
    private final int position;
    private final String titleKey;

    /**
     * Create a MainComponentContainer.
     *
     * @param component The component.
     * @param position  The position of the main component.
     * @param titleKey  The title key of the main component.
     */
    public MainComponentContainer(JComponent component, int position, String titleKey) {
        super();

        this.component = component;
        this.position = position;
        this.titleKey = titleKey;
    }

    @Override
    public JComponent getImpl() {
        return component;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public String getTitleKey() {
        return titleKey;
    }
}