package org.jtheque.core.utils.impl;

import org.jtheque.core.utils.WebHelper;
import org.jtheque.errors.ErrorService;
import org.jtheque.errors.Errors;
import org.jtheque.events.EventLevel;
import org.jtheque.events.EventService;
import org.jtheque.events.Events;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.io.WebUtils;

import javax.annotation.Resource;

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
 * A simple helper to manage services who needs web access.
 *
 * @author Baptiste Wicht
 */
public class SimpleWebHelper implements WebHelper {
    @Resource
    private EventService eventService;

    @Resource
    private ErrorService errorService;

    @Override
    public boolean isReachable(String url) {
        if (WebUtils.isURLReachable(url)) {
            return true;
        }

        addNotReachableError(url);

        eventService.addEvent(
                Events.newEvent(EventLevel.ERROR, "System", "events.network.error", EventService.CORE_EVENT_LOG));

        return false;
    }

    /**
     * Add a not reachable error to the errors.
     *
     * @param url the not reachable URL.
     */
    private void addNotReachableError(String url) {
        if (WebUtils.isInternetReachable()) {
            errorService.addError(Errors.newI18nError(
                    "network.resource.title", ArrayUtils.EMPTY_ARRAY,
                    "network.resource", new Object[]{url}));
        } else {
            errorService.addError(Errors.newI18nError(
                    "network.internet.title", ArrayUtils.EMPTY_ARRAY,
                    "network.internet", new Object[]{url}));
        }
    }
}