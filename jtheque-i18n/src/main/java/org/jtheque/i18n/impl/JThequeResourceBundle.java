package org.jtheque.i18n.impl;

import org.jtheque.i18n.able.EditableResourceBundle;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Collection;
import java.util.HashSet;

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
 * An editable resource bundle implementation.
 *
 * @author Baptiste Wicht
 */
public final class JThequeResourceBundle extends ReloadableResourceBundleMessageSource implements EditableResourceBundle {
    private final Collection<String> baseNames = new HashSet<String>(10);

    @Override
    public void addBaseName(String baseName) {
        baseNames.add(baseName);

        refresh();
    }

    @Override
    public void removeBaseName(String baseName) {
        baseNames.remove(baseName);

        refresh();
    }

    /**
     * Refresh the resource bundle.
     */
    private void refresh() {
        setBasenames(baseNames.toArray(new String[baseNames.size()]));
    }
}
