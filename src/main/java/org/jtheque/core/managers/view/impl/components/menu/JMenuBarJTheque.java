package org.jtheque.core.managers.view.impl.components.menu;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.feature.Feature;
import org.jtheque.core.managers.feature.FeatureEvent;
import org.jtheque.core.managers.feature.FeatureListener;
import org.jtheque.core.managers.feature.IFeatureManager;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A menu bar for the main view.
 *
 * @author Baptiste Wicht
 */
public final class JMenuBarJTheque extends JMenuBar implements FeatureListener {
    private final Comparator<Feature> featureComparator = new ByPositionComparator();

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
     * Construct a new JMenuBarJTheque.
     */
    public JMenuBarJTheque() {
        super();

        buildMenu();
    }

    /**
     * Build the menu.
     */
    private void buildMenu() {
        List<Feature> features = new ArrayList<Feature>(Managers.getManager(IFeatureManager.class).getFeatures());

        Collections.sort(features, featureComparator);

        for (Feature feature : features) {
            if (!feature.getSubFeatures().isEmpty()) {
                addFeature(feature);
            }
        }

        Managers.getManager(IFeatureManager.class).addFeatureListener(this);
    }


    /**
     * Add a feature.
     *
     * @param feature The feature to add.
     */
    private void addFeature(Feature feature) {
        JMenu menu = new JThequeMenu(feature.getTitleKey());

        List<Feature> subFeatures = new ArrayList<Feature>(feature.getSubFeatures());

        Collections.sort(subFeatures, featureComparator);

        for (Feature subFeature : subFeatures) {
            addFeature(menu, subFeature);
        }

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
    private static void addMenuItem(JMenu menu, Feature feature) {
        menu.add(new JThequeMenuItem(feature.getAction()));
    }

    /**
     * Add actions to the menu.
     *
     * @param menu    The menu to add the actions to.
     * @param feature The feature to add to the menu.
     */
    private void addActions(JMenu menu, Feature feature) {
        JMenu subMenu = new JThequeMenu(feature.getTitleKey());

        if (feature.getIcon() != null) {
            subMenu.setIcon(Managers.getManager(IResourceManager.class).getIcon(feature.getBaseName(), feature.getIcon(), ImageType.PNG));
        }

        for (Feature subFeature : feature.getSubFeatures()) {
            addFeature(subMenu, subFeature);
        }

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
    public void featureAdded(FeatureEvent event) {
        addFeature(event.getFeature());
    }

    @Override
    public void featureRemoved(FeatureEvent event) {
        for (int i = 0; i < getMenuCount(); i++) {
            JMenu menu = getMenu(i);

            if (isCorrespondingMenu(event.getFeature(), menu)) {
                remove(menu);

                break;
            }
        }
    }

    @Override
    public void subFeatureAdded(FeatureEvent event) {
        for (int i = 0; i < getMenuCount(); i++) {
            JMenu menu = getMenu(i);

            if (isCorrespondingMenu(event.getFeature(), menu)) {
                menu.removeAll();

                List<Feature> subFeatures = new ArrayList<Feature>(event.getFeature().getSubFeatures());

                Collections.sort(subFeatures, featureComparator);

                for (Feature subFeature : subFeatures) {
                    addFeature(menu, subFeature);
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
    private static boolean isCorrespondingMenu(Feature feature, AbstractButton menu) {
        return menu != null && menu.getText().equals(Managers.getManager(ILanguageManager.class).getMessage(
                feature.getTitleKey()));
    }

    @Override
    public void subFeatureRemoved(FeatureEvent event) {
        for (int i = 0; i < getMenuCount(); i++) {
            JMenu menu = getMenu(i);

            if (isCorrespondingMenu(event.getFeature(), menu)) {
                removeMenu(event, menu);

                break;
            }
        }
    }

    /**
     * Remove the feature of the event from the menu.
     *
     * @param event The event.
     * @param menu  The menu to the remove the menu from.
     */
    private void removeMenu(FeatureEvent event, JMenu menu) {
        String subtitle = getSubtitle(event);

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

    /**
     * Return the title of the sub feature of the event.
     *
     * @param event The feature event.
     * @return The title of the sub feature of the event.
     */
    private static String getSubtitle(FeatureEvent event) {
        if (event.getSubFeature().getAction() == null) {
            return Managers.getManager(ILanguageManager.class).getMessage(event.getSubFeature().getTitleKey());
        } else {
            return (String) event.getSubFeature().getAction().getValue(Action.NAME);
        }
    }
}