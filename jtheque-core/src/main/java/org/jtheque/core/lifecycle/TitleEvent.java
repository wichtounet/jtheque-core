package org.jtheque.core.lifecycle;

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

import java.util.EventObject;

/**
 * Event fired when the title has changed.
 *
 * @author Baptiste Wicht
 */
public final class TitleEvent extends EventObject {
    private final String title;

    /**
     * Construct a new Title Event with the new title.
     *
     * @param source The source of the change
     * @param title  The new title
     */
    public TitleEvent(Object source, String title) {
        super(source);

        this.title = title;
    }

    /**
     * Return the new title.
     *
     * @return The new title
     */
    public String getTitle() {
        return title;
    }
}