package org.jtheque.core.utils.io.able;

import org.jtheque.core.utils.io.AbstractJTFileHeader;

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
 * A JTheque non zipped file.
 *
 * @author Baptiste Wicht
 */
public interface JTNotZippedFile extends JTFile {
    /**
     * Return the header of the file.
     *
     * @return The JT Header.
     */
    AbstractJTFileHeader getHeader();

    /**
     * Set if the file has the correct separators or not.
     *
     * @param correctSeparators true if the file has correct separators else false.
     */
    void setCorrectSeparators(boolean correctSeparators);

    /**
     * Set if the file has the correct separators or not.
     *
     * @return true if the file has correct separators else false.
     */
    boolean isCorrectSeparators();
}