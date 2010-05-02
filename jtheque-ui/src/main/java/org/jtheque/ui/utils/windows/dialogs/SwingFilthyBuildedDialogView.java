package org.jtheque.ui.utils.windows.dialogs;

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

import org.jtheque.errors.JThequeError;
import org.jtheque.i18n.ILanguageService;
import org.jtheque.ui.able.IModel;
import org.jtheque.ui.utils.builders.FilthyPanelBuilder;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.filthy.FilthyBackgroundPanel;
import org.jtheque.ui.utils.filthy.IFilthyUtils;

import java.awt.Container;
import java.util.Collection;

/**
 * A swing dialog view.
 *
 * @author Baptiste Wicht
 */
public abstract class SwingFilthyBuildedDialogView<T extends IModel> extends SwingDialogView {
    @Override
    protected final void init() {
        initView();

        setContentPane(buildContentPane());

        refreshText(getService(ILanguageService.class));

        pack();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Build the content pane of the view.
     *
     * @return The builded content pane.
     */
    private Container buildContentPane() {
        FilthyBackgroundPanel contentPane = new FilthyBackgroundPanel(getService(IFilthyUtils.class));

        I18nPanelBuilder builder = new FilthyPanelBuilder(contentPane);

        builder.setInternationalizableContainer(this);

        buildView(builder);

        return contentPane;
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
        //Default empty implementation
    }

    @Override
    public T getModel() {
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