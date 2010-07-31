package org.jtheque.events.able;

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

import org.jtheque.utils.annotations.Immutable;

import java.util.Date;

/**
 * An event specification.
 *
 * @author Baptiste Wicht
 */
@Immutable
public interface Event {
    /**
     * Return the log of the event.
     *
     * @return The log of the event.
     */
    String getLog();

    /**
     * Return the details internationalization key.
     *
     * @return The details internationalization key.
     */
    String getDetailsKey();

    /**
     * Return the level of the event.
     *
     * @return The level of the event.
     */
    EventLevel getLevel();

    /**
     * Return the date of the event.
     *
     * @return The date of the event.
     */
    Date getDate();

    /**
     * Return the source of the event.
     *
     * @return The source of the event.
     */
    String getSource();

    /**
     * Return the title internationalization key.
     *
     * @return The title internationalization key.
     */
    String getTitleKey();
}
