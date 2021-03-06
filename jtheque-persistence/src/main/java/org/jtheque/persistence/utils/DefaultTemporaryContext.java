package org.jtheque.persistence.utils;

import org.jtheque.persistence.TemporaryContext;
import org.jtheque.utils.annotations.ThreadSafe;

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
 * A temporary context. It's a context for a data who's temporary and not stored somewhere.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public class DefaultTemporaryContext implements TemporaryContext {
    private volatile int id;

    @Override
    public final int getId() {
        return id;
    }

    @Override
    public final void setId(int id) {
        this.id = id;
    }
}