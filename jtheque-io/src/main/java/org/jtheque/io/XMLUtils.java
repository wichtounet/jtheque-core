package org.jtheque.io;

import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jtheque.utils.io.FileUtils;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
 * An utility class for XML.
 *
 * @author Baptiste Wicht
 */
public final class XMLUtils {
    /**
     * This is an utility class, not instanciable.
     */
    private XMLUtils() {
        super();
    }

    /**
     * Save a XML file.
     *
     * @param doc  The XML document to save.
     * @param path The path to the file.
     */
    public static void writeXml(Document doc, String path) {
        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());

        boolean fileOk = true;

        File f = new File(path);

        if (!f.exists()) {
            try {
                fileOk = f.createNewFile();
            } catch (IOException e) {
                fileOk = false;
                LoggerFactory.getLogger(XMLUtils.class).error(e.getMessage(), e);
            }
        }

        if (fileOk) {
            OutputStream stream = null;
            try {
                stream = new FileOutputStream(path);

                sortie.output(doc, stream);
            } catch (FileNotFoundException e) {
                LoggerFactory.getLogger(XMLUtils.class).error(e.getMessage(), e);
            } catch (IOException e) {
                LoggerFactory.getLogger(XMLUtils.class).error(e.getMessage(), e);
            } finally {
                FileUtils.close(stream);
            }
        }
    }
}
