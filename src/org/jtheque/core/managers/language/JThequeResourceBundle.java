package org.jtheque.core.managers.language;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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

/**
 * An editable resource bundle implementation.
 *
 * @author Baptiste Wicht
 */
public final class JThequeResourceBundle extends ReloadableResourceBundleMessageSource implements EditableResourceBundle {
    private String[] coreBaseNames;

    private final Collection<String> baseNames = new ArrayList<String>(10);

    /**
     * Init the JTheque resource bundle.
     */
    @PostConstruct
    public void init() {
        baseNames.addAll(Arrays.asList(coreBaseNames));

        refresh();
    }

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

    /**
     * Set the base names of the core.
     *
     * @param coreBaseNames An array of all the base names of the core.
     */
    public void setCoreBaseNames(String[] coreBaseNames) {
        this.coreBaseNames = Arrays.copyOf(coreBaseNames, coreBaseNames.length);
    }
}
