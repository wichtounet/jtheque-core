package org.jtheque.defaults;

import org.jtheque.utils.ui.SwingUtils;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.pushingpixels.trident.TridentConfig;
import org.pushingpixels.trident.interpolator.CorePropertyInterpolators;
import org.pushingpixels.trident.swing.AWTPropertyInterpolators;
import org.slf4j.LoggerFactory;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
 * The activator to activate the defaults configuration of the view. 
 *
 * @author Baptiste Wicht
 */
public class DefaultsActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        SwingUtils.inEdt(new ActivateDefaults());
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        //Nothing to stop
    }
    
    private static class ActivateDefaults implements Runnable {
        @Override
        public void run() {
            try {
                UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
            } catch (UnsupportedLookAndFeelException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }

            UIManager.getLookAndFeelDefaults().put("ClassLoader", SubstanceBusinessBlackSteelLookAndFeel.class.getClassLoader());

            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);

            UIManager.put(SubstanceLookAndFeel.COLORIZATION_FACTOR, 1.0);

            LoggerFactory.getLogger(getClass()).debug("Substance look and feel configured");
            LoggerFactory.getLogger(getClass()).debug("Current look and feel : {}", UIManager.getLookAndFeel());

            TridentConfig.getInstance().addPropertyInterpolatorSource(new CorePropertyInterpolators());
            TridentConfig.getInstance().addPropertyInterpolatorSource(new AWTPropertyInterpolators());
        }
    }
}