package org.jtheque.ui.utils;

import org.jtheque.ui.Action;
import org.jtheque.ui.ControllerException;
import org.jtheque.ui.View;
import org.jtheque.ui.Controller;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.SwingUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

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
 * An abstract controller. It handles all the request, translate them into method name and then execute the method using
 * Reflection.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractController<T extends View> implements Controller<T>, ApplicationContextAware {
    private final Map<String, Method> methodCache = CollectionUtils.newHashMap();
    private final Class<? extends T> viewType;

    private T view;
    private ApplicationContext applicationContext;

    /**
     * Construct a new AbstractController. We must pass the type of the view for the getView() method working
     * correctly.
     *
     * @param viewType The class of the view. Can be an interface. Will be getter from the application context.
     */
    protected AbstractController(Class<? extends T> viewType) {
        super();

        this.viewType = viewType;
    }

    /**
     * Handle the given action. Use the methods declared with the @Action annotation and execute the corresponding
     * method.
     *
     * @param actionName The i18n action name.
     *
     * @throws ControllerException If there is no method associated with the given action or if the action cannot be
     *                             accessed.
     */
    @Override
    public void handleAction(String actionName) {
        Method method = getCachedMethod(actionName);

        if (method == null) {
            throw new ControllerException("There is no method for the action (" + actionName + ')');
        }

        try {
            method.invoke(this);
        } catch (InvocationTargetException e) {
            throw new ControllerException("Unable to invoke the method (" + method + ')', e);
        } catch (IllegalAccessException e) {
            throw new ControllerException("Unable to invoke the method (" + method + ')', e);
        }
    }

    /**
     * Return the cached method corresponding the i18n action name. The methods are put in cache for later usages.
     *
     * @param action The i18n action name.
     *
     * @return The method corresponding to the i18n action name.
     */
    private Method getCachedMethod(String action) {
        if (methodCache.isEmpty()) {
            generateCache();
        }

        if (methodCache.containsKey(action)) {
            return methodCache.get(action);
        } else {
            throw new ControllerException("There is no method for the action (" + action + ')');
        }
    }

    /**
     * Generate the cache of methods.
     */
    private void generateCache() {
        Method[] methods = getClass().getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Action.class)) {
                Action action = method.getAnnotation(Action.class);

                methodCache.put(action.value(), method);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Return the application context of the controller.
     *
     * @return The application context.
     */
    protected ApplicationContext getContext() {
        return applicationContext;
    }

    @Override
    public T getView() {
        SwingUtils.assertEDT("Controller.getView()");

        if (view == null) {
            view = applicationContext.getBean(viewType);
        }

        return view;
    }
}