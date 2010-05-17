package org.jtheque.ui.utils.builded;

import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.i18n.able.Internationalizable;
import org.jtheque.i18n.able.InternationalizableContainer;
import org.jtheque.ui.able.IModel;
import org.jtheque.ui.able.IView;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.JThequePanelBuilder;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;
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

public abstract class BuildedPanel extends JPanel implements InternationalizableContainer, IView {
    private final Collection<Internationalizable> internationalizables = new ArrayList<Internationalizable>(15);

	private ILanguageService languageService;

	private IModel model;

	protected BuildedPanel(ILanguageService languageService) {
		super();

		this.languageService = languageService;
	}

	protected BuildedPanel() {
		super();
	}

	@PostConstruct
    public final void build(){
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

	protected ILanguageService getLanguageService(){
		return languageService;
	}

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

    @Override
    public void display(){
        //Nothing by default
    }

    @Override
    public void closeDown(){
        //Nothing by default
    }

    @Override
    public void toFirstPlan(){
        //Nothing by default
    }

    @Override
    public void sendMessage(String message, Object value){
        //Nothing by default
    }

    @Override
    public void refresh(){
        //Nothing by default
    }

    @Override
    public JComponent getImpl(){
        return this;
    }

    @Override
    public IModel getModel(){
        return model;
    }

    @Override
    public boolean validateContent(){
        return true;
    }

    /**
     * Set the model.
     *
     * @param model The model of the view.
     */
    protected void setModel(IModel model){
        this.model = model;
    }
}