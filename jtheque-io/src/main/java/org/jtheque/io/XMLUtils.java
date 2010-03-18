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
