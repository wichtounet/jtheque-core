package org.jtheque.file;

import org.jtheque.core.Core;
import org.jtheque.file.FileService.XmlBackupVersion;
import org.jtheque.utils.SystemProperty;
import org.jtheque.utils.bean.IntDate;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.xml.utils.XML;
import org.jtheque.xml.utils.XMLException;
import org.jtheque.xml.utils.XMLReader;
import org.jtheque.xml.utils.XMLWriter;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    @Test
    @DirtiesContext
    public void restore(){
        File backupFile = new File(SystemProperty.USER_DIR.get(), "backup.xml");

        createFakeBackupFile(backupFile);

        final AtomicInteger counter = new AtomicInteger(0);

        fileService.registerBackuper("no-module", new ModuleBackuper(){
            @Override
            public String getId() {
                return "test-backup";
            }

            @Override
            public String[] getDependencies() {
                return new String[0];
            }

            @Override
            public ModuleBackup backup() {
                fail("Backup must not be called");

                return null;
            }

            @Override
            public void restore(ModuleBackup backup) {
                assertEquals("test-backup", backup.getId());
                assertEquals(Version.get("1.0"), backup.getVersion());

                assertEquals(1, backup.getNodes().size());

                for(org.jtheque.xml.utils.Node node : backup.getNodes()){
                    assertEquals("simple", node.getName());
                    assertEquals("true", node.getAttributeValue("test"));
                }

                counter.incrementAndGet();
            }
        });

        try {
            fileService.restore(backupFile);
        } catch (XMLException e) {
            fail(e.getMessage());
        }

        assertEquals(1, counter.get());
    }

    @Test
    @DirtiesContext
    public void backup(){
        File restoreFile = new File(SystemProperty.USER_DIR.get(), "restore.xml");

        fileService.registerBackuper("no-module", new ModuleBackuper(){
            @Override
            public String getId() {
                return "test-backup";
            }

            @Override
            public String[] getDependencies() {
                return new String[0];
            }

            @Override
            public ModuleBackup backup() {
                Collection<org.jtheque.xml.utils.Node> nodes = new ArrayList<org.jtheque.xml.utils.Node>(1);

                org.jtheque.xml.utils.Node node = new org.jtheque.xml.utils.Node("element");
                node.setAttribute("test", "true");
                nodes.add(node);

                return new ModuleBackup(Version.get("1.0"), "test-backup", nodes);
            }

            @Override
            public void restore(ModuleBackup backup) {
                fail("Restore must not be called");
            }
        });

        fileService.backup(restoreFile);

        XMLReader<Node> reader = XML.newJavaFactory().newReader();

        try {
            reader.openFile(restoreFile);
        } catch (XMLException e) {
            fail(e.getMessage());
        }

        try {
            Collection<Node> nodes = reader.getNodes("backup", reader.getRootElement());

            assertEquals(1, nodes.size());

            for(Node node : nodes){
                NodeList childrens = node.getChildNodes();

                assertEquals("test-backup", getNode("id", childrens).getTextContent());
                assertEquals("1.0", getNode("version", childrens).getTextContent());

                Node nodesNode = getNode("nodes", childrens);

                assertEquals("nodes", nodesNode.getNodeName());

                childrens = nodesNode.getChildNodes();

                Node elementNode = getNode("element", childrens);

                assertEquals("element", elementNode.getNodeName());
                assertEquals("true", elementNode.getAttributes().getNamedItem("test").getTextContent());
            }
        } catch (XMLException e) {
            fail(e.getMessage());
        }

        try {
            reader.close();
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    private static Node getNode(String name, NodeList childrens) {
        for(int i = 0; i < childrens.getLength(); i++){
            if(name.equals(childrens.item(i).getNodeName())){
                return childrens.item(i);
            }
        }

        return null;
    }

    private static void createFakeBackupFile(File file) {
        XMLWriter<Node> writer = XML.newJavaFactory().newWriter("jtheque-backup");

        writer.add("header");

        writer.addOnly("date", Integer.toString(IntDate.today().intValue()));
        writer.addOnly("file-version", Integer.toString(XmlBackupVersion.THIRD.ordinal()));
        writer.addOnly("jtheque-version", Core.VERSION.getVersion());

        writer.switchToParent();

        addBackup(writer, "test-backup");
        addBackup(writer, "test-backup-2");

        writer.write(file.getAbsolutePath());
    }

    private static void addBackup(XMLWriter<Node> writer, String name) {
        writer.add("backup");

        writer.addOnly("id", name);
        writer.addOnly("version", "1.0");

        writer.add("nodes");

        writer.add("simple");
        writer.addAttribute("test", "true");
        writer.switchToParent();

        writer.switchToParent();

        writer.switchToParent();
    }
}