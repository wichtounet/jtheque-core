package org.jtheque.views.utils;

import org.jtheque.features.able.IFeatureService;
import org.jtheque.features.able.Menu;
import org.jtheque.file.able.IFileService;
import org.jtheque.file.able.ModuleBackuper;
import org.jtheque.schemas.able.ISchemaService;
import org.jtheque.schemas.able.Schema;
import org.jtheque.views.able.IViews;
import org.jtheque.views.able.components.ConfigTabComponent;
import org.jtheque.views.able.components.IStateBarComponent;
import org.jtheque.views.able.components.MainComponent;

import org.springframework.beans.factory.config.BeanPostProcessor;

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
 * A bean post processor to register all the resources of a module in the application context. This post processor add
 * automatically state bar components to state bar.
 *
 * @author Baptiste Wicht
 */
public class AutoRegistrationPostProcessor implements BeanPostProcessor {
    private final String module;

    private IViews views;
    private IFeatureService featureService;
    private ISchemaService schemaService;
    private IFileService fileService;

    /**
     * Construct a new AutoRegistrationPostProcessor.
     *
     * @param module The module ID.
     */
    public AutoRegistrationPostProcessor(String module) {
        super();

        this.module = module;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof IStateBarComponent) {
            views.addStateBarComponent(module, (IStateBarComponent) bean);
        } else if (bean instanceof Menu) {
            featureService.addMenu(module, (Menu) bean);
        } else if (bean instanceof ConfigTabComponent) {
            views.addConfigTabComponent(module, (ConfigTabComponent) bean);
        } else if (bean instanceof Schema) {
            schemaService.registerSchema(module, (Schema) bean);
        } else if (bean instanceof MainComponent) {
            views.addMainComponent(module, (MainComponent) bean);
        } else if (bean instanceof ModuleBackuper) {
            fileService.registerBackuper(module, (ModuleBackuper) bean);
        }

        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    /**
     * Set the views.
     *
     * @param views The views.
     */
    public void setViews(IViews views) {
        this.views = views;
    }

    /**
     * Set the feature service.
     *
     * @param featureService The feature service.
     */
    public void setFeatureService(IFeatureService featureService) {
        this.featureService = featureService;
    }

    /**
     * Set the schema service.
     *
     * @param schemaService The schema service.
     */
    public void setSchemaService(ISchemaService schemaService) {
        this.schemaService = schemaService;
    }

    /**
     * Set the file service.
     *
     * @param fileService The file service.
     */
    public void setFileService(IFileService fileService) {
        this.fileService = fileService;
    }
}