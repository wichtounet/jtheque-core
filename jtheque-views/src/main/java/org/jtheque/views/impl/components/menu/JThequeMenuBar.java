package org.jtheque.views.impl.components.menu;

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

import org.jtheque.features.Feature;
import org.jtheque.features.FeatureService;
import org.jtheque.features.FeatureListener;
import org.jtheque.i18n.Internationalizable;
import org.jtheque.i18n.LanguageService;
import org.jtheque.i18n.InternationalizableContainer;
import org.jtheque.images.ImageService;
import org.jtheque.utils.bean.Numbers;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.AbstractButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A menu bar for the main view.
 *
 * @author Baptiste Wicht
 */
public final class JThequeMenuBar extends JMenuBar implements FeatureListener, InternationalizableContainer {
    private final Collection<Internationalizable> internationalizables = CollectionUtils.newList(25);
    private final Comparator<Feature> featureComparator = new ByPositionComparator();
    private final ImageService imageService;
    private final LanguageService languageService;
    private final FeatureService featureService;

    /**
     * Construct a JThequeMenuBar.
     *
     * @param imageService    The resource service.
     * @param languageService The language service.
     * @param featureService  The feature service.
     */
    public JThequeMenuBar(ImageService imageService, LanguageService languageService, FeatureService featureService) {
        super();

        this.imageService = imageService;
        this.languageService = languageService;
        this.featureService = featureService;

        languageService.addInternationalizable(this);
    }

    /**
     * Build the menu.
     */
    public void buildMenu() {
        List<Feature> features = CollectionUtils.copyOf(featureService.getFeatures());

        Collections.sort(features, featureComparator);

        for (Feature feature : features) {
            addFeature(feature);
        }

        featureService.addFeatureListener(this);
    }


    /**
     * Add a feature.
     *
     * @param feature The feature to add.
     */
    private void addFeature(Feature feature) {
        JThequeMenu menu = new JThequeMenu(feature.getTitleKey());

        addInternationalizable(menu);

        addSubFeatures(feature, menu);

        menu.refreshText(languageService);

        add(menu);
    }

    /**
     * Add the sub features of the feature to the given menu.
     *
     * @param feature The feature to get the sub features from.
     * @param menu    The menu to add the subfeatures to.
     */
    private void addSubFeatures(Feature feature, JMenu menu) {
        List<Feature> subFeatures = CollectionUtils.copyOf(feature.getSubFeatures());

        Collections.sort(subFeatures, featureComparator);

        for (Feature subFeature : subFeatures) {
            addFeature(menu, subFeature);
        }
    }

    /**
     * Add a feature.
     *
     * @param menu    The JMenu.
     * @param feature The feature.
     */
    private void addFeature(JMenu menu, Feature feature) {
        switch (feature.getType()) {
            case ACTION:
                addMenuItem(menu, feature);

                break;
            case SEPARATED_ACTION:
                addSeparator(menu);
                addMenuItem(menu, feature);

                break;
            case SEPARATED_ACTIONS:
                addSeparator(menu);
                addActions(menu, feature);

                break;
            case ACTIONS:
                addActions(menu, feature);

                break;
        }
    }

    /**
     * Add a menu item to the menu.
     *
     * @param menu    The menu to add the item to.
     * @param feature The feature to add to the menu.
     */
    private void addMenuItem(JMenu menu, Feature feature) {
        feature.getAction().refreshText(languageService);

        menu.add(new JThequeMenuItem(feature.getAction()));
    }

    /**
     * Add actions to the menu.
     *
     * @param menu    The menu to add the actions to.
     * @param feature The feature to add to the menu.
     */
    private void addActions(JMenu menu, Feature feature) {
        JThequeMenu subMenu = new JThequeMenu(feature.getTitleKey());

        addInternationalizable(subMenu);

        if (feature.getIcon() != null) {
            subMenu.setIcon(imageService.getIcon(feature.getIcon()));
        }

        for (Feature subFeature : feature.getSubFeatures()) {
            addFeature(subMenu, subFeature);
        }

        subMenu.refreshText(languageService);

        menu.add(subMenu);
    }

    /**
     * Add a separator to the menu.
     *
     * @param menu The menu.
     */
    private static void addSeparator(JMenu menu) {
        if (menu.getItemCount() > 0) {
            menu.addSeparator();
        }
    }

    @Override
    public void featureAdded(Feature feature) {
        SwingUtils.inEdt(new Runnable(){
            @Override
            public void run() {
                removeAll();

                buildMenu();

                SwingUtils.refresh(JThequeMenuBar.this);
            }
        });
    }

    @Override
    public void featureRemoved(Feature feature) {
        for (int i = 0; i < getMenuCount(); i++) {
            final JMenu menu = getMenu(i);

            if (isCorrespondingMenu(feature, menu)) {
                SwingUtils.inEdt(new Runnable() {
                    @Override
                    public void run() {
                        remove(menu);
                    }
                });

                break;
            }
        }
    }

    /**
     * Indicate if the menu correspond to the feature
     *
     * @param feature The feature to test.
     * @param menu    The menu to test.
     *
     * @return true if the menu is corresponding to the feature else false.
     */
    private boolean isCorrespondingMenu(Feature feature, AbstractButton menu) {
        return menu != null && menu.getText().equals(languageService.getMessage(feature.getTitleKey()));
    }

    @Override
    public void featureModified(final Feature feature) {
        for (int i = 0; i < getMenuCount(); i++) {
            final JMenu menu = getMenu(i);

            if (isCorrespondingMenu(feature, menu)) {
                SwingUtils.inEdt(new Runnable() {
                    @Override
                    public void run() {
                        menu.removeAll();

                        addSubFeatures(feature, menu);
                    }
                });

                break;
            }
        }
    }

    @Override
    public void addInternationalizable(Internationalizable internationalizable) {
        internationalizables.add(internationalizable);
    }

    @Override
    public void refreshText(LanguageService languageService) {
        for (Internationalizable internationalizable : internationalizables) {
            internationalizable.refreshText(languageService);
        }

        SwingUtils.refresh(this);
    }

    /**
     * A comparator to sort the Features by position.
     *
     * @author Baptiste Wicht
     */
    private static final class ByPositionComparator implements Comparator<Feature>, Serializable {
        private static final long serialVersionUID = 2723796561895917073L;

        @Override
        public int compare(Feature feature, Feature other) {
            return Numbers.compare(feature.getPosition(), other.getPosition());
        }
    }
}