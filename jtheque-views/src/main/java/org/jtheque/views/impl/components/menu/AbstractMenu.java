package org.jtheque.views.impl.components.menu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jtheque.features.Feature;
import org.jtheque.features.IFeatureService;
import org.jtheque.features.Menu;
import org.jtheque.ui.able.IView;
import org.jtheque.ui.utils.actions.ActionFactory;
import org.jtheque.utils.collections.CollectionUtils;

import javax.swing.Action;

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
 * An abstract menu. This class provide several a method to specify features for each of the core features.
 * This methods are only called once and the result are kept in cache for the next call of the Menu methods.
 * On the same way, this class provide a method to specify the main features. This method are also cached.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractMenu implements Menu {
    private final Map<String, List<Feature>> cache = new HashMap<String, List<Feature>>(5);

    //Public methods

    @Override
    public final List<Feature> getSubFeatures(IFeatureService.CoreFeature feature){
        switch (feature){
            case FILE:
                if(!cache.containsKey(IFeatureService.CoreFeature.FILE.name())){
                    cache.put(IFeatureService.CoreFeature.FILE.name(), getFileMenuSubFeatures());
                }

                break;
            case EDIT:
                if(!cache.containsKey(IFeatureService.CoreFeature.EDIT.name())){
                    cache.put(IFeatureService.CoreFeature.EDIT.name(), getEditMenuSubFeatures());
                }

                break;
            case ADVANCED:
                if(!cache.containsKey(IFeatureService.CoreFeature.ADVANCED.name())){
                    cache.put(IFeatureService.CoreFeature.ADVANCED.name(), getAdvancedMenuSubFeatures());
                }

                break;
            case HELP:
                if(!cache.containsKey(IFeatureService.CoreFeature.HELP.name())){
                    cache.put(IFeatureService.CoreFeature.HELP.name(), getHelpMenuSubFeatures());
                }

                break;
        }

        return cache.get(feature.name());
    }

    @Override
    public final List<Feature> getMainFeatures(){
        if(!cache.containsKey("MAIN")){
            cache.put("MAIN", getMenuMainFeatures());
        }

        return cache.get("MAIN");
    }

    //Methods to override

    /**
     * Return the main features of this menu. This method will only be called once and the result
     * will be cached.
     *
     * @return A List containing all the main features of this menu.
     */
    protected List<Feature> getMenuMainFeatures(){
        return CollectionUtils.emptyList();
    }

    /**
     * Return all the sub features of the "File" menu. This method will only be called once and the result
     * will be cached.
     *
     * @return A List containing all the sub features of "File" menu.
     */
    protected List<Feature> getFileMenuSubFeatures(){
        return CollectionUtils.emptyList();
    }

    /**
     * Return all the sub features of the "Edit" menu. This method will only be called once and the result
     * will be cached.
     *
     * @return A List containing all the sub features of "Edit" menu.
     */
    protected List<Feature> getEditMenuSubFeatures(){
        return CollectionUtils.emptyList();
    }

    /**
     * Return all the sub features of the "Advanced" menu. This method will only be called once and the result
     * will be cached.
     *
     * @return A List containing all the sub features of "Advanced" menu.
     */
    protected List<Feature> getAdvancedMenuSubFeatures(){
        return CollectionUtils.emptyList();
    }

    /**
     * Return all the sub features of the "Help" menu. This method will only be called once and the result
     * will be cached.
     *
     * @return A List containing all the sub features of "Help" menu.
     */
    protected List<Feature> getHelpMenuSubFeatures(){
        return CollectionUtils.emptyList();
    }

    //Utility methods

    /**
     * Create a main feature.
     *
     * @param position The position of the feature in the menu bar.
     * @param key The i18n key of the feature.
     * @param features The sub features.
     *
     * @return The created main feature.
     */
    protected static Feature createMainFeature(int position, String key, Feature... features){
        Feature feature = new Feature(Feature.FeatureType.PACK, key, position);

        for(Feature f : features){
            feature.addSubFeature(f);
        }

        return feature;
    }

    /**
     * Create a separated (it seems with a line separator) feature.
     *
     * @param position The position of the feature.
     * @param key The i18n key of the feature.
     * @param features The sub features.
     *
     * @return The created separated feature.
     */
    protected static Feature createSeparatedSubFeature(int position, String key, Feature... features){
        Feature feature = new Feature(Feature.FeatureType.SEPARATED_ACTIONS, key, position);

        for(Feature f : features){
            feature.addSubFeature(f);
        }

        return feature;
    }

    /**
     * Create a feature.
     *
     * @param position The position of the feature.
     * @param key The i18n key of the feature.
     * @param features The sub features.
     *
     * @return The created feature.
     */
    protected static Feature createSubFeature(int position, String key, Feature... features){
        Feature feature = new Feature(Feature.FeatureType.ACTIONS, key, position);

        for(Feature f : features){
            feature.addSubFeature(f);
        }

        return feature;
    }

    /**
     * Create a separated (it seems with a line separator) feature.
     *
     * @param position The position of the feature.
     * @param action The name of the action. This action will searched in Spring context.
     * @param imagesBaseName The image's base name.
     * @param image The image name.
     *
     * @return The created separated feature.
     */
    protected static Feature createSeparatedSubFeature(int position, String action, String imagesBaseName, String image){
        Feature f = createSeparatedSubFeature(position, action);

        f.setBaseName(imagesBaseName);
        f.setIcon(image);

        return f;
    }

    /**
     * Create a separated (it seems with a line separator) feature.
     *
     * @param position The position of the feature.
     * @param action The action.
     * @param imagesBaseName The image's base name.
     * @param image The image name.
     *
     * @return The created separated feature.
     */
    protected static Feature createSeparatedSubFeature(int position, Action action, String imagesBaseName, String image){
        Feature f = createSeparatedSubFeature(position, action);

        f.setBaseName(imagesBaseName);
        f.setIcon(image);

        return f;
    }

    /**
     * Create a separated (it seems with a line separator) feature.
     *
     * @param position The position of the feature.
     * @param action The action of the feature.
     *
     * @return The created separated feature.
     */
    protected static Feature createSeparatedSubFeature(int position, Action action){
        return new Feature(Feature.FeatureType.SEPARATED_ACTION, position, action);
    }

    /**
     * Create a feature.
     *
     * @param position The position of the feature.
     * @param action The name of the action. This action will searched in Spring context.
     * @param imagesBaseName The image's base name.
     * @param image The image name.
     *
     * @return The created feature.
     */
    protected static Feature createSubFeature(int position, String action, String imagesBaseName, String image){
        Feature f = createSubFeature(position, action);

        f.setBaseName(imagesBaseName);
        f.setIcon(image);

        return f;
    }

    /**
     * Create a feature.
     *
     * @param position The position of the feature.
     * @param action The action. 
     * @param imagesBaseName The image's base name.
     * @param image The image name.
     *
     * @return The created feature.
     */
    protected static Feature createSubFeature(int position, Action action, String imagesBaseName, String image){
        Feature f = createSubFeature(position, action);

        f.setBaseName(imagesBaseName);
        f.setIcon(image);

        return f;
    }

    /**
     * Create a feature.
     *
     * @param position The position of the feature.
     * @param action The action of the feature.
     *
     * @return The created feature.
     */
    protected static Feature createSubFeature(int position, Action action){
        return new Feature(Feature.FeatureType.ACTION, position, action);
    }

    /**
     * Transform all the features into a List of features.
     *
     * @param features The features to put in the list.
     *
     * @return A List of features containing all the features in parameters.
     */
    protected static List<Feature> features(Feature... features){
        return Arrays.asList(features);
    }

    //Utility action methods
    
    /**
     * Create an action to close the view.
     *
      *@param key The i18n key.
     * @param view The view to close.
     *
     * @return An action to close the view.
     */
    public static Action createCloseViewAction(String key, IView view){
        return ActionFactory.createCloseViewAction(key, view);
    }

    /**
     * Create an action to close the view.
     *
      *@param key The i18n key.
     * @param view The name of the view to close. The action will be searched in Spring context.
     *
     * @return An action to close the view.
     */
    public static Action createCloseViewAction(String key, String view){
        return ActionFactory.createCloseViewAction(key, view);
    }

    /**
     * Create an action to display the view.
     *
      *@param key The i18n key.
     * @param view The view to close.
     *
     * @return An action to close the view.
     */
    public static Action createDisplayViewAction(String key, IView view){
        return ActionFactory.createDisplayViewAction(key, view);
    }

    /**
     * Create an action to display the view.
     *
      *@param key The i18n key.
     * @param view The name of the view to close. The action will be searched in Spring context.
     *
     * @return An action to close the view.
     */
    public static Action createDisplayViewAction(String key, String view){
        return ActionFactory.createDisplayViewAction(key, view);
    }
}