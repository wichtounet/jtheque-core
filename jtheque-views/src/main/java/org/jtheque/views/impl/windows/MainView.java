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

import org.jdesktop.jxlayer.JXLayer;
import org.jtheque.core.able.ICore;
import org.jtheque.core.able.lifecycle.TitleListener;
import org.jtheque.core.able.lifecycle.TitleEvent;
import org.jtheque.core.utils.SimplePropertiesCache;
import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.components.Borders;
import org.jtheque.ui.utils.builders.JThequePanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.ui.utils.components.LayerTabbedPane;
import org.jtheque.ui.utils.windows.frames.SwingFrameView;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.IViewService;
import org.jtheque.views.able.IViews;
import org.jtheque.views.able.components.MainComponent;
import org.jtheque.views.able.windows.IMainView;
import org.jtheque.views.impl.MainController;
import org.jtheque.views.impl.components.InfiniteWaitUI;
import org.jtheque.views.impl.components.panel.JThequeStateBar;
import org.jtheque.views.impl.components.MainTabbedPane;
import org.jtheque.views.impl.components.menu.JThequeMenuBar;

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
import java.util.Collection;

/**
 * The main view of JTheque.
 *
 * @author Baptiste Wicht
 */
public final class MainView extends SwingFrameView implements TitleListener, IMainView {
    private MainTabbedPane tab;

    private MainController controller;

    private static final int DEFAULT_WIDTH = 830;
    private static final int DEFAULT_HEIGHT = 645;

    private WindowListener tempListener;

    private JXLayer<JComponent> content;

    private InfiniteWaitUI waitUI;

    private int current;

    private JThequeStateBar stateBar;

    private final IViewService viewService;
    private final ILanguageService languageService;
    private final IViews views;
    private final IUIUtils uiUtils;
    private final ICore core;
    private final JThequeMenuBar menuBar;

    public MainView(ICore core, IViewService viewService, IViews views, IUIUtils uiUtils, JThequeMenuBar menuBar, ILanguageService languageService) {
        super();

        this.core = core;
        this.viewService = viewService;
        this.views = views;
        this.uiUtils = uiUtils;
        this.menuBar = menuBar;
        this.languageService = languageService;
    }

    /**
     * Build the view.
     */
    public void build() {
        setTitle(core.getLifeCycle().getTitle());
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

        core.getLifeCycle().addTitleListener(this);
    }

    /**
     * Return the content pane.
     *
     * @return the content pane.
     */
    public JXLayer<JComponent> getContent() {
        return content;
    }

    /**
     * Build the entire view with the final content.
     */
    public void fill() {
        setIconImage(getDefaultWindowIcon());
        setResizable(true);

        controller = new MainController(core, uiUtils);

        content = buildContentPane();

        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                setContentPane(content);

                menuBar.buildMenu();

                setJMenuBar(menuBar);

                removeWindowListener(tempListener);

                addWindowListener(controller);

                viewService.configureView(MainView.this, "main", DEFAULT_WIDTH, DEFAULT_HEIGHT);
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
    public void setGlassPane(final Component glassPane) {
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                if (glassPane == null) {
                    content.setGlassPane(content.createGlassPane());
                } else {
                    content.setGlassPane((JPanel) glassPane);

                    glassPane.setVisible(true);

                    glassPane.repaint();

                    SwingUtils.refresh(glassPane);
                }

                refresh();
            }
        });
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

        Collection<MainComponent> components = views.getMainComponents();

        if(components.isEmpty()){
            Component emptyPanel = new JPanel();
            emptyPanel.setBackground(Color.white);

            builder.add(emptyPanel, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));
        } else if (components.size() == 1) {
            builder.setDefaultInsets(new Insets(0, 0, 0, 0));
            builder.add(CollectionUtils.first(components).getImpl(),
                    builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));
        } else {
            tab = new MainTabbedPane(languageService, views);
            tab.addChangeListener(controller);

            builder.add(tab, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));
        }

        current = components.size();

        stateBar = new JThequeStateBar(views);

        SimplePropertiesCache.put("statebar-loaded", "true");

        builder.add(stateBar, builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.LAST_LINE_START));

        return new JXLayer<JComponent>(builder.getPanel());
    }

    @Override
    public void sendMessage(String message, Object value) {
        if("add".equals(message)){
            addComponent();
        } else if("remove".equals(message)){
            removeComponent((MainComponent) value);
        }
    }

    private void addComponent() {
        if(current == 0){
            content.getView().removeAll();

            GridBagUtils gbc = new GridBagUtils();

            Collection<MainComponent> components = views.getMainComponents();

            content.getView().add(CollectionUtils.first(components).getImpl(),
                    gbc.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));

            content.getView().add(stateBar, gbc.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.LAST_LINE_START));
        } else if(current == 1){
            content.getView().removeAll();

            GridBagUtils gbc = new GridBagUtils();

            tab = new MainTabbedPane(languageService, views);
            tab.addChangeListener(controller);

            content.getView().add(tab, gbc.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));

            content.getView().add(stateBar, gbc.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.LAST_LINE_START));
        } else {
            tab.refreshComponents();
        }
    }

    private void removeComponent(MainComponent component) {
        if(current == 1){
            content.getView().removeAll();

            GridBagUtils gbc = new GridBagUtils();

            Component emptyPanel = new JPanel();
            emptyPanel.setBackground(Color.white);

            content.getView().add(emptyPanel, gbc.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));

            content.getView().add(stateBar, gbc.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.LAST_LINE_START));
        } else if(current == 2){
            content.getView().removeAll();

            GridBagUtils gbc = new GridBagUtils();

            Collection<MainComponent> components = views.getMainComponents();

            content.getView().add(CollectionUtils.first(components).getImpl(),
                    gbc.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1.0, 1.0));

            content.getView().add(stateBar, gbc.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.LAST_LINE_START));
        } else {
            tab.removeMainComponent(component);
        }
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
        if (viewService != null) {
            viewService.saveState(this, "main");
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

    @Override
    public JThequeStateBar getStateBar() {
        return stateBar;
    }

    /**
     * The temporary window adapter.
     *
     * @author Baptiste Wicht
     */
    private final class TempWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            core.getLifeCycle().exit();
        }
    }
}