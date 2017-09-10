package com.ru.tgra.objects;

import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.shapes.RectangleGraphic;
import com.ru.tgra.utilities.Point2D;

public class Block extends GameObject
{
    private int health;

    public Block(Point2D position, int health)
    {
        super();

        this.position = position;
        this.health = health;
    }

    @Override
    public void draw()
    {
        super.draw();

        RectangleGraphic.drawOutline();

        GraphicsEnvironment.drawText(new Point2D(position.x - 5, position.y + 5), Integer.toString(health), color);
    }

    public void update(float deltaTime)
    {
        // TODO
    }
}
