package org.jtheque.views.impl.actions.author;

import org.jtheque.core.ICore;
import org.jtheque.ui.utils.actions.AbstractBrowseAction;

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
 * Action to inform the author of a bug.
 *
 * @author Baptiste Wicht
 */
public final class AcInformOfABug extends AbstractBrowseAction {
    /**
     * Construct a new AcInformOfABug with a specific key.
     */
    public AcInformOfABug() {
        super("menu.bug");
    }

    @Override
    public String getUrl() {
        return ICore.FORUM_URL;
    }
}