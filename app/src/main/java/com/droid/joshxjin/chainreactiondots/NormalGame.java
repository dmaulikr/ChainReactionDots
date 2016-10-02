package com.droid.joshxjin.chainreactiondots;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Joshua on 2/10/2016.
 */

public class NormalGame extends GamePanel implements SurfaceHolder.Callback {
    private MainThread thread;

    private Random r = new Random();
    private boolean playerExplosion;
    private boolean gameOver;

    public NormalGame(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Get the holder of the screen and register interest
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        startGame(Constants.dotNumber);

        setFocusable(true);
    }

    public void startGame(int dotNumber) {
        playerExplosion = false;
        gameOver = false;

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
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (gameOver) {
                    startGame(Constants.dotNumber);
                } else if (!playerExplosion) {
                    ExplodedDot.explodedDots.add(new ExplodedDot(event.getX(), event.getY(), Color.argb(100, 0, 0, 200)));
                    playerExplosion = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //playerPoint.set((int)event.getX(), (int)event.getY());
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
        //return super.onTouchEvent(event);
    }

    public void update() {
        if (!gameOver) {
            ArrayList<ExplodedDot> newExplodedDots = new ArrayList<ExplodedDot>();
            ArrayList<ExplodedDot> oldExplodedDots = new ArrayList<ExplodedDot>();

            for (Dot dot : Dot.dots) {
                dot.update();
            }

            for (ExplodedDot explodedDot : ExplodedDot.explodedDots) {
                //explodedDot.update();
                if (explodedDot.isActive()) {
                    newExplodedDots.addAll(explodedDot.update(Dot.dots));
                } else {
                    oldExplodedDots.add(explodedDot);
                }
            }

            if (!newExplodedDots.isEmpty()) {
                ExplodedDot.explodedDots.addAll(newExplodedDots);
            }

            if (!oldExplodedDots.isEmpty()) {
                ExplodedDot.explodedDots.removeAll(oldExplodedDots);
            }

            if (playerExplosion && ExplodedDot.explodedDots.isEmpty()) {
                gameOver = true;
            }
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

        for (ExplodedDot explodedDot : ExplodedDot.explodedDots) {
            explodedDot.draw(canvas);
        }
    }
}
