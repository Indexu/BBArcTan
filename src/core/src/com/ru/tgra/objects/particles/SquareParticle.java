package com.ru.tgra.objects.particles;

import com.ru.tgra.objects.GameObject;
import com.ru.tgra.shapes.RectangleGraphic;
import com.ru.tgra.utilities.Color;
import com.ru.tgra.utilities.Point2D;
import com.ru.tgra.utilities.RandomGenerator;
import com.ru.tgra.utilities.Vector2D;

public class SquareParticle extends GameObject
{
    public SquareParticle(Point2D position)
    {
        super();

        float r = RandomGenerator.RandomNumberInRange(0, 1);
        float g = RandomGenerator.RandomNumberInRange(0, 1);
        float b = RandomGenerator.RandomNumberInRange(0, 1);

        Color color = new Color(r, g, b, 1);

        float dirX = RandomGenerator.RandomNumberInRange(0, 1);
        float dirY = RandomGenerator.RandomNumberInRange(0, 1);

        Vector2D direction = new Vector2D(dirX, dirY);

        float speed = RandomGenerator.RandomNumberInRange(50, 150);
        float size = RandomGenerator.RandomNumberInRange(10, 25);

        Vector2D scale = new Vector2D(size, size);

        this.position = position;
        this.direction = direction;
        this.direction.normalize();
        this.speed = speed;
        this.scale = scale;
        this.color = color;
    }

    @Override
    public void draw()
    {
        super.draw();

        RectangleGraphic.drawSolid();
    }

    public void update(float deltaTime)
    {
        position.x += direction.x * speed * deltaTime;
        position.y += direction.y * speed * deltaTime;
    }
}
