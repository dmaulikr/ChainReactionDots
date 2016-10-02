package com.droid.joshxjin.chainreactiondots;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Joshua on 2/10/2016.
 */

public class splashBackGround extends GamePanel {

    private MainThread thread;

    private Random r = new Random();

    public splashBackGround(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Get the holder of the screen and register interest
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        startGame(20);

        setFocusable(true);
    }

    public void startGame(int dotNumber) {
        //Clear all previously store dots
        Dot.dots.clear();
        ExplodedDot.explodedDots.clear();

        for (int i = 0; i < dotNumber; i++) {
            Dot.dots.add(new Dot(randPos("width"), randPos("height"), randSpeed(), randSpeed(),
                    Constants.COLOURS[r.nextInt(4)]));
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder hold, int format, int width, int height) {

    }

    protected float randPos(String pos) {
        float maxWidth = Constants.SCREEN_WIDTH - Constants.DOT_RADIUS;
        float minWidth = 0 + Constants.DOT_RADIUS;
        float maxHeight = Constants.SCREEN_HEIGHT - Constants.DOT_RADIUS;
        float minHeight = 0 + Constants.DOT_RADIUS;
        switch (pos) {
            case "width":
                return r.nextFloat() * (maxWidth - minWidth + 1) + minWidth;
            case "height":
                return r.nextFloat() * (maxHeight - minHeight + 1) + minHeight;
        }

        return 0;
    }

    protected float randSpeed() {
        int[] i = {-1, 1};

        return i[r.nextInt(2)] * (r.nextFloat() * (Constants.MAX_SPEED - Constants.MIN_SPEED + 1) + Constants.MIN_SPEED);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void update() {

        for (Dot dot : Dot.dots) {
            dot.update();
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //Background colour, white.
        canvas.drawColor(Color.WHITE);

        for (Dot dot :  Dot.dots) {
            dot.draw(canvas);
        }

    }

}
