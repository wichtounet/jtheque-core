package org.jtheque.views.impl.models;

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
 * An info about the application to display.
 *
 * @author Baptiste Wicht
 */
public final class AboutInfo {
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
    public AboutInfo(String left, String right, boolean url, boolean mail) {
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
