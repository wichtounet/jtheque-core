package org.jtheque.views.utils;

import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.features.FeatureService;
import org.jtheque.features.Menu;
import org.jtheque.file.FileService;
import org.jtheque.file.ModuleBackuper;
import org.jtheque.modules.ModuleService;
import org.jtheque.modules.SwingLoader;
import org.jtheque.schemas.Schema;
import org.jtheque.schemas.SchemaService;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.components.ConfigTabComponent;
import org.jtheque.views.components.MainComponent;
import org.jtheque.views.components.StateBarComponent;
import org.jtheque.views.Views;

import org.osgi.framework.BundleContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.osgi.context.BundleContextAware;

import javax.annotation.PostConstruct;

import java.util.Collection;

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

public class AbstractModule implements ApplicationContextAware, BundleContextAware, SwingLoader {
    private ApplicationContext applicationContext;
    private BundleContext bundleContext;

    private final String module;
    private final String[] edtBeans;

    private static final String[] EMPTY_EDT_BEANS = {};

    public AbstractModule(String module) {
        this(module, EMPTY_EDT_BEANS);
    }

    public AbstractModule(String module, String[] edtBeans) {
        super();

        this.module = module;
        this.edtBeans = ArrayUtils.copyOf(edtBeans);
    }

    @PostConstruct
    public void register() {
        registerViews();
        registerMenus();
        registerSchema();
        registerBackupers();

        OSGiUtils.getService(bundleContext, ModuleService.class).registerSwingLoader(module, this);
    }

    private void registerViews() {
        SwingUtils.inEdt(new Runnable(){
            @Override
            public void run() {
                addViewComponents();
            }
        });
    }

    private void addViewComponents() {
        Views views = OSGiUtils.getService(bundleContext, Views.class);

        for (StateBarComponent component : getBeans(StateBarComponent.class)) {
            views.addStateBarComponent(module, component);
        }

        for (MainComponent component : getBeans(MainComponent.class)) {
            views.addMainComponent(module, component);
        }

        for (ConfigTabComponent component : getBeans(ConfigTabComponent.class)) {
            views.addConfigTabComponent(module, component);
        }

        for (StateBarComponent component : getBeans(StateBarComponent.class)) {
            views.addStateBarComponent(module, component);
        }
    }

    private void registerMenus() {
        Collection<Menu> menus = getBeans(Menu.class);

        if(!menus.isEmpty()){
            FeatureService featureService = OSGiUtils.getService(bundleContext, FeatureService.class);

            for(Menu menu : menus){
                featureService.addMenu(module, menu);
            }
        }
    }

    private void registerSchema() {
        Collection<Schema> schemas = getBeans(Schema.class);

        if (!schemas.isEmpty()) {
            SchemaService schemaService = OSGiUtils.getService(bundleContext, SchemaService.class);

            for (Schema schema : schemas) {
                schemaService.registerSchema(module, schema);
            }
        }
    }

    private void registerBackupers() {
        Collection<ModuleBackuper> backupers = getBeans(ModuleBackuper.class);

        if (!backupers.isEmpty()) {
            FileService fileService = OSGiUtils.getService(bundleContext, FileService.class);

            for (ModuleBackuper backuper : backupers) {
                fileService.registerBackuper(module, backuper);
            }
        }
    }

    private <T> Collection<T> getBeans(Class<T> type) {
        return applicationContext.getBeansOfType(type).values();
    }

    @Override
    public void afterAll() {
        for (final String bean : edtBeans) {
            SwingUtils.inEdt(new Runnable() {
                @Override
                public void run() {
                    applicationContext.getBean(bean);
                }
            });
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    protected BundleContext getBundleContext() {
        return bundleContext;
    }

    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}