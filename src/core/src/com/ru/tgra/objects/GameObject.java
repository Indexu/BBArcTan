package com.ru.tgra.objects;

import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.utilities.Color;
import com.ru.tgra.utilities.ModelMatrix;
import com.ru.tgra.utilities.Point2D;
import com.ru.tgra.utilities.Vector2D;
import com.sun.org.apache.xpath.internal.operations.Mod;

public abstract class GameObject
{
    protected Point2D position;
    protected Vector2D direction;
    protected float speed;
    protected float rotation;
    protected Vector2D scale;
    protected Color color;
    protected boolean destroyed;

    public GameObject()
    {
        position = new Point2D();
        direction = new Vector2D();
        speed = 0;
        rotation = 0;
        scale = new Vector2D();
        color = new Color(1, 1, 1, 1);
        destroyed = false;
    }

    public GameObject(Point2D position, Vector2D direction, float speed, float rotation, Vector2D scale, Color color)
    {
        this.position = position;
        this.direction = direction;
        this.speed = speed;
        this.rotation = rotation;
        this.scale = scale;
        this.color = color;

        destroyed = false;
    }

    public void draw()
    {
        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position);
        ModelMatrix.main.addRotationZ(rotation);
        ModelMatrix.main.addScale(scale);
        ModelMatrix.main.setShaderMatrix(GraphicsEnvironment.getModelMatrixLoc());

        GraphicsEnvironment.setColor(color);
    }

    public abstract void update(float deltaTime);

    public Point2D[] getPoints()
    {
        return null;
    }

    public void destroy()
    {
        destroyed = true;
    }

    public boolean isDestroyed()
    {
        return destroyed;
    }

    public Point2D getPosition()
    {
        return position;
    }

    public void setPosition(Point2D position)
    {
        this.position = position;
    }

    public Vector2D getDirection() {
        return direction;
    }

    public void setDirection(Vector2D direction)
    {
        this.direction = direction;
    }

    public float getSpeed()
    {
        return speed;
    }

    public void setSpeed(float speed)
    {
        this.speed = speed;
    }

    public float getRotation()
    {
        return rotation;
    }

    public void setRotation(float rotation)
    {
        this.rotation = rotation;
    }

    public Vector2D getScale()
    {
        return scale;
    }

    public void setScale(Vector2D scale)
    {
        this.scale = scale;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }
}
