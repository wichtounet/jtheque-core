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

import org.jtheque.core.Core;
import org.jtheque.core.lifecycle.LifeCycle;
import org.jtheque.core.lifecycle.TitleListener;
import org.jtheque.i18n.LanguageService;
import org.jtheque.ui.Model;
import org.jtheque.ui.UIUtils;
import org.jtheque.ui.components.Borders;
import org.jtheque.ui.components.LayerTabbedPane;
import org.jtheque.ui.utils.builders.JThequePanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.ui.utils.windows.frames.SwingFrameView;
import org.jtheque.utils.SimplePropertiesCache;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.ViewService;
import org.jtheque.views.Views;
import org.jtheque.views.components.MainComponent;
import org.jtheque.views.impl.MainController;
import org.jtheque.views.impl.components.MainTabbedPane;
import org.jtheque.views.impl.components.menu.JThequeMenuBar;
import org.jtheque.views.impl.components.panel.JThequeStateBar;
import org.jtheque.views.windows.MainView;

import javax.annotation.Resource;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Collection;

/**
 * The main view of JTheque.
 *
 * @author Baptiste Wicht
 */
public final class MainViewImpl extends SwingFrameView<Model> implements TitleListener, MainView {
    private MainTabbedPane tab;

    private MainController controller;

    private static final int DEFAULT_WIDTH = 830;
    private static final int DEFAULT_HEIGHT = 645;

    private WindowListener tempListener;

    private int current;

    private JThequeStateBar stateBar;

    @Resource
    private ViewService viewService;
    
    @Resource
    private LanguageService languageService;

    @Resource
    private Views views;

    @Resource
    private UIUtils uiUtils;

    @Resource
    private Core core;

    @Resource
    private LifeCycle lifeCycle;

    private final JThequeMenuBar menuBar;

    /**
     * Construct a new MainViewImpl.
     *
     * @param menuBar         The menu bar.
     */
    public MainViewImpl(JThequeMenuBar menuBar) {
        super();

        this.menuBar = menuBar;

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
        controller = new MainController(core, uiUtils, lifeCycle);

        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                setContentPane(new JPanel());

                buildContentPane();

                menuBar.buildMenu();

                setJMenuBar(menuBar);

                removeWindowListener(tempListener);

                addWindowListener(controller);

                viewService.configureView(MainViewImpl.this, "main", DEFAULT_WIDTH, DEFAULT_HEIGHT);
            }
        });
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
        if (current > 1){
            tab.refreshComponents();

            current++;
        } else {
            current++;

            buildContentPane();
        }
    }

    /**
     * Remove the given main component.
     *
     * @param component The main component to remove.
     */
    private void removeComponent(MainComponent component) {
        if(current > 2) {
            tab.removeMainComponent(component);

            current--;
        } else {
            current--;

            buildContentPane();
        }
    }

    /**
     * Build the content pane.
     */
    private void buildContentPane() {
        getContentPane().removeAll();

        PanelBuilder builder = new JThequePanelBuilder((JPanel) getContentPane());
        builder.setBorder(Borders.EMPTY_BORDER);
        builder.setDefaultInsets(new Insets(0, 0, 0, 0));

        builder.add(getMainComponent(), builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));

        stateBar = new JThequeStateBar(views);

        SimplePropertiesCache.put("statebar-loaded", true);

        builder.add(stateBar, builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.LAST_LINE_START));
    }

    private Component getMainComponent() {
        Component mainComponent;

        if(current == 0){
            mainComponent = new JPanel();
            mainComponent.setBackground(Color.white);
        } else if(current == 1) {
            Collection<MainComponent> components = views.getMainComponents();

            mainComponent = CollectionUtils.first(components).getImpl();
        } else {
            if(tab == null){
                tab = new MainTabbedPane(languageService, views);
                tab.addChangeListener(controller);
            }

            mainComponent = tab;
        }
        return mainComponent;
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