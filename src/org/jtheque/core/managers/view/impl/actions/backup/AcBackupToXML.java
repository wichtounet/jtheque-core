package org.jtheque.core.managers.view.impl.actions.backup;

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

import org.jtheque.core.managers.file.able.FileType;
import org.jtheque.utils.io.SimpleFilter;

/**
 * Action to backup to XML.
 *
 * @author Baptiste Wicht
 */
public final class AcBackupToXML extends AcBackup {
    /**
     * Construct a new AcBackupToXML.
     */
    public AcBackupToXML() {
        super("file.actions.to.xml", new SimpleFilter("XML(*.xml)", ".xml"), FileType.XML);
    }
}