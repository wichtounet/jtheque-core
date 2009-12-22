package org.jtheque.core.managers.view.impl.frame;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.able.ILicenceView;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.swing.Action;
import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

/**
 * A view to display the licence.
 *
 * @author Baptiste Wicht
 */
public final class LicenceView extends SwingDialogView implements ILicenceView {
    private Action printAction;
    private Action closeAction;

    private int defaultWidth;
    private int defaultHeight;

    /**
     * Construct a new Licence view.
     *
     * @param frame The parent frame.
     */
    public LicenceView(Frame frame) {
        super(frame);
    }

    /**
     * Build the view.
     */
    @PostConstruct
    public void build() {
        setTitle(getMessage("licence.view.title", Managers.getCore().getApplication().getName()));
        setContentPane(buildContentPane());

        Managers.getManager(IViewManager.class).configureView(this, "licence", defaultWidth, defaultHeight);
    }

    /**
     * Build the content pane.
     *
     * @return The content pane.
     */
    private Container buildContentPane() {
        PanelBuilder builder = new PanelBuilder();

        builder.addScrolledTextArea(FileUtils.getTextOf(Managers.getCore().getApplication().getLicenceFilePath()),
                builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.BELOW_BASELINE_LEADING, 1.0, 1.0));
        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL), printAction, closeAction);

        return builder.getPanel();
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
        //Nothing to validate
    }

    @Override
    public void refreshText() {
        setTitle(getMessage("licence.view.title", Managers.getCore().getApplication().getName()));
    }

    /**
     * Set the action to launch to print the licence. This is not for use, this is only for Spring injection.
     *
     * @param printAction The action.
     */
    public void setPrintAction(Action printAction) {
        this.printAction = printAction;
    }

    /**
     * Set the action to launch to close the view. This is not for use, this is only for Spring injection.
     *
     * @param closeAction The action.
     */
    public void setCloseAction(Action closeAction) {
        this.closeAction = closeAction;
    }

    /**
     * Set the default width. This is not for use, this is only for Spring injection.
     *
     * @param defaultWidth The default width.
     */
    public void setDefaultWidth(int defaultWidth) {
        this.defaultWidth = defaultWidth;
    }

    /**
     * Set the default height. This is not for use, this is only for Spring injection.
     *
     * @param defaultHeight The default height.
     */
    public void setDefaultHeight(int defaultHeight) {
        this.defaultHeight = defaultHeight;
    }
}