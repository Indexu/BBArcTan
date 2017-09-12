package com.ru.tgra.objects;

import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.Settings;
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

        scale.x = Settings.BlockSize;
        scale.y = Settings.BlockSize;
    }

    @Override
    public void draw()
    {
        super.draw();

        RectangleGraphic.drawOutline();

        GraphicsEnvironment.drawText(new Point2D(position.x - Settings.BlockTextOffset, position.y + Settings.BlockTextOffset), Integer.toString(health), color);
    }

    public void update(float deltaTime)
    {
        // TODO
    }

    @Override
    public Point2D[] getPoints()
    {
        Point2D topRight = new Point2D(position.x + Settings.BlockSize, position.y + Settings.BlockSize);
        Point2D bottomRight = new Point2D(position.x + Settings.BlockSize, position.y - Settings.BlockSize);
        Point2D bottomLeft = new Point2D(position.x - Settings.BlockSize, position.y - Settings.BlockSize);
        Point2D topLeft = new Point2D(position.x - Settings.BlockSize, position.y - Settings.BlockSize);

        return new Point2D[] { bottomLeft, topLeft, topRight, bottomRight };
    }
}
