package org.jtheque.views.windows;

import java.awt.Component;

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
 * An about view specification.
 *
 * @author Baptiste Wicht
 */
public interface AboutView {
    /**
     * Make the view appears.
     */
    void appear();

    /**
     * Return the implementation of the view.
     *
     * @return The implementation of the view.
     */
    Component getImpl();
}
