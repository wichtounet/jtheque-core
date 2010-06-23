package org.jtheque.i18n.able;

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
 * An internationalizable container. It's a internationalize who contains also some internationalizables and are
 * responsible to internationalize all the childs.
 *
 * @author Baptiste Wicht
 */
public interface InternationalizableContainer extends Internationalizable {
    /**
     * Add an internationalizable to the container.
     *
     * @param internationalizable The internationalizable to add.
     */
    void addInternationalizable(Internationalizable internationalizable);
}