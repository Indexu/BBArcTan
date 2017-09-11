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

        float r = RandomGenerator.randomNumberInRange(0, 1);
        float g = RandomGenerator.randomNumberInRange(0, 1);
        float b = RandomGenerator.randomNumberInRange(0, 1);

        Color color = new Color(r, g, b, 1);

        float dirX = RandomGenerator.randomNumberInRange(-1, 1);
        float dirY = RandomGenerator.randomNumberInRange(-1, 1);

        Vector2D direction = new Vector2D(dirX, dirY);

        float speed = RandomGenerator.randomNumberInRange(50, 150);
        float size = RandomGenerator.randomNumberInRange(10, 25);

        Vector2D scale = new Vector2D(size, size);

        this.position = position;
        this.direction = direction;
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

        color.setAlpha(color.getAlpha() - deltaTime);
    }
}
