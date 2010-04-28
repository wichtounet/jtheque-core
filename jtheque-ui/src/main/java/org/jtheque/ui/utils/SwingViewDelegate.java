package org.jtheque.ui.utils;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;
import org.jtheque.core.utils.SimplePropertiesCache;
import org.jtheque.errors.JThequeError;
import org.jtheque.ui.able.ViewDelegate;
import org.jtheque.utils.ui.SwingUtils;
import org.slf4j.LoggerFactory;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Window;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

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
 * @author Baptiste Wicht
 */
public final class SwingViewDelegate implements ViewDelegate {
    private static final String MAIN_VIEW_CACHE = "mainView";

    @Override
    public boolean askUserForConfirmation(final String text, final String title) {
        boolean yes = false;

		Window parent = null;

		if (SimplePropertiesCache.get(MAIN_VIEW_CACHE) != null) {
			parent = SimplePropertiesCache.get(MAIN_VIEW_CACHE);
		}

		final Window p = parent;

        final int[] response = new int[1];

		if(SwingUtilities.isEventDispatchThread()){
			response[0] = JOptionPane.showConfirmDialog(parent, text, title, JOptionPane.YES_NO_OPTION);
		} else {
			try {
				SwingUtilities.invokeAndWait(new Runnable(){
					@Override
					public void run() {
						response[0] = JOptionPane.showConfirmDialog(p, text, title, JOptionPane.YES_NO_OPTION);
					}
				});
			} catch (InterruptedException e) {
				LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
			}
		}
		
        if (response[0] == JOptionPane.YES_OPTION) {
            yes = true;
        }

        return yes;
    }

    @Override
    public void displayError(JThequeError error) {
        final ErrorInfo info = new ErrorInfo("Error", error.getMessage(), error.getDetails(), "", error.getException(), Level.SEVERE, null);

        run(new DisplayErrorRunnable(info));
    }

    @Override
    public void displayText(String text) {
        run(new DisplayTextRunnable(text));
    }



    @Override
    public void run(Runnable runnable) {
        SwingUtils.inEdt(runnable);
    }

    @Override
    public void refresh(Object c) {
        SwingUtils.refresh((Component) c);
    }

    @Override
    public String askText(String text) {
        Window parent = null;

        if (SimplePropertiesCache.<Component>get(MAIN_VIEW_CACHE) != null) {
            parent = (Window) SimplePropertiesCache.<Component>get(MAIN_VIEW_CACHE);
        }

        return JOptionPane.showInputDialog(parent, text);
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

    /**
     * A Runnable to display a text.
     *
     * @author Baptiste Wicht
     */
    private static class DisplayTextRunnable implements Runnable {
        private final String text;

        /**
         * The text to display.
         *
         * @param text The text to display. 
         */
        DisplayTextRunnable(String text){
            this.text = text;
        }

        @Override
        public void run() {
            JOptionPane.showMessageDialog(SimplePropertiesCache.<Component>get(MAIN_VIEW_CACHE), text);
        }
    }
}