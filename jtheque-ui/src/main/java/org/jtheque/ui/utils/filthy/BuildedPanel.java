package org.jtheque.ui.utils.filthy;

import org.jtheque.i18n.ILanguageService;
import org.jtheque.i18n.Internationalizable;
import org.jtheque.i18n.InternationalizableContainer;
import org.jtheque.ui.utils.builders.FilthyPanelBuilder;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;

import javax.swing.JPanel;
import java.util.ArrayList;
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

public abstract class BuildedPanel extends JPanel implements InternationalizableContainer{
    private final Collection<Internationalizable> internationalizables = new ArrayList<Internationalizable>(15);
    private final ILanguageService languageService;

    public BuildedPanel(ILanguageService languageService) {
        super();

        this.languageService = languageService;
    }

    protected final void build(){
        languageService.addInternationalizable(this);

        I18nPanelBuilder panelBuilder = new FilthyPanelBuilder(this);
        panelBuilder.setInternationalizableContainer(this);

        buildView(panelBuilder);

        refreshText(languageService);
    }

    /**
     * Init the view.
     *
     * @param builder The builder.
     */
    protected abstract void buildView(I18nPanelBuilder builder);

    @Override
    public void addInternationalizable(Internationalizable internationalizable) {
        internationalizables.add(internationalizable);
    }

    @Override
    public void refreshText(ILanguageService languageService) {
        for (Internationalizable internationalizable : internationalizables) {
            internationalizable.refreshText(languageService);
        }
    }
}