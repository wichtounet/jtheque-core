package org.jtheque.views.impl.models;

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
import org.jtheque.update.IUpdateManager;
import org.jtheque.update.Updatable;
import org.jtheque.utils.bean.Version;
import org.jtheque.modules.able.Module;
import org.jtheque.views.ViewsServices;

import javax.swing.DefaultComboBoxModel;
import java.util.ArrayList;
import java.util.List;

/**
 * A combo box model to display versions.
 *
 * @author Baptiste Wicht
 */
public final class VersionsComboBoxModel extends DefaultComboBoxModel {
    private Mode mode;
    private Module currentModule;
    private Updatable currentUpdatable;
    private final List<Version> versions = new ArrayList<Version>(5);

    /**
     * The mode of the model.
     */
    enum Mode {
        KERNEL,
        MODULE,
        UPDATABLE
    }

    @Override
    public Object getElementAt(int index) {
        return versions.get(index);
    }

    @Override
    public int getIndexOf(Object object) {
        Version version = (Version) object;

        return versions.indexOf(version);
    }

    @Override
    public int getSize() {
        return versions.size();
    }

    /**
     * Load kernel versions into the model.
     */
    public void loadKernelVersions() {
        if (mode != Mode.KERNEL) {
            mode = Mode.KERNEL;

            versions.clear();
            versions.addAll(ViewsServices.get(IUpdateManager.class).getVersions(ViewsServices.get(ICore.class)));
        }
    }

    /**
     * Load module versions into the model.
     *
     * @param value The module to load the versions from.
     */
    public void loadModuleVersions(Module value) {
        if (mode != Mode.MODULE || !currentModule.equals(value)) {
            mode = Mode.MODULE;
            currentModule = value;

            versions.clear();
            versions.addAll(ViewsServices.get(IUpdateManager.class).getVersions(currentModule));
        }
    }

    /**
     * Load module versions into the model.
     *
     * @param value The module to load the versions from.
     */
    public void loadUpdatableVersions(Updatable value) {
        if (mode != Mode.UPDATABLE || !currentUpdatable.equals(value)) {
            mode = Mode.UPDATABLE;
            currentUpdatable = value;

            versions.clear();
            versions.addAll(ViewsServices.get(IUpdateManager.class).getVersions(currentUpdatable));
        }
    }

    /**
     * Return the selected version.
     *
     * @return The selected version.
     */
    public Version getSelectedVersion() {
        return (Version) getSelectedItem();
    }
}