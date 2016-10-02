package com.droid.joshxjin.chainreactiondots;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Joshua on 2/10/2016.
 */

public class Dot implements GameObject {

    public static ArrayList<Dot> dots = new ArrayList<Dot>();

    private float x, y, dx, dy;
    private int colour;

    public Dot(float x, float y, float dx, float dy, int colour) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.colour = colour;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(this.colour);
        canvas.drawCircle(x, y, Constants.DOT_RADIUS, paint);
    }

    @Override
    public void update() {
        if (x - Constants.DOT_RADIUS <= 0 || x + Constants.DOT_RADIUS >= Constants.SCREEN_WIDTH) {
            dx = -dx;
        }

        if (y - Constants.DOT_RADIUS <= 0 || y + Constants.DOT_RADIUS >= Constants.SCREEN_HEIGHT) {
            dy = -dy;
        }

        x += dx;
        y += dy;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getColour() {
        return colour;
    }

}
