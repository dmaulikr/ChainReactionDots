package com.droid.joshxjin.chainreactiondots;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Joshua on 2/10/2016.
 */

public abstract class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public GamePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void startGame(int dotNumber);

    protected abstract float randPos(String pos);

    protected abstract float randSpeed();

    public abstract void update();

}
