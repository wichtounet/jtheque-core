package org.jtheque.features;

import org.jtheque.features.Feature.FeatureType;
import org.jtheque.i18n.LanguageService;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.SystemProperty;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.FileUtils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
@ContextConfiguration(locations = "jtheque-features-test.xml")
public class FeatureServiceTest {
    @Resource
    private FeatureService featureService;

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
        assertNotNull(featureService);
    }

    @Test
    public void configCreated() {
        File folder = new File(SystemProperty.JAVA_IO_TMP_DIR.get(), "jtheque");

        assertTrue(new File(folder, "config.xml").exists());
    }

    @Test
    public void defaultFeatures(){
        assertEquals(4, featureService.getFeatures().size());

        for(Feature feature : featureService.getFeatures()){
            assertEquals(FeatureType.PACK, feature.getType());

            assertTrue(StringUtils.equalsOneOf(feature.getTitleKey(), "menu.file", "menu.help", "menu.advanced", "menu.edit"));
        }
    }

    @Test
    public void listenerCalledForMainFeatures(){
        final AtomicInteger addCounter = new AtomicInteger(0);
        final AtomicInteger removeCounter = new AtomicInteger(0);
        final AtomicInteger modifyCounter = new AtomicInteger(0);

        featureService.addFeatureListener(new MyFeatureListener(addCounter, removeCounter, modifyCounter));

        featureService.addMenu("no-module", new MenuMain());

        assertEquals(1, addCounter.get());
        assertEquals(0, removeCounter.get());
        assertEquals(0, modifyCounter.get());

        featureService.addMenu("no-module", new MenuMain());

        assertEquals(2, addCounter.get());
        assertEquals(0, removeCounter.get());
        assertEquals(0, modifyCounter.get());
    }

    @Test
    public void listenerCalledForAddingFeature() {
        final AtomicInteger addCounter = new AtomicInteger(0);
        final AtomicInteger removeCounter = new AtomicInteger(0);
        final AtomicInteger modifyCounter = new AtomicInteger(0);

        featureService.addFeatureListener(new MyFeatureListener(addCounter, removeCounter, modifyCounter));

        featureService.addMenu("no-module", new MenuNoMain());

        assertEquals(0, addCounter.get());
        assertEquals(0, removeCounter.get());
        assertEquals(1, modifyCounter.get());

        featureService.addMenu("no-module", new MenuNoMain());

        assertEquals(0, addCounter.get());
        assertEquals(0, removeCounter.get());
        assertEquals(2, modifyCounter.get());
    }

    @Test
    public void listenerNotCalledAfterRemoved() {
        final AtomicInteger addCounter = new AtomicInteger(0);
        final AtomicInteger removeCounter = new AtomicInteger(0);
        final AtomicInteger modifyCounter = new AtomicInteger(0);

        FeatureListener listener = new MyFeatureListener(addCounter, removeCounter, modifyCounter);

        featureService.addFeatureListener(listener);

        featureService.addMenu("no-module", new MenuMain());

        featureService.removeFeatureListener(listener);

        featureService.addMenu("no-module-2", new MenuMain());

        assertEquals(1, addCounter.get());
        assertEquals(0, removeCounter.get());
        assertEquals(0, modifyCounter.get());
    }

    private static final class MenuMain implements Menu {
        @Override
        public Collection<Feature> getMainFeatures() {
            List<Feature> features = CollectionUtils.newList(1);

            features.add(Features.newPackFeature(55, "test.key"));

            return features;
        }

        @Override
        public Collection<Feature> getSubFeatures(CoreFeature feature) {
            return CollectionUtils.emptyList();
        }

        @Override
        public void refreshText(LanguageService languageService) {

        }
    }

    private static final class MenuNoMain implements Menu {
        @Override
        public Collection<Feature> getMainFeatures() {
            return CollectionUtils.emptyList();
        }

        @Override
        public Collection<Feature> getSubFeatures(CoreFeature feature) {
            if(feature == CoreFeature.FILE){
                return Arrays.asList(Features.newActionFeature(11, new JThequeAction(){
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        //Nothing to do
                    }
                }));
            }

            return CollectionUtils.emptyList();
        }

        @Override
        public void refreshText(LanguageService languageService) {

        }
    }

    private static class MyFeatureListener implements FeatureListener {
        private final AtomicInteger addCounter;
        private final AtomicInteger removeCounter;
        private final AtomicInteger modifyCounter;

        private MyFeatureListener(AtomicInteger addCounter, AtomicInteger removeCounter, AtomicInteger modifyCounter) {
            super();

            this.addCounter = addCounter;
            this.removeCounter = removeCounter;
            this.modifyCounter = modifyCounter;
        }

        @Override
        public void featureAdded(Feature feature) {
            addCounter.incrementAndGet();
        }

        @Override
        public void featureRemoved(Feature feature) {
            removeCounter.incrementAndGet();
        }

        @Override
        public void featureModified(Feature feature) {
            modifyCounter.incrementAndGet();
        }
    }
}