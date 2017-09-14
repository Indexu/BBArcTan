package com.ru.tgra.objects;

import com.ru.tgra.Settings;
import com.ru.tgra.shapes.CircleGraphic;
import com.ru.tgra.utilities.*;

public class Ball extends GameObject
{
    private float moveScalar;

    public Ball(Point2D position, Vector2D direction, float speed)
    {
        super();

        this.position = position;
        this.direction = direction;
        this.speed = speed;

        scale.x = Settings.BallSize;
        scale.y = Settings.BallSize;

        moveScalar = 0.0f;
    }

    @Override
    public void draw()
    {
        super.draw();

        CircleGraphic.drawSolid();

        /*

        for (Point2D point : getPoints())
        {
            ModelMatrix.main.loadIdentityMatrix();
            ModelMatrix.main.addTranslation(point);
            ModelMatrix.main.addScale(new Vector2D(4, 4));
            ModelMatrix.main.setShaderMatrix(GraphicsEnvironment.getModelMatrixLoc());

            GraphicsEnvironment.setColor(new Color(RandomGenerator.randomNumberInRange(0, 1), RandomGenerator.randomNumberInRange(0, 1), RandomGenerator.randomNumberInRange(0, 1), 1));

            CircleGraphic.drawSolid();
        }
        */
    }

    public void update(float deltaTime)
    {
        moveScalar = deltaTime * speed;
        Collisions.checkCollisions(this);
        move(moveScalar);
    }

    @Override
    public Point2D[] getPoints()
    {
        Point2D top = new Point2D(position.x, position.y + Settings.BallSize);
        Point2D bottom = new Point2D(position.x, position.y - Settings.BallSize);
        Point2D left = new Point2D(position.x - Settings.BallSize, position.y);
        Point2D right = new Point2D(position.x + Settings.BallSize, position.y);

        float topRightCos = (float) Math.cos(45 * Math.PI / 180.0);
        float topRightSin = (float) Math.sin(45 * Math.PI / 180.0);

        Point2D topRight = new Point2D((topRightCos * Settings.BallSize) + position.x, (topRightSin * Settings.BallSize) + position.y);

        float topLeftCos = (float) Math.cos(135 * Math.PI / 180.0);
        float topLeftSin = (float) Math.sin(135 * Math.PI / 180.0);

        Point2D topLeft = new Point2D((topLeftCos * Settings.BallSize) + position.x, (topLeftSin * Settings.BallSize) + position.y);

        float bottomLeftCos = (float) Math.cos(225 * Math.PI / 180.0);
        float bottomLeftSin = (float) Math.sin(225 * Math.PI / 180.0);

        Point2D bottomLeft = new Point2D((bottomLeftCos * Settings.BallSize) + position.x, (bottomLeftSin * Settings.BallSize) + position.y);

        float bottomRightCos = (float) Math.cos(315 * Math.PI / 180.0);
        float bottomRightSin = (float) Math.sin(315 * Math.PI / 180.0);

        Point2D bottomRight = new Point2D((bottomRightCos * Settings.BallSize) + position.x, (bottomRightSin * Settings.BallSize) + position.y);

        return new Point2D[] { top, topRight, right, bottomRight, bottom, bottomLeft, left, topLeft };
    }

    public void setMoveScalar(float scalar)
    {
        moveScalar = scalar;
    }

    public float getMoveScalar()
    {
        return moveScalar;
    }

    public void move(float scalar)
    {
        position.x += direction.x * scalar;
        position.y += direction.y * scalar;
    }
}
