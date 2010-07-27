package org.jtheque.features.impl;

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

import org.jtheque.features.able.IFeature;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;
import java.util.Collections;

/**
 * A Feature of JTheque.
 *
 * @author Baptiste Wicht
 */
public class Feature implements IFeature {
    private final Collection<IFeature> subFeatures = CollectionUtils.newList(15);
    private final JThequeAction action;
    private final FeatureType type;
    private final String titleKey;
    private final String icon;
    private final int position;

    /**
     * Construct a new Feature.
     *
     * @param action   The action.
     * @param position The position in the parent menu.
     * @param type     The type of feature.
     * @param titleKey The i18n title key.
     * @param icon     The icon name.
     */
    public Feature(JThequeAction action, int position, FeatureType type, String titleKey, String icon) {
        super();

        this.action = action;
        this.position = position;
        this.type = type;
        this.titleKey = titleKey;
        this.icon = icon;
    }

    @Override
    public final FeatureType getType() {
        return type;
    }

    @Override
    public final String getTitleKey() {
        return titleKey;
    }

    @Override
    public final JThequeAction getAction() {
        return action;
    }

    @Override
    public final Collection<IFeature> getSubFeatures() {
        return Collections.unmodifiableCollection(subFeatures);
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

    @Override
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
    public final int getPosition() {
        return position;
    }

    @Override
    public final String getIcon() {
        return icon;
    }
}