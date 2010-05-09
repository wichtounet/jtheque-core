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

/**
 * The activator to activate the defaults configuration of the view. 
 *
 * @author Baptiste Wicht
 */
public class DefaultsActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext){
        SwingUtils.inEdt(new ActivateDefaults());
    }

    @Override
    public void stop(BundleContext bundleContext){
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