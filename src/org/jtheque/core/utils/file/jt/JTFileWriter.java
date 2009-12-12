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

import org.jtheque.core.managers.file.able.BasicDataSource;
import org.jtheque.core.utils.file.jt.able.IJTFileWriter;
import org.jtheque.utils.io.FileException;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * A writer for JT File.
 *
 * @author Baptiste Wicht
 */
public abstract class JTFileWriter implements IJTFileWriter {
    @Override
    public final void writeFile(File file, BasicDataSource source) throws FileException {
        try {
            writeFile(new FileOutputStream(file), source);
        } catch (FileNotFoundException e) {
            throw new FileException("File not found", e);
        }
    }

    @Override
    public final void writeFile(String path, BasicDataSource source) throws FileException {
        writeFile(new File(path), source);
    }

    @Override
    public final void writeFile(FileOutputStream stream, BasicDataSource source) throws FileException {
        writeFile(new BufferedOutputStream(stream), source);
    }

    @Override
    public final void writeFile(BufferedOutputStream stream, BasicDataSource source) throws FileException {
        writeFile(new DataOutputStream(stream), source);
    }

    /**
     * Write a JT file to a stream.
     *
     * @param stream The stream to write to.
     * @param source The datasource.
     * @throws FileException Throws when an error occurs during the writing process.
     */
    protected abstract void writeFile(DataOutputStream stream, BasicDataSource source) throws FileException;
}