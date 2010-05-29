package org.jtheque.features.able;

import org.jtheque.ui.utils.actions.JThequeAction;

import java.util.Collection;

/**
 * A feature specification. A Feature is an element of the menu. It can be an action of a group of actions. It can be
 * seperated or not.
 *
 * @author Baptiste Wicht
 */
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
    Integer getPosition();

    /**
     * Return the icon id of the feature.
     *
     * @return The icon id of the feature.
     */
    String getIcon();

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
