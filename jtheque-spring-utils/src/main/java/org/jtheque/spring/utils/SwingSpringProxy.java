package org.jtheque.spring.utils;

import org.jtheque.utils.ui.SwingUtils;
import org.springframework.context.ApplicationContext;

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

public class SwingSpringProxy<T> {
    private final Class<T> classz;
    private final ApplicationContext applicationContext;

    private T instance;

    public SwingSpringProxy(Class<T> classz, ApplicationContext applicationContext){
        super();


        this.classz = classz;
        this.applicationContext = applicationContext;
    }

    public T get(){
        if(instance == null){
            createInstance();
        }

        return instance;
    }

    private void createInstance() {
        SwingUtils.inEdtSync(new Runnable(){
            @Override
            public void run() {
                instance = applicationContext.getBean(classz);
            }
        });
    }
}