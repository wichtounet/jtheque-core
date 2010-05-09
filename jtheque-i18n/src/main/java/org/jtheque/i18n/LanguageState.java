package org.jtheque.i18n;

import org.jtheque.states.AbstractState;
import org.jtheque.states.State;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.Version;

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
 * The persistent state of the language. This class must be public, it will be accessed with reflection.
 *
 * @author Baptiste Wicht
 */
@State(id = "jtheque-i18n-configuration")
public final class LanguageState extends AbstractState {
    /**
     * Set the language.
     *
     * @param language The language.
     */
    public void setLanguage(String language) {
        setProperty("language", language);
    }

    /**
     * Return the language of the state.
     *
     * @return The language.
     */
    public String getLanguage() {
        return getProperty("language", "fr");
    }

    public Version getResourceVersion(String resource){
        String property = getProperty(resource + "_version");

	    return StringUtils.isEmpty(property) ? null : new Version(property);
    }

    public void setResourceVersion(String resource, Version version){
        setProperty(resource + "_version", version.toString());
    }
}