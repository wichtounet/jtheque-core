package org.jtheque.errors;

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

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;
import org.jtheque.i18n.ILanguageManager;
import org.jtheque.utils.ui.SwingUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

/**
 * An error manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class ErrorManager implements IErrorManager {
     private final Collection<JThequeError> errors = new ArrayList<JThequeError>(10);

    private final ILanguageManager languageManager;

    public ErrorManager(ILanguageManager languageManager) {
        super();

        this.languageManager = languageManager;
    }

    @Override
    public void addError(JThequeError error) {
        errors.add(error);

        displayError(error);
    }

    @Override
    public void displayErrors() {
        for (JThequeError error : errors) {
            displayError(error);
        }
    }

    @Override
    public void addInternationalizedError(String messageKey) {
        addError(new InternationalizedError(languageManager, messageKey));
    }

    @Override
    public void addInternationalizedError(String messageKey, Object... messageReplaces) {
        addError(new InternationalizedError(languageManager, messageKey, messageReplaces));
    }

    private static void displayError(JThequeError error) {
        SwingUtils.inEdt(new DisplayErrorRunnable(
                new ErrorInfo("Error", error.getMessage(), error.getDetails(), "", error.getException(), Level.SEVERE, null)));
    }

    /**
     * A Runnable to display an error.
     *
     * @author Baptiste Wicht
     */
    private static class DisplayErrorRunnable implements Runnable {
        private final ErrorInfo info;

        /**
         * The error info to display.
         *
         * @param info The error info to display.
         */
        DisplayErrorRunnable(ErrorInfo info){
            this.info = info;
        }

        @Override
        public void run() {
            JXErrorPane.showDialog(null, info);
        }
    }
}