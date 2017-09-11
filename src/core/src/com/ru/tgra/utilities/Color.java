package com.ru.tgra.utilities;

public class Color
{
    private float r;
    private float g;
    private float b;
    private float a;

    public Color(float red, float green, float blue, float alpha)
    {
        this.r = red;
        this.g = green;
        this.b = blue;
        this.a = alpha;
    }

    public float getRed()
    {
        return r;
    }

    public void setRed(float r)
    {
        this.r = r;
    }

    public float getGreen()
    {
        return g;
    }

    public void setGreen(float g)
    {
        this.g = g;
    }

    public float getBlue()
    {
        return b;
    }

    public void setBlue(float b)
    {
        this.b = b;
    }

    public float getAlpha()
    {
        return a;
    }

    public void setAlpha(float a)
    {
        this.a = a;
    }
}
