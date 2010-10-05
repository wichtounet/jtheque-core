package org.jtheque.core;

import org.jtheque.core.application.Application;
import org.jtheque.core.application.ApplicationProperties;
import org.jtheque.core.utils.ImageType;
import org.jtheque.unit.AbstractJThequeTest;
import org.jtheque.utils.SystemProperty;
import org.jtheque.utils.bean.Version;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.concurrent.atomic.AtomicInteger;

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
@ContextConfiguration(locations = "jtheque-core-test.xml")
public class CoreTest extends AbstractJThequeTest {
    @Resource
    private Core core;

    @Test
    public void initOK() {
        assertNotNull(core);
    }

    @Test
    public void loaded() {
        assertNotNull(core.getConfiguration());
    }

    @Test
    public void foldersExists() {
        assertTrue(Folders.getApplicationFolder().exists());
        assertTrue(Folders.getModulesFolder().exists());
    }

    @Test
    @DirtiesContext
    public void launchApplication() {
        Application application = new TestApplication();

        core.launchApplication(application);

        assertEquals(application, core.getApplication());

        assertEquals(2, core.getPossibleLanguages().size());
        assertTrue(core.getPossibleLanguages().contains("Français"));
        assertTrue(core.getPossibleLanguages().contains("English"));
    }

    @Test(expected = IllegalStateException.class)
    @DirtiesContext
    public void languagesWithoutApplication() {
        core.getPossibleLanguages();
    }

    @Test(expected = IllegalStateException.class)
    @DirtiesContext
    public void multipleLaunches() {
        core.launchApplication(new TestApplication());
        core.launchApplication(new TestApplication());
    }

    @Test
    @DirtiesContext
    public void applicationListener() {
        final AtomicInteger counter = new AtomicInteger(0);

        final Application launchedApplication = new TestApplication();

        core.addApplicationListener(new ApplicationListener() {
            @Override
            public void applicationLaunched(Application application) {
                assertEquals(application, launchedApplication);
                assertEquals(application, core.getApplication());

                counter.incrementAndGet();
            }
        });

        core.launchApplication(launchedApplication);

        assertEquals(1, counter.intValue());
    }

    @Test
    @DirtiesContext
    public void applicationURLs() {
        core.launchApplication(new TestApplication());

        assertEquals("help-application-url", core.getHelpURL());
        assertEquals("bugs-application-url", core.getBugTrackerURL());
        assertEquals("improvement-application-url", core.getImprovementURL());
    }

    @Test
    public void creditsMessage() {
        int oldSize = core.getCreditsMessage().size();
        core.addCreditsMessage("super.key");

        assertEquals(oldSize + 1, core.getCreditsMessage().size());
        assertTrue(core.getCreditsMessage().contains("super.key"));
    }

    static final class TestApplication implements Application {
        @Override
        public String getLogo() {
            return "test";
        }

        @Override
        public ImageType getLogoType() {
            return ImageType.PNG;
        }

        @Override
        public String getWindowIcon() {
            return "application.png";
        }

        @Override
        public String getLicenseFilePath() {
            return null;
        }

        @Override
        public Version getVersion() {
            return Version.get("1.0.1");
        }

        @Override
        public String getFolderPath() {
            return SystemProperty.USER_DIR.get();
        }

        @Override
        public boolean isLicenseDisplayed() {
            return false;
        }

        @Override
        public String getRepository() {
            return "";
        }

        @Override
        public String getMessageFileURL() {
            return "";
        }

        @Override
        public ApplicationProperties getI18nProperties() {
            return new ApplicationProperties() {
                @Override
                public String getName() {
                    return "test-application-name";
                }

                @Override
                public String getAuthor() {
                    return "test-application-author";
                }

                @Override
                public String getEmail() {
                    return "test-application-email";
                }

                @Override
                public String getSite() {
                    return "test-application-site";
                }

                @Override
                public String getCopyright() {
                    return "test-application-copyright";
                }
            };
        }

        @Override
        public String[] getSupportedLanguages() {
            return new String[]{"fr", "en"};
        }

        @Override
        public String getProperty(String key) {
            if ("url.help".equals(key)) {
                return "help-application-url";
            } else if ("url.bugs".equals(key)) {
                return "bugs-application-url";
            } else if ("url.improvement".equals(key)) {
                return "improvement-application-url";
            }

            return null;
        }
    }
}