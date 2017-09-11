package com.ru.tgra.objects;

import com.ru.tgra.shapes.RectangleGraphic;
import com.ru.tgra.utilities.Color;
import com.ru.tgra.utilities.Point2D;

public class Aimer extends GameObject
{
    public Aimer(Point2D position, Color color)
    {
        super();

        this.position = position;
        this.color = color;

        scale.x = 5;
        scale.y = 850;
    }

    @Override
    public void draw()
    {
        super.draw();

        RectangleGraphic.setVertexBuffer(false);
        RectangleGraphic.drawSolid();
        RectangleGraphic.setVertexBuffer(true);
    }

    public void update(float deltaTime)
    {
        // TODO
    }
}
