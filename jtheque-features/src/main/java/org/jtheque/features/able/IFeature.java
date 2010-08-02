package org.jtheque.features.able;

import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.utils.annotations.ThreadSafe;

import java.util.Collection;

/**
 * A feature specification. A Feature is an element of the menu. It can be an action of a group of actions. It can be
 * separated or not. The only thing we can edit on a Feature it's adding new sub feature to it.
 * <p/>
 * The implementations of this class must be thread safe.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public interface IFeature {
    /**
     * Return the type of the feature.
     *
     * @return The type of the feature.
     */
    FeatureType getType();

    /**
     * Return the internationalization key of the title of the feature.
     *
     * @return The internationalisation key of the feature.
     */
    String getTitleKey();

    /**
     * Return the action of the feature.
     *
     * @return The action.
     */
    JThequeAction getAction();

    /**
     * Return the sub features of the feature.
     *
     * @return A List containing all the sub feature of the feature.
     */
    Collection<IFeature> getSubFeatures();

    /**
     * Return the position of the feature.
     *
     * @return The position of the feature.
     */
    int getPosition();

    /**
     * Return the icon id of the feature.
     *
     * @return The icon id of the feature.
     */
    String getIcon();

    /**
     * Add a sub feature to the feature.
     *
     * @param feature The feature to add.
     */
    void addSubFeature(IFeature feature);

    /**
     * Add a collection of sub-features to the feature.
     *
     * @param subFeatures The sub-features to add.
     */
    void addSubFeatures(Collection<IFeature> subFeatures);

    /**
     * Remove a collection of sub-features from the feature.
     *
     * @param subFeatures The sub-features to remove.
     */
    void removeSubFeatures(Collection<IFeature> subFeatures);

    /**
     * The Feature Type.
     *
     * @author Baptiste Wicht
     */
    enum FeatureType {
        PACK,
        SEPARATED_ACTIONS,
        ACTIONS,
        SEPARATED_ACTION,
        ACTION
    }
}
