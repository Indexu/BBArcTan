package com.ru.tgra.objects;

import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.Settings;
import com.ru.tgra.shapes.CircleGraphic;
import com.ru.tgra.shapes.RectangleGraphic;
import com.ru.tgra.utilities.*;

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

        for (Point2D point : getPoints())
        {
            ModelMatrix.main.loadIdentityMatrix();
            ModelMatrix.main.addTranslation(point);
            ModelMatrix.main.addScale(new Vector2D(3, 3));
            ModelMatrix.main.setShaderMatrix(GraphicsEnvironment.getModelMatrixLoc());

            GraphicsEnvironment.setColor(new Color(RandomGenerator.randomNumberInRange(0, 1), RandomGenerator.randomNumberInRange(0, 1), RandomGenerator.randomNumberInRange(0, 1), 1));

            CircleGraphic.drawSolid();
        }

    }

    public void update(float deltaTime)
    {
        // TODO
    }

    @Override
    public Point2D[] getPoints()
    {
        float halfSize = Settings.BlockSize / 2;

        Point2D topRight = new Point2D(position.x + halfSize, position.y + halfSize);
        Point2D bottomRight = new Point2D(position.x + halfSize, position.y - halfSize);
        Point2D bottomLeft = new Point2D(position.x - halfSize, position.y - halfSize);
        Point2D topLeft = new Point2D(position.x - halfSize, position.y + halfSize);

        return new Point2D[] { bottomLeft, topLeft, topRight, bottomRight };
    }
}
