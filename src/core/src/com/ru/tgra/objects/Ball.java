package com.ru.tgra.objects;

import com.ru.tgra.shapes.CircleGraphic;
import com.ru.tgra.utilities.Point2D;
import com.ru.tgra.utilities.Vector2D;

public class Ball extends GameObject
{
    public Ball(Point2D position, Vector2D direction, float speed)
    {
        super();

        this.position = position;
        this.direction = direction;
        this.speed = speed;

        scale.x = 10;
        scale.y = 10;
    }

    @Override
    public void draw()
    {
        super.draw();

        CircleGraphic.drawSolid();
    }

    public void update(float deltaTime)
    {
        position.x += direction.x * speed * deltaTime;
        position.y += direction.y * speed * deltaTime;
    }
}
