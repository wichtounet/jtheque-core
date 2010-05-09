package org.jtheque.core.utils.io.able;

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
 * A JTheque file.
 *
 * @author Baptiste Wicht
 */
public interface JTFile {
    String JT_KEY = "1W@JTHEQUE@W1";
    long JT_CATEGORY_SEPARATOR = -89569876428459544L;

    /**
     * Indicate if the file is valid.
     *
     * @return true if the file is valid else false.
     */
    boolean isValid();
}