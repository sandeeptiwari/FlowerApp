package com.flowerapp.others;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.animation.PathInterpolator;

/**
 * Created by Sandeep Tiwari on 6/16/2016.
 */

public class Util {

    public final static int COLOR_ANIMATION_DURATION = 1000;
    public final static int DEFAULT_DELAY = 0;

    /**
     * calculate the optimal width for the image
     *
     * @param screenWidth
     * @return
     */
    public static int optimalImageWidth(int screenWidth) {
        int preOptimalWidth = screenWidth / 2;

        if (preOptimalWidth >= 720) {
            return 720;
        } else if (preOptimalWidth >= 540) {
            return 540;
        } else if (preOptimalWidth >= 360) {
            return 360;
        } else {
            return 360;
        }
    }

    public static void animateViewColor(View v, int startColor, int endColor) {

        Log.i("Palet", "view start"+startColor);
        ObjectAnimator animator = ObjectAnimator.ofObject(v, "backgroundColor",
                new ArgbEvaluator(), startColor, endColor);

        if (Build.VERSION.SDK_INT >= 21) {
            animator.setInterpolator(new PathInterpolator(0.4f, 0f, 1f, 1f));
        }
        animator.setDuration(COLOR_ANIMATION_DURATION);
        animator.start();
    }
}
