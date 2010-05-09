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

import org.jtheque.utils.io.FileException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * A file reader for JTheque File.
 *
 * @author Baptiste Wicht
 */
public interface IJTFileReader {
    /**
     * Read a JT File from a File object.
     *
     * @param file The file to read from.
     * @return The JT File.
     * @throws FileException Throws when an error occurs during the reading.
     */
    JTFile readFile(File file) throws FileException;

    /**
     * Read a JT File from a file path.
     *
     * @param path The path to the file to read from.
     * @return The JT File.
     * @throws FileException Throws when an error occurs during the reading.
     */
    JTFile readFile(String path) throws FileException;

    /**
     * Read a JT File from a stream.
     *
     * @param stream The stream to read from.
     * @return The JT File.
     * @throws FileException Throws when an error occurs during the reading.
     */
    JTFile readFile(FileInputStream stream) throws FileException;

    /**
     * Read a JT File from a stream.
     *
     * @param stream The stream to read from.
     * @return The JT File.
     * @throws FileException Throws when an error occurs during the reading.
     */
    JTFile readFile(BufferedInputStream stream) throws FileException;
}