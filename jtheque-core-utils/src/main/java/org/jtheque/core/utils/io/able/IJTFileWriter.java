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