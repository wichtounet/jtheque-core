package org.jtheque.ui.utils.actions;

import org.jtheque.ui.able.IView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.awt.event.ActionEvent;

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
 * An action to display a view.
 *
 * @author Baptiste Wicht
 */
public final class DisplayBeanViewAction extends JThequeAction implements ApplicationContextAware {
    private final String view;
    private ApplicationContext applicationContext;

    /**
     * Construct a new DisplayViewAction with a specific internationalization key.
     *
     * @param key The i18n key.
     * @param view The name of the view to display. The view will be searched in the Spring context.
     */
    public DisplayBeanViewAction(String key, String view) {
        super(key);

        this.view = view;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((IView)applicationContext.getBean(view)).display();
    }
}