package org.jtheque.core.utils.io.impl;

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

import org.jtheque.core.utils.io.AbstractJTFileHeader;

/**
 * A header of a JTDFile.
 *
 * @author Baptiste Wicht
 */
public final class JTDFileHeader extends AbstractJTFileHeader {
    @Override
    public boolean isComplete() {
        return getVersionJTheque() != null && getKey() != null && getKey().length() > 0;
    }
}