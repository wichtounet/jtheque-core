package org.jtheque.ui.utils.builders;

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

import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

public abstract class AbstractPanelBuilder implements PanelBuilder {
    private final JPanel panel;

    private final GridBagUtils gbc;

    /**
     * Construct a new JThequePanelBuilder.
     */
    public AbstractPanelBuilder() {
        this(new JPanel(), true);
    }

    /**
     * Construct a new panel builder.
     *
     * @param panel The panel to build.
     */
    public AbstractPanelBuilder(JPanel panel) {
        this(panel, true);
    }

    /**
     * Construct a new JThequePanelBuilder with a specified layout.
     *
     * @param layout The layout to set to the builded panel.
     */
    public AbstractPanelBuilder(LayoutManager layout){
        this(new JPanel(layout), false);
    }

    /**
     * Construct a new JThequePanelBuilder.
     *
     * @param panel The panel to build.
     * @param layout If true set a default layout (GridBagLayout) to the builded panel.
     */
    public AbstractPanelBuilder(JPanel panel, boolean layout){
        super();

        if(layout){
            panel.setLayout(new GridBagLayout());
        }

        this.panel = panel;
        gbc = new GridBagUtils();

        initDefaults();
    }

    /**
     * Init the panel with the defaults properties.
     */
    abstract void initDefaults();

    @Override
    public <T extends Component> T add(T component, Object constraints) {
        panel.add(component, constraints);

        return component;
    }

    @Override
    public final JComponent getPanel() {
        return panel;
    }

    @Override
    public void setBorder(Border border){
        panel.setBorder(border);
    }

    @Override
    public void setDefaultInsets(Insets defaultInsets) {
        gbc.setDefaultInsets(defaultInsets);
    }

    @Override
    public final Object gbcSet(int x, int y, int fill, int anchor, double weightx, double weighty) {
        return gbc.gbcSet(x, y, fill, anchor, weightx, weighty);
    }

    @Override
    public final Object gbcSet(int x, int y, int fill, int anchor, int width, int height, double weightx, double weighty) {
        return gbc.gbcSet(x, y, fill, anchor, width, height, weightx, weighty);
    }

    @Override
    public final Object gbcSet(int x, int y, int fill, int anchor, int width, int height, double weightx, double weighty, int ipadx, int ipady) {
        return gbc.gbcSet(x, y, fill, anchor, width, height, weightx, weighty, ipadx, ipady);
    }

    @Override
    public final Object gbcSet(int x, int y, int fill, int anchor, int width, int height) {
        return gbc.gbcSet(x, y, fill, anchor, width, height);
    }

    @Override
    public final Object gbcSet(int x, int y, int fill, int width, int height) {
        return gbc.gbcSet(x, y, fill, GridBagUtils.BASELINE_LEADING, width, height);
    }

    @Override
    public final Object gbcSet(int x, int y, int fill, int anchor) {
        return gbc.gbcSet(x, y, fill, anchor);
    }

    @Override
    public final Object gbcSet(int x, int y, int fill) {
        return gbc.gbcSet(x, y, fill);
    }

    @Override
    public final Object gbcSet(int x, int y) {
        return gbc.gbcSet(x, y);
    }
}
