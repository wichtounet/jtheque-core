package org.jtheque.ui.utils.builded;

import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.errors.able.IError;
import org.jtheque.errors.able.IErrorService;
import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.spring.utils.SwingSpringProxy;
import org.osgi.framework.BundleContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.osgi.context.BundleContextAware;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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

public abstract class OSGIBuildedPanel extends BuildedPanel implements BundleContextAware, ApplicationContextAware {
	private BundleContext bundleContext;
	private ApplicationContext applicationContext;

	@PostConstruct
	public void init(){
		build();
	}

	@Override
	protected ILanguageService getLanguageService() {
		return getService(ILanguageService.class);
	}

	@Override
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

    protected <T> T getService(Class<T> classz){
        return OSGiUtils.getService(bundleContext, classz);
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

    protected <T> T getBean(Class<T> classz){
        return applicationContext.getBean(classz);
    }

	protected <T> T getBeanFromEDT(Class<T> classz){
        return new SwingSpringProxy<T>(classz, applicationContext).get();
    }

	@Override
	public boolean validateContent() {
		Collection<IError> errors = new ArrayList<IError>(5);

		validate(errors);

		for (IError error : errors) {
			getService(IErrorService.class).addError(error);
		}

		return errors.isEmpty();
	}

	protected void validate(Collection<IError> errors){
		//Default empty implementation
	}
}