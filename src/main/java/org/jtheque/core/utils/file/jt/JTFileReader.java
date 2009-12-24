package org.jtheque.core.utils.file.jt;

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

import org.jtheque.core.utils.file.jt.able.IJTFileReader;
import org.jtheque.core.utils.file.jt.able.JTFile;
import org.jtheque.utils.io.FileException;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * A reader for JT Files.
 *
 * @author Baptiste Wicht
 */
public abstract class JTFileReader implements IJTFileReader {
    @Override
    public final JTFile readFile(File file) throws FileException {
        try {
            return readFile(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new FileException("File not found", e);
        }
    }

    @Override
    public final JTFile readFile(String path) throws FileException {
        return readFile(new File(path));
    }

    @Override
    public final JTFile readFile(FileInputStream stream) throws FileException {
        return readFile(new BufferedInputStream(stream));
    }

    @Override
    public final JTFile readFile(BufferedInputStream stream) throws FileException {
        return readFile(new DataInputStream(stream));
    }

    /**
     * Read a JT File.
     *
     * @param stream The data input stream to read from.
     * @return The read JT File.
     * @throws FileException Throws when the file is corrupted.
     */
    protected abstract JTFile readFile(DataInputStream stream) throws FileException;
}