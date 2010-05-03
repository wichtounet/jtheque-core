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
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
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