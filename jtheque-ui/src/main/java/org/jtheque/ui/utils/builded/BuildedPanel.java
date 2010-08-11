package org.jtheque.ui.utils.builded;

import org.jtheque.i18n.LanguageService;
import org.jtheque.i18n.Internationalizable;
import org.jtheque.i18n.InternationalizableContainer;
import org.jtheque.ui.Model;
import org.jtheque.ui.utils.AbstractPanelView;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.JThequePanelBuilder;
import org.jtheque.utils.collections.CollectionUtils;

import javax.annotation.PostConstruct;

import java.util.Collection;

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
 * A builded panel. This panel is builded using a panel builder.
 *
 * @author Baptiste Wicht
 */
public abstract class BuildedPanel extends AbstractPanelView implements InternationalizableContainer {
    private final Collection<Internationalizable> internationalizables = CollectionUtils.newList(15);

    private LanguageService languageService;
    private Model model;

    /**
     * Construct a new builded panel.
     *
     * @param languageService The language service to use.
     */
    protected BuildedPanel(LanguageService languageService) {
        super();

        this.languageService = languageService;
    }

    /**
     * Construct a new builded panel providing no internationalizable service, so the class must override the
     * getLanguageService() method.
     */
    protected BuildedPanel() {
        super();
    }

    /**
     * Build the view. Called by spring after construct.
     */
    @PostConstruct
    public final void build() {
        getLanguageService().addInternationalizable(this);

        I18nPanelBuilder panelBuilder = new JThequePanelBuilder(this);
        panelBuilder.setInternationalizableContainer(this);

        buildView(panelBuilder);

        refreshText(getLanguageService());
    }

    /**
     * Init the view.
     *
     * @param builder The builder.
     */
    protected abstract void buildView(I18nPanelBuilder builder);

    /**
     * Return the language service to use to build the panel.
     *
     * @return The language service to build the panel.
     */
    protected LanguageService getLanguageService() {
        return languageService;
    }

    @Override
    public void addInternationalizable(Internationalizable internationalizable) {
        internationalizables.add(internationalizable);
    }

    @Override
    public void refreshText(LanguageService languageService) {
        for (Internationalizable internationalizable : internationalizables) {
            internationalizable.refreshText(languageService);
        }
    }

    @Override
    public Model getModel() {
        return model;
    }

    /**
     * Set the model.
     *
     * @param model The model of the view.
     */
    protected void setModel(Model model) {
        this.model = model;
    }
}