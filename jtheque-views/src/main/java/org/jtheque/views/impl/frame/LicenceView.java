package org.jtheque.views.impl.frame;

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

import org.jtheque.core.ICore;
import org.jtheque.ui.able.IModel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.windows.frames.SwingFilthyBuildedDialogView;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.ViewsServices;
import org.jtheque.views.able.IViewManager;
import org.jtheque.views.able.windows.ILicenceView;
import org.jtheque.views.impl.actions.about.PrintLicenseAction;

/**
 * A view to display the licence.
 *
 * @author Baptiste Wicht
 */
public final class LicenceView extends SwingFilthyBuildedDialogView<IModel> implements ILicenceView {
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    /**
     * Construct a new LicenceView. 
     */
    public LicenceView(){
        super();

        build();
    }

    @Override
    protected void initView(){
        setTitleKey("licence.view.title", ViewsServices.get(ICore.class).getApplication().getName());
    }

    @Override
    protected void buildView(I18nPanelBuilder builder){
        builder.addScrolledTextArea(FileUtils.getTextOf(ViewsServices.get(ICore.class).getApplication().getLicenceFilePath()),
                builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.BELOW_BASELINE_LEADING, 1.0, 1.0));

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL),
                new PrintLicenseAction(), getCloseAction("licence.actions.close"));

        ViewsServices.get(IViewManager.class).configureView(this, "licence", DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}