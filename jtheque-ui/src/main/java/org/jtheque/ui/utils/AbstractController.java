package org.jtheque.ui.utils;

import org.jtheque.ui.able.IController;
import org.jtheque.ui.able.IView;
import org.jtheque.utils.ui.SwingUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
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
public abstract class AbstractController<T extends IView> implements IController<T>, ApplicationContextAware {
    private final Map<String, Method> methodCache = new HashMap<String, Method>(10);
    private final Map<String, String> translations = new HashMap<String, String>(15);
    private final Class<? extends T> type;

    private T view;
    private ApplicationContext applicationContext;

    protected AbstractController(Class<? extends T> type) {
        super();
        this.type = type;
    }

    @Override
    public void handleAction(String actionName) {
        if (translations.isEmpty()) {
            translations.putAll(getTranslations());
        }

        Method method = getCachedMethod(actionName);

        if (method == null) {
            throw new RuntimeException("There is no method for the action (" + actionName + ')');
        }

        try {
            method.invoke(this);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Unable to invoke the method (" + method + ')', e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to invoke the method (" + method + ')', e);
        }
    }

    /**
     * Return the cached method corresponding the i18n action name. The methods are
     * put in cache for later usages. 
     *
     * @param actionName The i18n action name.
     *
     * @return The method corresponding to the i18n action name.
     */
    private Method getCachedMethod(String actionName) {
        Method method;
        String action = translations.get(actionName);

        if (!translations.containsKey(actionName)) {
            throw new RuntimeException("There is no translation for the action (" + actionName + ')');
        }

        if (methodCache.containsKey(action)) {
            method = methodCache.get(action);
        } else {
            try {
                method = getClass().getDeclaredMethod(action);
                method.setAccessible(true);
                methodCache.put(action, method);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("There is no method for the action (" + actionName + ')');
            }
        }
        return method;
    }

    /**
     * Invalidate the cache of translations. Doing that, you have the guarantee that the first time you
     * handle an action, the cache will be repopulated with the translations of the getTranslations method. 
     */
    protected void invalidateTranslations(){
        translations.clear();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    protected ApplicationContext getContext() {
        return applicationContext;
    }

    @Override
    public T getView() {
        SwingUtils.assertEDT("Controller.getView()");

        if(view == null){
            view = applicationContext.getBean(type);
        }

        return view;
    }

    /**
     * Return the translations of the i18n actions name to the method names.
     *
     * @return A Map containing all the translations of the controller.
     */
    protected abstract Map<String, String> getTranslations();
}