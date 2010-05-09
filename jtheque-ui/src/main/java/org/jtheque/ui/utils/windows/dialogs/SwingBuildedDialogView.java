package org.jtheque.ui.utils.windows.dialogs;

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

import org.jtheque.errors.JThequeError;
import org.jtheque.i18n.ILanguageService;
import org.jtheque.ui.able.IModel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.JThequePanelBuilder;

import java.awt.Container;
import java.util.Collection;

/**
 * A swing dialog view.
 *
 * @author Baptiste Wicht
 */
public abstract class SwingBuildedDialogView<T extends IModel> extends SwingDialogView {
    @Override
    protected final void init(){
        initView();

        setContentPane(buildContentPane());

        pack();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Build the content pane of the view.
     *
     * @return The builded content pane.
     */
    private Container buildContentPane(){
        I18nPanelBuilder builder = new JThequePanelBuilder();

        builder.setInternationalizableContainer(this);

        buildView(builder);

        refreshText(getService(ILanguageService.class));

        return builder.getPanel();
    }

    @Override
    protected void validate(Collection<JThequeError> errors){
        //Default empty implementation
    }

    @Override
    public T getModel(){
        return (T) super.getModel();
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