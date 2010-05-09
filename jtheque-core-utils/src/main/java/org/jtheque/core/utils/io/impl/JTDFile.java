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
import org.jtheque.core.utils.io.able.JTNotZippedFile;

import java.io.DataInputStream;

/**
 * A JTD File.
 *
 * @author Baptiste Wicht
 */
public final class JTDFile implements JTNotZippedFile {
    //Correct separators
    private boolean correctSeparators;

    private DataInputStream stream;

    //Header
    private AbstractJTFileHeader header;

    /**
     * Construct a new JTDFile.
     */
    public JTDFile() {
        super();

        header = new JTDFileHeader();
    }

    @Override
    public AbstractJTFileHeader getHeader() {
        return header;
    }

    /**
     * Set the header of the file.
     *
     * @param header The new header to set.
     */
    void setHeader(AbstractJTFileHeader header) {
        this.header = header;
    }

    /**
     * Return the stream to the file.
     *
     * @return The stream to the file.
     */
    public DataInputStream getStream() {
        return stream;
    }

    /**
     * Set the stream.
     *
     * @param stream The stream to the file.
     */
    public void setStream(DataInputStream stream) {
        this.stream = stream;
    }

    @Override
    public boolean isValid() {
        return header.isComplete();
    }

    @Override
    public void setCorrectSeparators(boolean correctSeparators) {
        this.correctSeparators = correctSeparators;
    }

    @Override
    public boolean isCorrectSeparators() {
        return correctSeparators;
    }
}