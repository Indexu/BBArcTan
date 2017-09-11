package com.ru.tgra.objects;

import com.ru.tgra.Settings;
import com.ru.tgra.shapes.CircleGraphic;
import com.ru.tgra.utilities.*;

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

        checkCollisions(deltaTime);
    }

    @Override
    public Point2D[] getPoints()
    {
        float halfSize = Settings.BallSize / 2;

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

    private void checkCollisions(float deltaTime)
    {
        // Bounds
        float min_tHit = Float.MAX_VALUE;
        Point2D p_hit = null;
        Vector2D temp_c = null;
        Vector2D main_c = null;
        Vector2D main_n = null;

        for (Point2D A : getPoints())
        {
            for (int i = 0; i < 4; i++)
            {
                int j = (i + 1) % 4;

                Point2D p1 = Layout.points[i];
                Point2D p2 = Layout.points[j];

                Vector2D c = new Vector2D(this.direction);
                Point2D B = p1;
                Vector2D v = B.vectorBetweenPoints(p2);
                Vector2D n = v.getPerp();

                float t_hit = (n.dot(Vector2D.difference(B, A))) / n.dot(c);

                temp_c = new Vector2D(c);
                c.scale(t_hit);
                p_hit = Point2D.additionVector(A, c);

                boolean onLine;

                if (v.y == 0)
                {
                    onLine = (B.y < p2.y && (B.y <= p_hit.y && p_hit.y <= p2.y)) || (p2.y <= p_hit.y && p_hit.y <= B.y);
                }
                else
                {
                    onLine = (B.x < p2.x && (B.x <= p_hit.x && p_hit.x <= p2.x)) || (p2.x <= p_hit.x && p_hit.x <= B.x);
                }

                if (onLine && 0 < t_hit && t_hit < min_tHit)
                {
                    min_tHit = t_hit;
                    main_n = n;
                    main_c = temp_c;
                }
            }
        }

        if (min_tHit <= deltaTime * speed)
        {
            main_n.scale(2 * (main_c.dot(main_n) / main_n.dotSelf()));

            this.direction = new Vector2D(main_c.x - main_n.x, main_c.y - main_n.y);
        }
    }
}
