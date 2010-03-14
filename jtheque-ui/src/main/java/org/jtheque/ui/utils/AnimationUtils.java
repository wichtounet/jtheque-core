package org.jtheque.ui.utils;

import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.callback.TimelineCallbackAdapter;

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
 * An utility class for animation.
 *
 * @author Baptiste Wicht
 */
public final class AnimationUtils {
    private static final int SPRING_EFFECT_DURATION = 400;

    /**
     * This is an utility class, not instanciable.
     */
    private AnimationUtils() {
        super();
    }

    /**
     * Create a Fade Out animator for the view.
     *
     * @param view The view to fade out.
     *
     * @return The timeline animation.
     */
    public static Timeline createFadeOutAnimation(Object view) {
        return createInterpolationAnimation(view, 1000, "alpha", 1.0f, 0.0f);
    }

    /**
     * Create a fade in animator for a view.
     *
     * @param view The view to fade in.
     *
     * @return The timeline animation.
     */
    public static Timeline createFadeInAnimation(Object view) {
        return createInterpolationAnimation(view, 1000, "alpha", 0.0f, 1.0f);
    }

    /**
     * Create a spring effect animator for a view.
     *
     * @param view The view to animate.
     *
     * @return The timeline animation.
     */
    public static Timeline createSpringEffectAnimation(Object view) {
        return createInterpolationAnimation(view, SPRING_EFFECT_DURATION, "zoom", 0.0f, 1.0f);
    }

    /**
     * Create an animation to interpolate a property.
     *
     * @param object The bean on which the property must be interpolated.
     * @param duration The duration of the animation.
     * @param property The property to interpolate.
     * @param from The from value.
     * @param to The to value.
     *
     * @param <T> The type of property to interpolate.
     *
     * @return The timeline animation.
     */
    public static <T> Timeline createInterpolationAnimation(Object object, int duration, String property, T from, T to) {
        Timeline timeline = new Timeline(object);

        timeline.addPropertyToInterpolate(property, from, to);
        timeline.setDuration(duration);

        return timeline;
    }

    /**
     * Fade in the view.
     *
     * @param view The view to fade in.
     *
     * @return The timeline animation.
     */
    public static Timeline startFadeIn(Object view) {
        Timeline animation = createFadeInAnimation(view);

        animation.play();

        return animation;
    }

    /**
     * Fade out the view.
     *
     * @param view The view to fade out.
     *
     * @return The timeline animation.
     */
    public static Timeline startFadeOut(Object view) {
        Timeline animation = createFadeOutAnimation(view);

        animation.play();

        return animation;
    }

    /**
     * Starts looping of the target when the source animation is done.
     *
     * @param source The source animation.
     * @param target The target animation.
     */
    public static void startsLoopWhenStop(Timeline source, final Timeline target) {
        source.addCallback(new TimelineCallbackAdapter(){
            @Override
            public void onTimelineStateChanged(Timeline.TimelineState oldState, Timeline.TimelineState newState,
                                               float durationFraction, float timelinePosition) {
                if(newState == Timeline.TimelineState.DONE){
                    target.playLoop(Timeline.RepeatBehavior.LOOP);
                }
            }
        });
    }
}