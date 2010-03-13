package org.jtheque.core.utils.io.able;

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

import org.jtheque.core.utils.io.BasicDataSource;
import org.jtheque.utils.io.FileException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * A file writer for JTheque File.
 *
 * @author Baptiste Wicht
 */
public interface IJTFileWriter {
    /**
     * Write a JT file to a File object.
     *
     * @param file   The file to write to.
     * @param source The datasource.
     * @throws FileException Throws when an error occurs during the writing process.
     */
    void writeFile(File file, BasicDataSource source) throws FileException;

    /**
     * Write a JT file to a file path.
     *
     * @param path   The path of the file to write to.
     * @param source The datasource.
     * @throws FileException Throws when an error occurs during the writing process.
     */
    void writeFile(String path, BasicDataSource source) throws FileException;

    /**
     * Write a JT file to a stream.
     *
     * @param stream The stream to write to.
     * @param source The datasource.
     * @throws FileException Throws when an error occurs during the writing process.
     */
    void writeFile(FileOutputStream stream, BasicDataSource source) throws FileException;

    /**
     * Write a JT file to a stream.
     *
     * @param stream The stream to write to.
     * @param source The datasource.
     * @throws FileException Throws when an error occurs during the writing process.
     */
    void writeFile(BufferedOutputStream stream, BasicDataSource source) throws FileException;
}