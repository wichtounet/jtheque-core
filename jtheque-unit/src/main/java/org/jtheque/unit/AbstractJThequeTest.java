package org.jtheque.unit;

import org.jtheque.utils.SystemProperty;
import org.jtheque.utils.io.FileUtils;

import org.junit.AfterClass;
import org.slf4j.LoggerFactory;

import java.io.File;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import static org.junit.Assert.fail;

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
 * A simple abstract JTheque test.
 *
 * @author Baptiste Wicht
 */
public class AbstractJThequeTest {
    static {
        ((Logger) LoggerFactory.getLogger("root")).setLevel(Level.ERROR);

        SystemProperty.USER_DIR.set(getFolder(SystemProperty.JAVA_IO_TMP_DIR, "jtheque").getAbsolutePath());

        for (File f : new File(SystemProperty.USER_DIR.get()).listFiles()) {
            FileUtils.delete(f);
        }

        getSubFolder("core");
        getSubFolder("lib");
        getSubFolder("modules");
        getSubFolder("application");
    }

    /**
     * Return the sub folder in the JTheque folder.
     *
     * @param folderName The name of the folder.
     *
     * @return The sub folder.
     */
    static File getSubFolder(String folderName) {
        return getFolder(SystemProperty.USER_DIR, folderName);
    }

    /**
     * Return the folder in the given dir.
     *
     * @param dir    The dir to search into.
     * @param folder The folder to search.
     *
     * @return The folder in the dir.
     */
    private static File getFolder(SystemProperty dir, String folder) {
        File f = new File(dir.get(), folder);

        if (!f.exists() && !f.mkdirs()) {
            fail();
        }

        return f;
    }

    @AfterClass
    public static void after() {
        new File(SystemProperty.USER_DIR.get()).deleteOnExit();
    }
}