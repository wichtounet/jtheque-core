package org.jtheque.core.managers.view;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.able.ISplashManager;
import org.jtheque.core.managers.view.able.components.ISplashView;
import org.jtheque.core.managers.view.impl.components.filthy.java2d.SplashScreenPane;
import org.jtheque.core.managers.view.impl.frame.MainView;
import org.jtheque.utils.ui.SwingUtils;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.BusinessBlackSteelSkin;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

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
 * A manager for the splash. The splash is not in Spring to make it appears before spring context loading.
 *
 * @author Baptiste Wicht
 */
public final class SplashManager implements ISplashManager {
    private static final ISplashManager INSTANCE = new SplashManager();

    private ISplashView splashScreenPane;
    private MainView mainView;
    private boolean mainDisplayed;

    /**
     * Construct a new <code>SplashManager</code>
     */
    public SplashManager() {
        super();

        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                SubstanceLookAndFeel.setSkin(new BusinessBlackSteelSkin());

                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);

                UIManager.put(SubstanceLookAndFeel.COLORIZATION_FACTOR, 1.0);

                mainView = new MainView();
                splashScreenPane = new SplashScreenPane();
            }
        });
    }

    /**
     * Return the unique instance of the manager.
     *
     * @return The unique instance of the manager.
     */
    public static ISplashManager getInstance() {
        return INSTANCE;
    }

    @Override
    public void displaySplashScreen() {
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                displayMainViewIfNecessary();

                mainView.getContent().setUI(splashScreenPane.getImpl());

                mainView.refresh();

                splashScreenPane.appearsAndAnimate();
            }
        });
    }

    @Override
    public void closeSplashScreen() {
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                splashScreenPane.disappear();
                mainView.getContent().setUI(null);
            }
        });
    }

    /**
     * Display the main view if necessary.
     */
    private void displayMainViewIfNecessary() {
        if (!mainDisplayed) {
            mainDisplayed = true;
            mainView.setTitle(Managers.getCore().getLifeCycleManager().getTitle());
            mainView.display();
        }
    }

    @Override
    public void fillMainView() {
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                mainView.fill();
                mainView.refresh();
            }
        });
    }

    @Override
    public MainView getMainView() {
        return mainView;
    }
}