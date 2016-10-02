package com.droid.joshxjin.chainreactiondots;

import android.graphics.Color;

/**
 * Created by Joshua on 2/10/2016.
 */

public class Constants {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static final int DOT_RADIUS = 25;
    public static final float MAX_SPEED = 15f;
    public static final float MIN_SPEED = 5f;
    public static final int[] COLOURS = {Color.argb(100, 50, 100, 200),     //blue
            Color.argb(100, 200, 50, 100),  //red
            Color.argb(100, 200, 100, 50),  //orange
            Color.argb(100, 50, 200, 100)   //green
    };
    public static final int EXPLOSION_RADIUS = 60;
    public static final long EXPLODE_TIME = 2000000000; //2seconds or 2000000000 nanoseconds

    public static int dotNumber;
    public static int normalTargetNumber;
    public static float survivalTarget;
}
