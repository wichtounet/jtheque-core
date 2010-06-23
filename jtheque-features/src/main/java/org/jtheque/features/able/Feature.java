package org.jtheque.features.able;

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

import org.jtheque.ui.utils.actions.JThequeAction;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A Feature of JTheque.
 *
 * @author Baptiste Wicht
 */
public class Feature implements IFeature {
    private final Collection<IFeature> subFeatures = new ArrayList<IFeature>(20);
    private final JThequeAction action;
    private final Integer position;

    private FeatureType type;
    private String titleKey;
    private String icon;

    /**
     * Construct a new Feature for an action.
     *
     * @param type     The type of feature.
     * @param position The position of the feature in the parent.
     * @param action   The action to execute when the feature is pressed.
     */
    public Feature(FeatureType type, Integer position, JThequeAction action) {
        super();

        this.action = action;
        this.type = type;
        this.position = position;
    }

    /**
     * Construct a new Feature for a menu.
     *
     * @param type     The type of feature.
     * @param titleKey The i18n key of the title of the feature.
     * @param position The position of the feature in the parent.
     */
    public Feature(FeatureType type, String titleKey, Integer position) {
        super();

        action = null;
        this.type = type;
        this.titleKey = titleKey;
        this.position = position;
    }

    @Override
    public final FeatureType getType() {
        return type;
    }

    /**
     * Set the type of the feature.
     *
     * @param type The type of the feature.
     */
    public final void setType(FeatureType type) {
        this.type = type;
    }

    @Override
    public final String getTitleKey() {
        return titleKey;
    }

    /**
     * Set the internationalization key of the title of the feature.
     *
     * @param titleKey The title key.
     */
    public final void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    @Override
    public final JThequeAction getAction() {
        return action;
    }

    @Override
    public final Collection<IFeature> getSubFeatures() {
        return subFeatures;
    }

    /**
     * Add all the given sub features to this feature.
     *
     * @param subFeatures A collection of sub features to add to the feature.
     */
    public final void addSubFeatures(Iterable<IFeature> subFeatures) {
        for (IFeature subFeature : subFeatures) {
            addSubFeature(subFeature);
        }
    }

    /**
     * Add a sub feature to the feature.
     *
     * @param feature The feature to add.
     */
    public void addSubFeature(IFeature feature) {
        if (feature.getType() == FeatureType.PACK) {
            throw new IllegalArgumentException(
                    "Unable to add feature of type Pack to a menu");
        }

        subFeatures.add(feature);
    }

    /**
     * Remove a sub feature to the feature.
     *
     * @param feature The feature to remove.
     */
    public void removeSubFeature(IFeature feature) {
        subFeatures.remove(feature);
    }

    /**
     * Remove all the given sub features from the feature.
     *
     * @param features The sub features to remove.
     */
    public final void removeSubFeatures(Collection<IFeature> features) {
        subFeatures.removeAll(features);
    }

    @Override
    public final Integer getPosition() {
        return position;
    }

    @Override
    public final String getIcon() {
        return icon;
    }

    /**
     * Set the icon of the feature.
     *
     * @param icon The icon of the feature.
     */
    public final void setIcon(String icon) {
        this.icon = icon;
    }
}