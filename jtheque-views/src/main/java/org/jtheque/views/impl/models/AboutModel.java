package org.jtheque.views.impl.models;

import org.jtheque.core.able.ICore;
import org.jtheque.core.able.application.Application;
import org.jtheque.i18n.able.LanguageService;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;

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

public final class AboutModel {
    public static final int CREDITS_HEIGHT = 75;
    public static final int VIEW_HEIGHT = 400;
    public static final int INFO_MARGIN = 15;
    public static final int CREDIT_HEIGHT = 25;
    public static final int INFO_HEIGHT = 20;

    private String license;
    private String copyright;
    private AboutInfo[] infos;
    private Collection<String> credits;

    public void refresh(ICore core, LanguageService i18n){
        Application application = core.getApplication();

        copyright = application.getCopyright();
        license = i18n.getMessage("about.actions.read");

        infos = new AboutInfo[]{
                new AboutInfo(i18n.getMessage("about.view.version"), application.getVersion().getVersion(), false, false),
                new AboutInfo(i18n.getMessage("about.view.author"), application.getAuthor(), false, false),
                new AboutInfo(i18n.getMessage("about.view.site"), application.getSite(), true, false),
                new AboutInfo(i18n.getMessage("about.view.mail"), application.getEmail(), false, true)
        };

        credits = CollectionUtils.newList(core.getCreditsMessage().size() * 4);

        for (String key : core.getCreditsMessage()) {
            String[] messages = i18n.getLinesMessage(key);

            credits.addAll(Arrays.asList(messages));
        }
    }

    public String getLicense() {
        return license;
    }

    public String getCopyright() {
        return copyright;
    }

    public AboutInfo[] getInfos() {
        return infos;
    }

    public Collection<String> getCredits() {
        return credits;
    }

    /**
     * Return the credits height.
     *
     * @return The credits height.
     */
    public int getCreditsHeight() {
        return credits.size() * CREDIT_HEIGHT;
    }
}