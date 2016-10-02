package com.droid.joshxjin.chainreactiondots;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Joshua on 2/10/2016.
 */

public class ExplodedDot implements GameObject {

    public static ArrayList<ExplodedDot> explodedDots = new ArrayList<ExplodedDot>();

    private float x, y, radius;
    private int colour;
    private boolean explode = true;
    private boolean active = true;
    private long explosionTime;

    public ExplodedDot(float x, float y, int colour) {
        this.x = x;
        this.y = y;
        this.colour = colour;
        this.radius = Constants.DOT_RADIUS;
    }

    @Override
    public void draw(Canvas canvas) {
        if (active) {
            Paint paint = new Paint();
            paint.setColor(this.colour);
            canvas.drawCircle(x, y, radius, paint);
        }
    }

    @Override
    public void update() {
        if (explode) {
            radius += 2;
            if (radius >= Constants.EXPLOSION_RADIUS) {
                explode = false;
                explosionTime = System.nanoTime();
            }
        } else if (!explode && System.nanoTime() - explosionTime >= Constants.EXPLODE_TIME) {
            if (radius <= 0) {
                active = false;
            } else {
                radius -= 2;
            }
        }
    }

    public ArrayList<ExplodedDot> update(ArrayList<Dot> dots) {
        ArrayList<Dot> explodedDots = new ArrayList<Dot>();
        ArrayList<ExplodedDot> newExplodedDots = new ArrayList<ExplodedDot>();

        this.update();

        for (Dot dot : dots) {
            float distance = (float)Math.hypot(this.x - dot.getX(), this.y - dot.getY());
            if (distance <= (this.radius + Constants.DOT_RADIUS)) {
                explodedDots.add(dot);
                newExplodedDots.add(new ExplodedDot(dot.getX(), dot.getY(), dot.getColour()));
            }
        }

        if (!explodedDots.isEmpty()) {
            dots.removeAll(explodedDots);
        }

        return newExplodedDots;
    }

    public boolean isActive() {
        return active;
    }
}
