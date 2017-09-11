package com.ru.tgra.objects;

import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.shapes.RectangleGraphic;
import com.ru.tgra.utilities.Point2D;

public class Layout extends GameObject
{
    public Layout(Point2D position)
    {
        super();

        this.position = position;

        scale.x = position.x * 2;
        scale.y = position.y * 2;
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
