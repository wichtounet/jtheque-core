package org.jtheque.core.managers.view.impl;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.Views;
import org.jtheque.core.managers.view.able.IView;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.ViewDelegate;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.ui.SwingUtils;

import javax.annotation.Resource;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.io.File;
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
    @Resource
    private Views windowManager;

    private JFileChooser chooser;

    @Override
    public boolean askYesOrNo(String text, String title) {
        boolean yes = false;

        Window parent = null;

        if (Managers.getManager(IViewManager.class).getViews().getMainView() != null) {
            parent = (Window) Managers.getManager(IViewManager.class).getViews().getMainView().getImpl();
        }

        final int response = JOptionPane.showConfirmDialog(parent, text, title, JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            yes = true;
        }

        return yes;
    }

    @Override
    public void displayError(JThequeError error) {
        ErrorInfo info = new ErrorInfo("Error", error.getMessage(), error.getDetails(), "", error.getException(), Level.SEVERE, null);

        JXErrorPane.showDialog((Component) Managers.getManager(IViewManager.class).getViews().getMainView().getImpl(), info);
    }

    @Override
    public void displayText(String text) {
        JOptionPane.showMessageDialog((Component) Managers.getManager(IViewManager.class).getViews().getMainView().getImpl(), text);
    }

    @Override
    public String chooseFile(SimpleFilter filter) {
        File file = null;

        if (chooser == null) {
            chooser = new JFileChooser();
        }

        if (filter == null) {
            chooser.setAcceptAllFileFilterUsed(true);
        } else {
            chooser.addChoosableFileFilter(new SwingFileFilter(filter));
            chooser.setAcceptAllFileFilterUsed(false);
        }

        int answer = chooser.showOpenDialog(Managers.getManager(IViewManager.class).getSplashManager().getMainView());

        if (answer == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }

        return file == null ? null : file.getAbsolutePath();
    }

    @Override
    public String chooseDirectory() {
        File file = null;

        if (chooser == null) {
            chooser = new JFileChooser();
        }

        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnCode = chooser.showOpenDialog(new JFrame());

        if (returnCode == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }

        return file == null ? null : file.getAbsolutePath();
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
    public void applyGlassPane(Object component) {
        final Component glass = (Component) component;

        Runnable display = new Runnable() {
            @Override
            public void run() {
                windowManager.getMainView().setGlassPane(glass);

                glass.setVisible(true);

                glass.repaint();

                refresh(glass);
                windowManager.getMainView().refresh();
            }
        };

        run(display);
    }

    @Override
    public void setSize(IView view, int defaultWidth, int defaultHeight) {
        ((Component) view).setSize(defaultWidth, defaultHeight);
        SwingUtils.centerFrame((Window) view);
    }

    @Override
    public void fill(WindowConfiguration configuration, IView view) {
        Component window = (Component) view;

        configuration.setWidth(window.getWidth());
        configuration.setHeight(window.getHeight());
        configuration.setPositionX(window.getLocation().x);
        configuration.setPositionY(window.getLocation().y);
    }

    @Override
    public void configure(WindowConfiguration configuration, IView view) {
        Window window = (Window) view;

        window.setSize(configuration.getWidth(), configuration.getHeight());

        if (configuration.getPositionX() == -1 || configuration.getPositionY() == -1) {
            SwingUtils.centerFrame(window);
        } else {
            if (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().contains(
                    configuration.getPositionX(), configuration.getPositionY())) {
                window.setLocation(configuration.getPositionX(), configuration.getPositionY());
            } else {
                SwingUtils.centerFrame(window);
            }
        }
    }

    @Override
    public String askText(String text) {
        Window parent = null;

        if (Managers.getManager(IViewManager.class).getViews().getMainView() != null) {
            parent = (Window) Managers.getManager(IViewManager.class).getViews().getMainView().getImpl();
        }

        return JOptionPane.showInputDialog(parent, text);
    }
}
