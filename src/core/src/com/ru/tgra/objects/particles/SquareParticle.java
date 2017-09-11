package com.ru.tgra.objects.particles;

import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.objects.GameObject;
import com.ru.tgra.shapes.RectangleGraphic;
import com.ru.tgra.utilities.*;

public class SquareParticle extends GameObject
{
    private float rotationStrength;

    public SquareParticle(Point2D position)
    {
        super();

        // Color
        float r = RandomGenerator.randomNumberInRange(0, 1);
        float g = RandomGenerator.randomNumberInRange(0, 1);
        float b = RandomGenerator.randomNumberInRange(0, 1);

        Color color = new Color(r, g, b, 1);

        // Direction
        float dirX = RandomGenerator.randomNumberInRange(-1, 1);
        float dirY = RandomGenerator.randomNumberInRange(-1, 1);

        Vector2D direction = new Vector2D(dirX, dirY);

        // Speed
        this.speed = RandomGenerator.randomNumberInRange(50, 150);

        // Scale
        float size = RandomGenerator.randomNumberInRange(5, 15);
        Vector2D scale = new Vector2D(size, size);

        // Rotation
        rotationStrength = RandomGenerator.randomNumberInRange(-25, 25);

        this.position = position;
        this.direction = direction;
        this.scale = scale;
        this.color = color;
    }

    @Override
    public void draw()
    {
        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position);
        ModelMatrix.main.addRotationX(rotation);
        ModelMatrix.main.addRotationY(rotation);
        ModelMatrix.main.addRotationZ(rotation);
        ModelMatrix.main.addScale(scale);
        ModelMatrix.main.setShaderMatrix(GraphicsEnvironment.getModelMatrixLoc());

        GraphicsEnvironment.setColor(color);

        RectangleGraphic.drawSolid();
    }

    public void update(float deltaTime)
    {
        position.x += direction.x * speed * deltaTime;
        position.y += direction.y * speed * deltaTime;

        rotation += rotationStrength;
    }
}
