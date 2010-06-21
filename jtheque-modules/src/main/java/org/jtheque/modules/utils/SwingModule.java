package org.jtheque.modules.utils;

import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.ui.SwingUtils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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

public class SwingModule implements ApplicationContextAware{
    private final String[] edtBeans;

    public SwingModule(String[] edtBeans) {
        super();

        this.edtBeans = ArrayUtils.copyOf(edtBeans);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        SwingUtils.inEdt(new Runnable(){
            @Override
            public void run() {
                for(String bean : edtBeans){
                    applicationContext.getBean(bean);
                }
            }
        });
    }
}