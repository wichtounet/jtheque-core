package org.jtheque.ui.utils.windows;

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

/**
 * A JTheque managed window.
 *
 * @author Baptiste Wicht
 */
public interface ManagedWindow {
    /**
     * Build the view.
     */
    void build();

    /**
     * Set the title of the window.
     *
     * @param title The title of the window. 
     */
    void setTitle(String title);

    /**
     * Validate the view and save all the validation's errors in the list.
     *
     * @param errors The error's list.
     */
    void validate(Collection<org.jtheque.errors.Error> errors);
}