package org.jtheque.file;

import org.jtheque.utils.SystemProperty;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.FileUtils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import static org.junit.Assert.*;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "jtheque-file-test.xml")
public class FileServiceTest {
    @Resource
    private FileService fileService;

    private static String userDir;

    static {
        ((Logger) LoggerFactory.getLogger("root")).setLevel(Level.ERROR);
    }

    @BeforeClass
    public static void before() {
        userDir = SystemProperty.USER_DIR.get();

        File folder = new File(SystemProperty.JAVA_IO_TMP_DIR.get(), "jtheque");
        folder.mkdirs();

        SystemProperty.USER_DIR.set(folder.getAbsolutePath());
    }

    @AfterClass
    public static void after() {
        FileUtils.delete(new File(SystemProperty.JAVA_IO_TMP_DIR.get(), "jtheque"));

        SystemProperty.USER_DIR.set(userDir);
    }

    @Test
    public void initOK() {
        assertNotNull(fileService);
    }

    @Test
    public void exporters(){
        final AtomicInteger counter = new AtomicInteger(0);

        final Collection<String> datas = Arrays.asList("data1", "data2", "data3");

        fileService.registerExporter("no-module", new Exporter<String>(){
            @Override
            public boolean canExportTo(String fileType) {
                return "xml".equals(fileType);
            }

            @Override
            public void export(String path, Collection<String> exportedDatas) throws FileException {
                counter.incrementAndGet();

                assertEquals("path", path);

                assertEquals(datas, exportedDatas);
            }
        });

        try {
            fileService.exportDatas("no-module", "xml", "path", datas);
        } catch (FileException e) {
            fail("Exception during the export");
        }

        assertEquals(1, counter.get());
    }

    @Test
    public void importers() {
        final AtomicInteger counter = new AtomicInteger(0);

        fileService.registerImporter("no-module", new Importer() {
            @Override
            public boolean canImportFrom(String fileType) {
                return "xml".equals(fileType);
            }

            @Override
            public void importFrom(String path) throws FileException {
                assertEquals("path", path);

                counter.incrementAndGet();
            }
        });

        try {
            fileService.importDatas("no-module", "xml", "path");
        } catch (FileException e) {
            fail("Exception during the export");
        }

        assertEquals(1, counter.get());
    }
}