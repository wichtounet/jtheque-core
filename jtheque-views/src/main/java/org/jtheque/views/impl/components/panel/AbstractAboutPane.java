package org.jtheque.views.impl.components.panel;

import org.jdesktop.swingx.JXPanel;
import org.jtheque.core.able.ICore;
import org.jtheque.i18n.able.ILanguageService;
import org.pushingpixels.trident.Timeline;

import java.util.ArrayList;
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

/**
 * An abstract about pane view.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractAboutPane extends JXPanel {
    private Timeline timeline;

    private String licence;
    private String copyright;
    private Info[] infos;
    private Collection<String> credits;

    private final ICore core;
    private final ILanguageService languageService;

    protected AbstractAboutPane(ILanguageService languageService, ICore core) {
        super();

        this.languageService = languageService;
        this.core = core;
    }

    /**
     * Init the view.
     */
    void init() {
        copyright = core.getApplication().getCopyright();
        licence = getMessage("about.actions.read");

        infos = new Info[4];

        infos[0] = new Info(getMessage("about.view.version"), core.getApplication().getVersion().getVersion(), false, false);
        infos[1] = new Info(getMessage("about.view.author"), core.getApplication().getAuthor(), false, false);
        infos[2] = new Info(getMessage("about.view.site"), core.getApplication().getSite(), true, false);
        infos[3] = new Info(getMessage("about.view.mail"), core.getApplication().getEmail(), false, true);

        credits = new ArrayList<String>(core.getCreditsMessage().size() * 4);

        for (String key : core.getCreditsMessage()) {
            String[] messages = languageService.getLinesMessage(key);

            credits.addAll(Arrays.asList(messages));
        }
    }

    /**
     * Return an internationalized message.
     *
     * @param key The internationalization key.
     * @return The internationalized message.
     */
    private String getMessage(String key) {
        return languageService.getMessage(key);
    }

    /**
     * The timeline of the view.
     *
     * @return The timeline of the view.
     */
    public final Timeline getTimeline() {
        return timeline;
    }

    /**
     * Set the timeline of the view.
     *
     * @param timeline The timeline of the view.
     */
    final void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    /**
     * The licence message.
     *
     * @return The licence message.
     */
    final String getLicenceMessage() {
        return licence;
    }

    /**
     * Return the copyright to display on the view.
     *
     * @return The copyright to display on the view.
     */
    final String getCopyright() {
        return copyright;
    }

    /**
     * Return all the informations to display on the view.
     *
     * @return All the informations to display.
     */
    final Info[] getInfos() {
        return infos;
    }

    /**
     * Return all the credits to display.
     *
     * @return All the credits to display.
     */
    final Collection<String> getCredits() {
        return credits;
    }

    /**
     * An info about the application to display.
     *
     * @author Baptiste Wicht
     */
    static final class Info {
        private final String left;
        private final String right;
        private final boolean url;
        private final boolean mail;

        private int leftWidth;
        private int rightWidth;

        /**
         * Create a new Info.
         *
         * @param left  The left part of the information.
         * @param right The right part of the information.
         * @param url   Indicate if the information is an url or not.
         * @param mail  Indicate if the information is an email address or not.
         */
        private Info(String left, String right, boolean url, boolean mail) {
            super();

            this.left = left;
            this.right = right;
            this.url = url;
            this.mail = mail;
        }

        /**
         * Return the left part of the information.
         *
         * @return The left part of the information.
         */
        public String getLeft() {
            return left;
        }

        /**
         * Return the right part of the information.
         *
         * @return The right part of the information.
         */
        public String getRight() {
            return right;
        }

        /**
         * Indicate if the info is an url or not.
         *
         * @return true if the info is an url else false.
         */
        public boolean isUrl() {
            return url;
        }

        /**
         * Indicate if the info is a mail or not.
         *
         * @return true if the info is a mail else false.
         */
        public boolean isMail() {
            return mail;
        }

        /**
         * Return the width of the left part of the information.
         *
         * @return The width of the left part of the information.
         */
        public int getLeftWidth() {
            return leftWidth;
        }

        /**
         * Set the width of the left part of the information.
         *
         * @param leftWidth The width of the left part of the information.
         */
        public void setLeftWidth(int leftWidth) {
            this.leftWidth = leftWidth;
        }

        /**
         * Return the width of the right part of the information.
         *
         * @return The width of the right part of the information.
         */
        public int getRightWidth() {
            return rightWidth;
        }

        /**
         * Set the width of the right part of the information.
         *
         * @param rightWidth The width of the right part of the information.
         */
        public void setRightWidth(int rightWidth) {
            this.rightWidth = rightWidth;
        }
    }
}
