package org.jtheque.core.managers.view.impl.components.filthy.java2d;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.swingx.JXPanel;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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
 * An abstract about pane view.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractAboutPane extends JXPanel {
    private Animator animator;

    private String licence;
    private String copyright;
    private Info[] infos;
    private Collection<String> credits;

    /**
     * Init the view.
     */
    void init() {
        copyright = Managers.getCore().getApplication().getCopyright();
        licence = getMessage("about.actions.read");

        infos = new Info[4];

        infos[0] = new Info(getMessage("about.view.version"),
                Managers.getCore().getApplication().getVersion().getVersion(), false, false);
        infos[1] = new Info(getMessage("about.view.author"),
                Managers.getCore().getApplication().getAuthor(), false, false);
        infos[2] = new Info(getMessage("about.view.site"),
                Managers.getCore().getApplication().getSite(), true, false);
        infos[3] = new Info(getMessage("about.view.mail"),
                Managers.getCore().getApplication().getEmail(), false, true);

        credits = new ArrayList<String>(Managers.getCore().getCreditsMessage().size() * 4);

        for (String key : Managers.getCore().getCreditsMessage()) {
            String[] messages = Managers.getManager(ILanguageManager.class).getLinesMessage(key);

            credits.addAll(Arrays.asList(messages));
        }
    }

    /**
     * Return an internationalized message.
     *
     * @param key The internationalization key.
     * @return The internationalized message.
     */
    private static String getMessage(String key) {
        return Managers.getManager(ILanguageManager.class).getMessage(key);
    }

    /**
     * The animator of the view.
     *
     * @return The animator of the view.
     */
    public final Animator getAnimator() {
        return animator;
    }

    /**
     * Set the animator of the view.
     *
     * @param animator The animator of the view.
     */
    final void setAnimator(Animator animator) {
        this.animator = animator;
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
