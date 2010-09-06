package org.jtheque.features;

import org.jtheque.features.Feature.FeatureType;
import org.jtheque.ui.utils.actions.JThequeAction;

import org.junit.Test;

import java.awt.event.ActionEvent;

import static org.junit.Assert.*;

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

public class FeaturesTest {
    @Test
    public void typesAreCorrect(){
        assertEquals(FeatureType.PACK, Features.newPackFeature(0, "123").getType());

        assertEquals(FeatureType.ACTION, Features.newActionFeature(0, new TestAction()).getType());
        assertEquals(FeatureType.ACTION, Features.newActionFeature(0, new TestAction(), "icon").getType());

        assertEquals(FeatureType.SEPARATED_ACTION, Features.newSeparatedActionFeature(0, new TestAction()).getType());
        assertEquals(FeatureType.SEPARATED_ACTION, Features.newSeparatedActionFeature(0, new TestAction(), "icon").getType());

        assertEquals(FeatureType.ACTIONS, Features.newActionsFeature(0, "asfs").getType());
        assertEquals(FeatureType.SEPARATED_ACTIONS, Features.newSeparatedActionsFeature(0, "asfs").getType());
    }

    @Test
    public void newPackFeature(){
        Feature subFeature1 = Features.newActionFeature(22, new TestAction());
        Feature subFeature2 = Features.newActionFeature(33, new TestAction());

        Feature feature = Features.newPackFeature(123, "test.key", subFeature1, subFeature2);

        assertEquals(123, feature.getPosition());
        assertEquals("test.key", feature.getTitleKey());
        assertEquals(2, feature.getSubFeatures().size());
        assertTrue(feature.getSubFeatures().contains(subFeature1));
        assertTrue(feature.getSubFeatures().contains(subFeature2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullAction(){
        Features.newActionFeature(22, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativePosition() {
        Features.newActionFeature(-22, new TestAction());
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyKey() {
        Features.newPackFeature(0, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyIcon() {
        Features.newActionFeature(0, new TestAction(), "");
    }

    private static final class TestAction extends JThequeAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            //Nothing to do
        }
    }
}