package org.jtheque.views.utils;

import org.jtheque.features.able.CoreFeature;
import org.jtheque.features.able.Features;
import org.jtheque.features.able.IFeature;
import org.jtheque.features.able.IFeature.FeatureType;
import org.jtheque.features.able.Menu;
import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.i18n.able.Internationalizable;
import org.jtheque.ui.able.IController;
import org.jtheque.ui.able.IView;
import org.jtheque.ui.utils.actions.ActionFactory;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.utils.collections.CollectionUtils;

import javax.swing.Action;
import javax.swing.KeyStroke;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * An abstract menu. This class provide several a method to specify features for each of the core features. This methods
 * are only called once and the result are kept in cache for the next call of the Menu methods. On the same way, this
 * class provide a method to specify the main features. This method are also cached.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractMenu implements Menu {
    private final Map<String, List<IFeature>> cache = new HashMap<String, List<IFeature>>(5);

    private final Collection<Internationalizable> internationalizables = new ArrayList<Internationalizable>(10);

    //Public methods

    @Override
    public final List<IFeature> getSubFeatures(CoreFeature feature) {
        switch (feature) {
            case FILE:
                loadSubFeatures(CoreFeature.FILE, getFileMenuSubFeatures());

                break;
            case EDIT:
                loadSubFeatures(CoreFeature.EDIT, getEditMenuSubFeatures());

                break;
            case ADVANCED:
                loadSubFeatures(CoreFeature.ADVANCED, getAdvancedMenuSubFeatures());

                break;
            case HELP:
                loadSubFeatures(CoreFeature.HELP, getHelpMenuSubFeatures());

                break;
        }

        return cache.get(feature.name());
    }

    /**
     * Load the sub features of the given core feature.
     *
     * @param feature     The core feature.
     * @param subFeatures The sub features to add to the given core feature.
     */
    private void loadSubFeatures(CoreFeature feature, List<IFeature> subFeatures) {
        if (!cache.containsKey(feature.name())) {
            cache.put(feature.name(), subFeatures);
        }
    }

    @Override
    public final List<IFeature> getMainFeatures() {
        if (!cache.containsKey("MAIN")) {
            cache.put("MAIN", getMenuMainFeatures());
        }

        return cache.get("MAIN");
    }

    //Methods to override

    /**
     * Return the main features of this menu. This method will only be called once and the result will be cached.
     *
     * @return A List containing all the main features of this menu.
     */
    protected List<IFeature> getMenuMainFeatures() {
        return CollectionUtils.emptyList();
    }

    /**
     * Return all the sub features of the "File" menu. This method will only be called once and the result will be
     * cached.
     *
     * @return A List containing all the sub features of "File" menu.
     */
    protected List<IFeature> getFileMenuSubFeatures() {
        return CollectionUtils.emptyList();
    }

    /**
     * Return all the sub features of the "Edit" menu. This method will only be called once and the result will be
     * cached.
     *
     * @return A List containing all the sub features of "Edit" menu.
     */
    protected List<IFeature> getEditMenuSubFeatures() {
        return CollectionUtils.emptyList();
    }

    /**
     * Return all the sub features of the "Advanced" menu. This method will only be called once and the result will be
     * cached.
     *
     * @return A List containing all the sub features of "Advanced" menu.
     */
    protected List<IFeature> getAdvancedMenuSubFeatures() {
        return CollectionUtils.emptyList();
    }

    /**
     * Return all the sub features of the "Help" menu. This method will only be called once and the result will be
     * cached.
     *
     * @return A List containing all the sub features of "Help" menu.
     */
    protected List<IFeature> getHelpMenuSubFeatures() {
        return CollectionUtils.emptyList();
    }

    //Utility methods

    /**
     * Create a main feature.
     *
     * @param position The position of the feature in the menu bar.
     * @param key      The i18n key of the feature.
     * @param features The sub features.
     *
     * @return The created main feature.
     */
    protected static IFeature createMainFeature(int position, String key, IFeature... features) {
        return Features.newFeature(FeatureType.PACK, key, position, features);
    }

    /**
     * Create a separated (it seems with a line separator) feature.
     *
     * @param position The position of the feature.
     * @param key      The i18n key of the feature.
     * @param features The sub features.
     *
     * @return The created separated feature.
     */
    protected static IFeature createSeparatedSubFeature(int position, String key, IFeature... features) {
        return Features.newFeature(FeatureType.SEPARATED_ACTIONS, key, position, features);
    }

    /**
     * Create a feature.
     *
     * @param position The position of the feature.
     * @param key      The i18n key of the feature.
     * @param features The sub features.
     *
     * @return The created feature.
     */
    protected static IFeature createSubFeature(int position, String key, IFeature... features) {
        return Features.newFeature(FeatureType.ACTIONS, key, position, features);
    }

    /**
     * Create a separated (it seems with a line separator) feature.
     *
     * @param position The position of the feature.
     * @param action   The action.
     * @param image    The image name.
     *
     * @return The created separated feature.
     */
    protected IFeature createSeparatedSubFeature(int position, JThequeAction action, String image) {
        internationalizables.add(action);

        return Features.newFeature(FeatureType.SEPARATED_ACTION, position, action, image);
    }

    /**
     * Create a separated (it seems with a line separator) feature.
     *
     * @param position    The position of the feature.
     * @param action      The action.
     * @param image       The image name.
     * @param accelerator The menu accelerator.
     *
     * @return The created separated feature.
     */
    protected IFeature createSeparatedSubFeature(int position, JThequeAction action, String image, int accelerator) {
        IFeature feature = createSeparatedSubFeature(position, action, image);

        feature.getAction().putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(accelerator, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        return feature;
    }

    /**
     * Create a separated (it seems with a line separator) feature.
     *
     * @param position The position of the feature.
     * @param action   The action of the feature.
     *
     * @return The created separated feature.
     */
    protected IFeature createSeparatedSubFeature(int position, JThequeAction action) {
        internationalizables.add(action);

        return Features.newFeature(FeatureType.SEPARATED_ACTION, position, action);
    }

    /**
     * Create a feature.
     *
     * @param position The position of the feature.
     * @param action   The action.
     * @param image    The image name.
     *
     * @return The created feature.
     */
    protected IFeature createSubFeature(int position, JThequeAction action, String image) {
        internationalizables.add(action);

        return Features.newFeature(FeatureType.ACTION, position, action, image);
    }

    /**
     * Create a feature.
     *
     * @param position The position of the feature.
     * @param action   The action of the feature.
     *
     * @return The created feature.
     */
    protected IFeature createSubFeature(int position, JThequeAction action) {
        internationalizables.add(action);

        return Features.newFeature(FeatureType.ACTION, position, action);
    }

    /**
     * Transform all the features into a List of features.
     *
     * @param features The features to put in the list.
     *
     * @return A List of features containing all the features in parameters.
     */
    protected static List<IFeature> features(IFeature... features) {
        return Arrays.asList(features);
    }

    //Utility action methods

    /**
     * Create an action to close the view.
     *
     * @param key  The i18n key.
     * @param view The view to close.
     *
     * @return An action to close the view.
     */
    public static JThequeAction createCloseViewAction(String key, IView view) {
        return ActionFactory.createCloseViewAction(key, view);
    }

    /**
     * Create an action to display the view.
     *
     * @param key  The i18n key.
     * @param view The view to close.
     *
     * @return An action to close the view.
     */
    public static JThequeAction createDisplayViewAction(String key, IView view) {
        return ActionFactory.createDisplayViewAction(key, view);
    }

    /**
     * Create an action linked to the controller.
     *
     * @param key        The i18n key of the action.
     * @param controller The controller to bind the action to.
     *
     * @return The JThequeAction for this controller binding.
     */
    public static JThequeAction createControllerAction(String key, IController controller) {
        return ActionFactory.createControllerAction(key, controller);
    }

    @Override
    public void refreshText(ILanguageService languageService) {
        for (Internationalizable internationalizable : internationalizables) {
            internationalizable.refreshText(languageService);
        }
    }
}