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

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.update.Updatable;
import org.jtheque.core.managers.view.able.update.IUpdateView;
import org.jtheque.core.managers.view.impl.components.model.VersionsComboBoxModel;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.swing.Action;
import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

/**
 * An update view.
 *
 * @author Baptiste Wicht
 */
public final class UpdateView extends SwingDialogView implements IUpdateView {
    private static final long serialVersionUID = -7893962323439572997L;

    private VersionsComboBoxModel model;

    private Mode mode = Mode.KERNEL;
    private ModuleContainer module;
    private Updatable updatable;

    private Action validateAction;
    private Action closeAction;

    /**
     * Construct a new UpdateView.
     *
     * @param frame The parent frame.
     */
    public UpdateView(Frame frame) {
        super(frame);
    }

    /**
     * Build the view.
     */
    @PostConstruct
    public void build() {
        setContentPane(buildContentPane());
        setResizable(false);
        setTitleKey("update.view.title");
        pack();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Build the content pane.
     *
     * @return The content pane.
     */
    private Container buildContentPane() {
        PanelBuilder builder = new PanelBuilder();

        builder.addI18nLabel("update.view.versions", builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL));

        model = new VersionsComboBoxModel();

        builder.addComboBox(model, builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL));

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL), validateAction, closeAction);

        return builder.getPanel();
    }

    @Override
    public void sendMessage(String message, Object value) {
        if ("kernel".equals(message)) {
            mode = Mode.KERNEL;

            model.loadKernelVersions();
        } else if ("module".equals(message)) {
            mode = Mode.MODULE;

            module = (ModuleContainer) value;
            model.loadModuleVersions(module);
        } else if ("updatable".equals(message)) {
            mode = Mode.UPDATABLE;

            updatable = (Updatable) value;
            model.loadUpdatableVersions(updatable);
        }
    }

    @Override
    public Mode getMode() {
        return mode;
    }

    @Override
    public Version getSelectedVersion() {
        return model.getSelectedVersion();
    }

    @Override
    public ModuleContainer getModule() {
        return module;
    }

    @Override
    public Updatable getUpdatable() {
        return updatable;
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
        //Nothing to validate
    }

    /**
     * Set the action to validate the update view. This is not for use, this is only for Spring injection.
     *
     * @param validateAction The action.
     */
    public void setValidateAction(Action validateAction) {
        this.validateAction = validateAction;
    }

    /**
     * Set the action to close the update view. This is not for use, this is only for Spring injection.
     *
     * @param closeAction The action.
     */
    public void setCloseAction(Action closeAction) {
        this.closeAction = closeAction;
    }
}