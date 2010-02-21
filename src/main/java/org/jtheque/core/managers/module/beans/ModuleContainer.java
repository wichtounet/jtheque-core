package org.jtheque.core.managers.module.beans;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.module.annotations.Module;
import org.jtheque.core.managers.update.IUpdateManager;
import org.jtheque.utils.bean.Version;

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

/**
 * @author Baptiste Wicht
 */
public final class ModuleContainer {
    private Object module;
    private final String beanName;

    private final Module infos;

    private BeanMethod plugMethod;
    private BeanMethod prePlugMethod;
    private BeanMethod unPlugMethod;

    private ModuleState state;

    /**
     * Construct a new ModuleContainer.
     *
     * @param beanName The name of the bean.
     * @param infos    The informations about the module.
     */
    public ModuleContainer(String beanName, Module infos) {
        super();

        this.beanName = beanName;
        this.infos = infos;
    }

    /**
     * Return the real module.
     *
     * @return The real module.
     */
    public Object getModule() {
        return module;
    }

    /**
     * Return the plug method.
     *
     * @return The plug method.
     */
    public BeanMethod getPlugMethod() {
        return plugMethod;
    }

    /**
     * Set the plug method.
     *
     * @param plugMethod The plug method.
     */
    public void setPlugMethod(BeanMethod plugMethod) {
        this.plugMethod = plugMethod;
    }

    /**
     * Return the pre plug method.
     *
     * @return The plug method.
     */
    public BeanMethod getPrePlugMethod() {
        return prePlugMethod;
    }

    /**
     * Set the pre plug method.
     *
     * @param prePlugMethod The pre plug method.
     */
    public void setPrePlugMethod(BeanMethod prePlugMethod) {
        this.prePlugMethod = prePlugMethod;
    }

    /**
     * Return the unplug method.
     *
     * @return The plug method.
     */
    public BeanMethod getUnPlugMethod() {
        return unPlugMethod;
    }

    /**
     * Set the unplug method.
     *
     * @param unPlugMethod The unplug method.
     */
    public void setUnPlugMethod(BeanMethod unPlugMethod) {
        this.unPlugMethod = unPlugMethod;
    }

    /**
     * Return the current state of the module.
     *
     * @return The current state of the module.
     */
    public ModuleState getState() {
        return state;
    }

    /**
     * Set the state of the module.
     *
     * @param state The state of the module.
     */
    public void setState(ModuleState state) {
        this.state = state;
    }

    /**
     * Return the infos of the module.
     *
     * @return The infos of the module.
     */
    public Module getInfos() {
        return infos;
    }

    /**
     * Return the name of the bean.
     *
     * @return The name of the bean.
     */
    public String getBeanName() {
        return beanName;
    }

    /**
     * Set the module instance.
     *
     * @param module The module instance.
     */
    public void setModule(Object module) {
        this.module = module;
    }

    /**
     * Return the id of the module.
     *
     * @return The id of the module.
     */
    public String getId() {
        return infos.id();
    }

    /**
     * Return the name key of the module.
     *
     * @return The name key of the module.
     */
    public String getName() {
        return Managers.getManager(ILanguageManager.class).getMessage(infos.id() + ".name");
    }

    /**
     * Return the author key of the module.
     *
     * @return The author key of the module.
     */
    public String getAuthor() {
        return Managers.getManager(ILanguageManager.class).getMessage(infos.id() + ".author");
    }

    /**
     * Return the key description of the module.
     *
     * @return The key description of the module.
     */
    public String getDescription() {
        return Managers.getManager(ILanguageManager.class).getMessage(infos.id() + ".description");
    }

    public Version getMostRecentVersion() {
        return Managers.getManager(IUpdateManager.class).getMostRecentVersion(this);
    }

    @Override
    public String toString() {
        return getName();
    }
}