package com.ru.tgra.objects;

import com.ru.tgra.Settings;
import com.ru.tgra.shapes.CircleGraphic;
import com.ru.tgra.utilities.*;

import java.util.ArrayList;

public class Ball extends GameObject
{
    public Ball(Point2D position, Vector2D direction, float speed)
    {
        super();

        this.position = position;
        this.direction = direction;
        this.speed = speed;

        scale.x = Settings.BallSize;
        scale.y = Settings.BallSize;
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

    public void checkCollisions(float deltaTime, ArrayList<GameObject> gameObjects)
    {
        float min_tHit = Float.MAX_VALUE;
        Point2D pHit;
        Vector2D n = null;

        for (Point2D A : getPoints())
        {
            // Bounds
            for (int i = 0; i < 4; i++)
            {
                int j = (i + 1) % 4;

                Point2D p1 = Layout.points[i];
                Point2D p2 = Layout.points[j];

                float tHit = Collisions.calculateTHit(A, p1, p2, direction);

                pHit = Collisions.calculatePHit(A, direction, tHit);

                boolean onLine = Collisions.isPointOnLine(pHit, p1, p2);

                if (onLine && 0 <= tHit && tHit < min_tHit)
                {
                    min_tHit = tHit;
                    Vector2D v = p1.vectorBetweenPoints(p2);
                    n = v.getPerp();
                }
            }

            /*
            // Blocks
            for (GameObject gameObject : gameObjects)
            {
                if (gameObject instanceof Block)
                {
                    Block block = (Block) gameObject;
                    Point2D[] points = block.getPoints();

                    for (int i = 0; i < 4; i++)
                    {
                        int j = (i + 1) % 4;

                        Point2D p1 = points[i];
                        Point2D p2 = points[j];

                        float tHit = Collisions.calculateTHit(A, p1, p2, direction);

                        pHit = Collisions.calculatePHit(A, direction, tHit);

                        boolean onLine = Collisions.isPointOnLine(pHit, p1, p2);

                        if (onLine && 0 < tHit && tHit < min_tHit)
                        {
                            min_tHit = tHit;
                            Vector2D v = p1.vectorBetweenPoints(p2);
                            n = v.getPerp();
                        }
                    }
                }
            }
            */
        }

        if (min_tHit <= deltaTime * speed)
        {
            this.direction = Collisions.calculateReflectionVector(direction, n);
            this.direction.normalize();
        }
    }
}
