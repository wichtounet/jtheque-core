package org.jtheque.views.impl.windows;

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

import org.jtheque.core.able.Core;
import org.jtheque.core.able.lifecycle.LifeCycle;
import org.jtheque.core.able.lifecycle.TitleListener;
import org.jtheque.i18n.able.LanguageService;
import org.jtheque.ui.able.Model;
import org.jtheque.ui.able.UIUtils;
import org.jtheque.ui.able.components.Borders;
import org.jtheque.ui.able.components.LayerTabbedPane;
import org.jtheque.ui.utils.builders.JThequePanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.ui.utils.windows.frames.SwingFrameView;
import org.jtheque.utils.SimplePropertiesCache;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.ViewService;
import org.jtheque.views.able.Views;
import org.jtheque.views.able.components.MainComponent;
import org.jtheque.views.impl.MainController;
import org.jtheque.views.impl.components.MainTabbedPane;
import org.jtheque.views.impl.components.menu.JThequeMenuBar;
import org.jtheque.views.impl.components.panel.JThequeStateBar;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Collection;

/**
 * The main view of JTheque.
 *
 * @author Baptiste Wicht
 */
public final class MainView extends SwingFrameView<Model> implements TitleListener, org.jtheque.views.able.windows.MainView {
    private MainTabbedPane tab;

    private MainController controller;

    private static final int DEFAULT_WIDTH = 830;
    private static final int DEFAULT_HEIGHT = 645;

    private WindowListener tempListener;

    private int current;

    private JThequeStateBar stateBar;

    private final ViewService viewService;
    private final LanguageService languageService;
    private final Views views;
    private final UIUtils uiUtils;
    private final Core core;
    private final LifeCycle lifeCycle;
    private final JThequeMenuBar menuBar;

    /**
     * Construct a new MainView.
     *
     * @param core            The core.
     * @param viewService     The view service.
     * @param views           The views.
     * @param uiUtils         The ui utils.
     * @param menuBar         The menu bar.
     * @param languageService The language service.
     * @param lifeCycle       The lifecycle.
     */
    public MainView(Core core, ViewService viewService, Views views, UIUtils uiUtils, JThequeMenuBar menuBar,
                    LanguageService languageService, LifeCycle lifeCycle) {
        super();

        this.core = core;
        this.viewService = viewService;
        this.views = views;
        this.uiUtils = uiUtils;
        this.menuBar = menuBar;
        this.languageService = languageService;
        this.lifeCycle = lifeCycle;

        SimplePropertiesCache.put("mainView", this);
    }

    /**
     * Build the view.
     */
    @Override
    public void init() {
        setTitle(lifeCycle.getTitle());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        tempListener = new TempWindowAdapter();

        addWindowListener(tempListener);

        JComponent background = new JPanel();
        background.setBackground(Color.black);

        setContentPane(background);

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        lifeCycle.addTitleListener(this);
    }

    /**
     * Build the entire view with the final content.
     */
    public void fill() {
        //setIconImage(getDefaultWindowIcon());

        controller = new MainController(core, uiUtils, lifeCycle);

        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                setContentPane(buildContentPane());

                menuBar.buildMenu();

                setJMenuBar(menuBar);

                removeWindowListener(tempListener);

                addWindowListener(controller);

                viewService.configureView(MainView.this, "main", DEFAULT_WIDTH, DEFAULT_HEIGHT);
            }
        });
    }

    /**
     * Build the content pane.
     *
     * @return the contentPane
     */
    private JComponent buildContentPane() {
        PanelBuilder builder = new JThequePanelBuilder();
        builder.setBorder(Borders.EMPTY_BORDER);

        Component emptyPanel = new JPanel();
        emptyPanel.setBackground(Color.white);

        builder.add(emptyPanel, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));

        stateBar = new JThequeStateBar(views);

        SimplePropertiesCache.put("statebar-loaded", true);

        builder.add(stateBar, builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.LAST_LINE_START));

        return builder.getPanel();
    }

    @Override
    public void sendMessage(String message, final Object value) {
        if ("add".equals(message)) {
            SwingUtils.inEdt(new Runnable() {
                @Override
                public void run() {
                    addComponent();

                    refresh();
                }
            });
        } else if ("remove".equals(message)) {
            SwingUtils.inEdt(new Runnable() {
                @Override
                public void run() {
                    removeComponent((MainComponent) value);

                    refresh();
                }
            });
        }
    }

    /**
     * Add the main component to the view.
     */
    private void addComponent() {
        if (current == 0) {
            setFirstComponentAsView();
        } else if (current == 1) {
            tab = new MainTabbedPane(languageService, views);
            tab.addChangeListener(controller);

            setComponentAsView(tab);
        } else {
            tab.refreshComponents();
        }

        current++;
    }

    /**
     * Remove the given main component.
     *
     * @param component The main component to remove.
     */
    private void removeComponent(MainComponent component) {
        if (current == 1) {
            Component emptyPanel = new JPanel();
            emptyPanel.setBackground(Color.white);

            setComponentAsView(emptyPanel);
        } else if (current == 2) {
            setFirstComponentAsView();
        } else {
            tab.removeMainComponent(component);
        }

        current--;
    }

    /**
     * Set the first main component to be the main view.
     */
    private void setFirstComponentAsView() {
        Collection<MainComponent> components = views.getMainComponents();

        setComponentAsView(CollectionUtils.first(components).getImpl());
    }

    /**
     * Set the given component as the main view.
     *
     * @param component The component to be the main view.
     */
    private void setComponentAsView(Component component) {
        getContentPane().removeAll();

        GridBagUtils gbc = new GridBagUtils();

        getContentPane().add(component, gbc.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));
        getContentPane().add(stateBar, gbc.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.LAST_LINE_START));
    }

    @Override
    public LayerTabbedPane getTabbedPane() {
        return tab;
    }

    @Override
    public void titleUpdated(String title) {
        setTitle(title);
    }

    @Override
    public void setSelectedComponent(Object component) {
        tab.setSelectedComponent((Component) component);
    }

    @Override
    public JComponent getSelectedComponent() {
        return tab.getSelectedComponent();
    }

    @Override
    public JThequeStateBar getStateBar() {
        return stateBar;
    }

    @Override
    public void closeDown() {
        if (viewService != null) {
            viewService.saveState(this, "main");
        }

        super.closeDown();
    }

    /**
     * The temporary window adapter.
     *
     * @author Baptiste Wicht
     */
    private final class TempWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            lifeCycle.exit();
        }
    }
}