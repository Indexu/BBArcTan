package com.ru.tgra.objects;

import com.badlogic.gdx.Gdx;
import com.ru.tgra.shapes.RectangleGraphic;
import com.ru.tgra.utilities.Point2D;

public class Layout extends GameObject
{
    public static Point2D[] points;

    public Layout(Point2D position)
    {
        super();

        this.position = position;

        scale.x = position.x * 2;
        scale.y = position.y * 2;

        Point2D topRight = new Point2D(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Point2D bottomRight = new Point2D(Gdx.graphics.getWidth(), 0);
        Point2D bottomLeft = new Point2D(0, 0);
        Point2D topLeft = new Point2D(0, Gdx.graphics.getHeight());

        points = new Point2D[] { bottomLeft, topLeft, topRight, bottomRight };
    }

    @Override
    public void draw()
    {
        super.draw();

        RectangleGraphic.drawSolid();
    }

    public void update(float deltaTime)
    {
        // TODO
    }
}
