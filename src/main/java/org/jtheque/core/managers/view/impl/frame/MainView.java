package org.jtheque.core.managers.view.impl.frame;

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

import org.jdesktop.jxlayer.JXLayer;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.lifecycle.listeners.TitleEvent;
import org.jtheque.core.managers.lifecycle.listeners.TitleListener;
import org.jtheque.core.managers.view.MainController;
import org.jtheque.core.managers.view.SplashManager;
import org.jtheque.core.managers.view.able.IMainView;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.components.InfiniteWaitUI;
import org.jtheque.core.managers.view.impl.components.JThequeStateBar;
import org.jtheque.core.managers.view.impl.components.LayerTabbedPane;
import org.jtheque.core.managers.view.impl.components.MainTabbedPane;
import org.jtheque.core.managers.view.impl.components.ViewContainer;
import org.jtheque.core.managers.view.impl.components.menu.JMenuBarJTheque;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingFrameView;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * The main view of JTheque.
 *
 * @author Baptiste Wicht
 */
public final class MainView extends SwingFrameView implements TitleListener, IMainView {
    private LayerTabbedPane tab;

    private MainController controller;

    private static final int DEFAULT_WIDTH = 830;
    private static final int DEFAULT_HEIGHT = 645;

    private WindowListener tempListener;
    private IViewManager viewManager;

    private JXLayer<JComponent> content;

    private InfiniteWaitUI waitUI;

    /**
     * Construct a new MainView.
     */
    public MainView() {
        super();

        build();
    }

    /**
     * Build the view.
     */
    private void build() {
        setTitle(Managers.getCore().getLifeCycleManager().getTitle());
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        tempListener = new TempWindowAdapter();

        addWindowListener(tempListener);

        JComponent background = new JPanel();
        background.setBackground(Color.black);

        content = new JXLayer<JComponent>(background);

        setContentPane(content);

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        SwingUtils.centerFrame(this);

        Managers.getCore().getLifeCycleManager().addTitleListener(this);
    }

    /**
     * Return the content pane.
     *
     * @return the content pane.
     */
    public JXLayer<JComponent> getContent() {
        return content;
    }

    @Override
    public void fill() {
        viewManager = Managers.getManager(IViewManager.class);

        setIconImage(getDefaultWindowIcon());
        setResizable(true);
        controller = new MainController();

        content = buildContentPane();

        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                setContentPane(content);

                setJMenuBar(new JMenuBarJTheque());

                removeWindowListener(tempListener);

                addWindowListener(controller);

                viewManager.configureView(MainView.this, "main", DEFAULT_WIDTH, DEFAULT_HEIGHT);
            }
        });
    }

    @Override
    public void startWait() {
        installWaitUIIfNecessary();
        content.setUI(waitUI);
        waitUI.start();
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }

    @Override
    public void stopWait() {
        if (waitUI != null) {
            waitUI.stop();
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            content.setUI(null);
        }
    }

    /**
     * Create the wait UI only if this has not been done before.
     */
    private void installWaitUIIfNecessary() {
        if (waitUI == null) {
            waitUI = new InfiniteWaitUI(content);
        }
    }

    @Override
    public void setGlassPane(Component glassPane) {
        if (glassPane == null) {
            content.setGlassPane(content.createGlassPane());
        } else {
            content.setGlassPane((JPanel) glassPane);
        }
    }

    @Override
    public Component getGlassPane() {
        return content.getGlassPane();
    }

    /**
     * Build the content pane.
     *
     * @return le contentPane
     */
    private JXLayer<JComponent> buildContentPane() {
        PanelBuilder builder = new JThequePanelBuilder();
        builder.setBorder(Borders.EMPTY_BORDER);

        if (viewManager.isTabMainComponent()) {
            if (Managers.getManager(IViewManager.class).getTabComponents().isEmpty()) {
                Component emptyPanel = new JPanel();
                emptyPanel.setBackground(viewManager.getViewDefaults().getBackgroundColor());

                viewManager.setMainComponent(new ViewContainer(emptyPanel));

                builder.add(emptyPanel, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));
            } else {
                tab = new MainTabbedPane();
                tab.addChangeListener(controller);

                builder.add(tab, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));
            }
        } else {
            builder.setDefaultInsets(new Insets(0, 0, 0, 0));
            builder.add((Component) viewManager.getMainComponent().getImpl(), builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));
        }

        builder.add(new JThequeStateBar(), builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.LAST_LINE_START));

        return new JXLayer<JComponent>(builder.getPanel());
    }

    @Override
    public LayerTabbedPane getTabbedPane() {
        return tab;
    }

    @Override
    public void titleUpdated(TitleEvent event) {
        setTitle(event.getTitle());
    }

    @Override
    public void closeDown() {
        if (viewManager != null) {
            viewManager.saveState(this, "main");
        }

        super.closeDown();
    }

    @Override
    public boolean validateContent() {
        return true;
    }

    @Override
    public void setSelectedComponent(Object component) {
        tab.setSelectedComponent((Component) component);
    }

    @Override
    public JComponent getSelectedComponent() {
        return tab.getSelectedComponent();
    }
    
    /**
     * Return the instance of the main view.
     *
     * @return The instance of the main view.
     */
    public static MainView get() {
        return SplashManager.getInstance().getMainView();
    }

    /**
     * The temporary window adapter.
     *
     * @author Baptiste Wicht
     */
    private static final class TempWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            Managers.getCore().getLifeCycleManager().exit();
        }
    }
}