package org.jtheque.core.utils.ui;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.Animator.RepeatBehavior;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

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
    private static final float LONG_ACCELERATION = 0.4f;
    private static final float SHORT_ACCELERATION = 0.2f;
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
     * @return The animator.
     */
    public static Animator createFadeOutAnimator(Object view) {
        Animator fadeOut = PropertySetter.createAnimator(1000, view, "alpha", 0.0f);

        fadeOut.setAcceleration(SHORT_ACCELERATION);
        fadeOut.setDeceleration(LONG_ACCELERATION);

        return fadeOut;
    }

    /**
     * Create a fade in animator for a view.
     *
     * @param view The view to fade in.
     * @return The animator.
     */
    public static Animator createFadeInAnimator(Object view) {
        Animator fadeIn = PropertySetter.createAnimator(1000, view, "alpha", 1.0f);

        fadeIn.setAcceleration(LONG_ACCELERATION);
        fadeIn.setDeceleration(SHORT_ACCELERATION);

        return fadeIn;
    }

    /**
     * Create a spring effect animator for a view.
     *
     * @param view The view to animate.
     * @return The animator.
     */
    public static Animator createSpringEffectAnimator(Object view) {
        Animator springEffect = PropertySetter.createAnimator(SPRING_EFFECT_DURATION, view, "zoom", 0.0f, 1.0f);

        springEffect.setAcceleration(SHORT_ACCELERATION);
        springEffect.setDeceleration(LONG_ACCELERATION);

        return springEffect;
    }

    /**
     * Create a simple loop effect for the view.
     *
     * @param view     The view to animate.
     * @param duration The duration of the animation.
     * @param property The property to loop.
     * @param to       The to value.
     * @return The animator.
     */
    public static Animator createLoopEffect(Object view, int duration, String property, int to) {
        Animator loop = PropertySetter.createAnimator(duration, view, property, to);

        loop.setRepeatBehavior(RepeatBehavior.LOOP);
        loop.setRepeatCount(Animator.INFINITE);

        return loop;
    }

    /**
     * Fade in the view.
     *
     * @param view The view to fade in.
     */
    public static void startFadeIn(Object view) {
        createFadeInAnimator(view).start();
    }

    /**
     * Fade out the view.
     *
     * @param view The view to fade out.
     */
    public static void startFadeOut(Object view) {
        createFadeOutAnimator(view).start();
    }
}
