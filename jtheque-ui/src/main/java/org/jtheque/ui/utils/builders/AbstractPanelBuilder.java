package org.jtheque.ui.utils.builders;

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
