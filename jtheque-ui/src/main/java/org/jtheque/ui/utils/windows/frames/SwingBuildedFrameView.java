package org.jtheque.ui.utils.windows.frames;

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

import org.jtheque.i18n.LanguageService;
import org.jtheque.ui.Model;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.JThequePanelBuilder;

import javax.swing.JComponent;

/**
 * A swing dialog view.
 *
 * @author Baptiste Wicht
 */
public abstract class SwingBuildedFrameView<T extends Model> extends SwingFrameView<T> {
    @Override
    protected final void init() {
        initView();

        setContentPane(buildContentPane());

        refreshText(getService(LanguageService.class));

        pack();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Build the content pane of the view.
     *
     * @return The builded content pane.
     */
    private JComponent buildContentPane() {
        I18nPanelBuilder builder = createBuilder();

        builder.setInternationalizableContainer(this);

        buildView(builder);

        refreshText(getService(LanguageService.class));

        return builder.getPanel();
    }

    /**
     * Create the builder for the window.
     *
     * @return The panel builder of the window.
     */
    JThequePanelBuilder createBuilder() {
        return new JThequePanelBuilder();
    }

    /**
     * Init the view.
     */
    protected abstract void initView();

    /**
     * Build the view.
     *
     * @param builder The builder to use to build the content pane.
     */
    protected abstract void buildView(I18nPanelBuilder builder);
}