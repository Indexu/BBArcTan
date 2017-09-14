package com.ru.tgra.objects.particles;

import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.Settings;
import com.ru.tgra.objects.GameObject;
import com.ru.tgra.shapes.RectangleGraphic;
import com.ru.tgra.utilities.*;

public class SquareParticle extends GameObject
{
    private float rotationStrength;
    private float scaleDown;
    private boolean gravity;

    public SquareParticle(Point2D position, float lifespan, Color color, boolean gravity, boolean large)
    {
        super();

        // Color

        if (color == null)
        {
            float r = RandomGenerator.randomNumberInRange(0, 1);
            float g = RandomGenerator.randomNumberInRange(0, 1);
            float b = RandomGenerator.randomNumberInRange(0, 1);

            color = new Color(r, g, b, 1);
        }

        // Direction
        float dirX = RandomGenerator.randomNumberInRange(-1, 1);
        float dirY = RandomGenerator.randomNumberInRange(-1, 1);

        Vector2D direction = new Vector2D(dirX, dirY);

        // Speed

        this.speed = (large ? RandomGenerator.randomNumberInRange(50, 150) : RandomGenerator.randomNumberInRange(50, 100));

        // Scale
        float size = (large ? RandomGenerator.randomNumberInRange(5, 15) : RandomGenerator.randomNumberInRange(1, 5));
        Vector2D scale = new Vector2D(size, size);

        scaleDown = size / lifespan;

        // Rotation
        rotationStrength = RandomGenerator.randomNumberInRange(-25, 25);

        this.position = position;
        this.direction = direction;
        this.scale = scale;
        this.color = color;
        this.gravity = gravity;
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

        scale.x -= scaleDown * deltaTime;
        scale.y -= scaleDown * deltaTime;

        if (gravity)
        {
            position.y -= Settings.ParticleGravity * deltaTime;
        }
    }
}
