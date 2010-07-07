package org.jtheque.ui.utils;

import org.jtheque.ui.able.IController;

import org.slf4j.LoggerFactory;

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
 * An abstract controller. It handles all the request, translate them into method name and
 * then execute the method using Reflection.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractController implements IController {
    private final Map<String, Method> methodCache = new HashMap<String, Method>(10);
    private final Map<String, String> translations = new HashMap<String, String>(15);

    @Override
    public void handleAction(String actionName) {
        if(translations.isEmpty()){
            translations.putAll(getTranslations());
        }

        String action = translations.get(actionName);

        if (!methodCache.containsKey(action)) {
            try {
                Method method = getClass().getDeclaredMethod(action);
                method.setAccessible(true);
                methodCache.put(action, method);
            } catch (NoSuchMethodException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }
        }

        try {
            methodCache.get(action).invoke(this);
        } catch (InvocationTargetException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
    }

    /**
     * Return the translations of the i18n actions name to the method names.
     *
     * @return A Map containing all the translations of the controller. 
     */
    protected abstract Map<String, String> getTranslations();
}