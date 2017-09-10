package com.ru.tgra.utilities;

public class Point2D {

    public float x;
    public float y;

    public Point2D()
    {
        this.x = 0;
        this.y = 0;
    }

    public Point2D(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2D v)
    {
        x += v.x;
        y += v.y;
    }
}
