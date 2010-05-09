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
import org.jtheque.features.FeatureListener;
import org.jtheque.features.IFeatureService;
import org.jtheque.i18n.ILanguageService;
import org.jtheque.i18n.Internationalizable;
import org.jtheque.i18n.InternationalizableContainer;
import org.jtheque.resources.IResourceService;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.util.ArrayList;
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
    private final Comparator<Feature> featureComparator = new ByPositionComparator();

    private final IResourceService resourceService;
    private final ILanguageService languageService;
    private final IFeatureService featureService;

    private final Collection<Internationalizable> internationalizables = new ArrayList<Internationalizable>(25);

    public JThequeMenuBar(IResourceService resourceService, ILanguageService languageService, IFeatureService featureService) {
        super();

        this.resourceService = resourceService;
        this.languageService = languageService;
        this.featureService = featureService;

        languageService.addInternationalizable(this);
    }

    /**
     * A comparator to sort the Features by position.
     *
     * @author Baptiste Wicht
     */
    private static final class ByPositionComparator implements Comparator<Feature> {
        @Override
        public int compare(Feature feature, Feature other) {
            return feature.getPosition().compareTo(other.getPosition());
        }
    }

    /**
     * Build the menu.
     */
    public void buildMenu() {
        List<Feature> features = new ArrayList<Feature>(featureService.getFeatures());

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

        List<Feature> subFeatures = new ArrayList<Feature>(feature.getSubFeatures());

        Collections.sort(subFeatures, featureComparator);

        for (Feature subFeature : subFeatures) {
            addFeature(menu, subFeature);
        }

        menu.refreshText(languageService);

        add(menu);
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
            subMenu.setIcon(resourceService.getIcon(feature.getIcon()));
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
        addFeature(feature);
    }

    @Override
    public void featureRemoved(Feature feature) {
        for (int i = 0; i < getMenuCount(); i++) {
            JMenu menu = getMenu(i);

            if (isCorrespondingMenu(feature, menu)) {
                remove(menu);

                break;
            }
        }
    }

    @Override
    public void subFeatureAdded(Feature feature, Feature subFeature) {
        for (int i = 0; i < getMenuCount(); i++) {
            JMenu menu = getMenu(i);

            if (isCorrespondingMenu(feature, menu)) {
                menu.removeAll();

                List<Feature> subFeatures = new ArrayList<Feature>(feature.getSubFeatures());

                Collections.sort(subFeatures, featureComparator);

                for (Feature sub : subFeatures) {
                    addFeature(menu, sub);
                }

                break;
            }
        }
    }

    /**
     * Indicate if the menu correspond to the feature
     *
     * @param feature The feature to test.
     * @param menu    The menu to test.
     * @return true if the menu is corresponding to the feature else false.
     */
    private boolean isCorrespondingMenu(Feature feature, AbstractButton menu) {
        return menu != null && menu.getText().equals(languageService.getMessage(feature.getTitleKey()));
    }

    @Override
    public void subFeatureRemoved(Feature feature, Feature subFeature) {
        for (int i = 0; i < getMenuCount(); i++) {
            JMenu menu = getMenu(i);

            if (isCorrespondingMenu(feature, menu)) {
                removeMenu(subFeature, menu);

                break;
            }
        }
    }

    /**
     * Remove the feature of the event from the menu.
     *
     * @param subFeature The sub feature.
     * @param menu  The menu to the remove the menu from.
     */
    private void removeMenu(Feature subFeature, JMenu menu) {
        String subtitle = getSubtitle(subFeature);

        for (int z = 0; z < menu.getItemCount(); z++) {
            JMenuItem item = menu.getItem(z);

            //Perhaps, it's a separator
            if (item != null) {
                if (item.getAction() != null && item.getAction().getValue(Action.NAME).equals(subtitle)) {
                    menu.remove(item);
                } else if (item.getText() != null && item.getText().equals(subtitle)) {
                    menu.remove(item);
                }

                clearMenu(menu);
            }
        }
    }

    /**
     * Clear the menu.
     *
     * @param menu The menu to clear.
     */
    private void clearMenu(JMenu menu) {
        if (menu.getItemCount() <= 0) {
            remove(menu);
        } else if (menu.getItem(0) == null) {
            menu.remove(0);
        }
    }

    private String getSubtitle(Feature subFeature) {
        if (subFeature.getAction() == null) {
            return languageService.getMessage(subFeature.getTitleKey());
        } else {
            return (String) subFeature.getAction().getValue(Action.NAME);
        }
    }

    @Override
    public void addInternationalizable(Internationalizable internationalizable) {
        internationalizables.add(internationalizable);
    }

    @Override
    public void refreshText(ILanguageService languageService) {
        for (Internationalizable internationalizable : internationalizables) {
            internationalizable.refreshText(languageService);
        }

        SwingUtils.refresh(this);
    }
}