package org.jtheque.core.utils.io.impl;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
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