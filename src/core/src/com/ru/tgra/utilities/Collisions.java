package com.ru.tgra.utilities;

import com.ru.tgra.AudioManager;
import com.ru.tgra.GameManager;
import com.ru.tgra.Settings;
import com.ru.tgra.objects.*;
import com.ru.tgra.objects.powerups.BallUp;

public class Collisions
{
    public static void checkCollisions(Ball ball)
    {
        float moveScalar = ball.getMoveScalar();
        Vector2D direction = ball.getDirection();

        // Do nothing if some how moving backwards
        // A base case for the recursion
        if (moveScalar <= 0)
        {
            return;
        }

        float min_tHit = Float.MAX_VALUE;
        Point2D pHit = null;
        Vector2D n = null;
        GridObject hitObject = null;
        boolean bottomHit = false;
        Point2D[] points;

        // For every point on the ball
        for (Point2D A : ball.getPoints())
        {
            points = GameManager.layout.getPoints();

            // Check bounds
            for (int i = 0; i < 4; i++)
            {
                int j = (i + 1) % 4;

                Point2D p1 = points[i];
                Point2D p2 = points[j];

                float tHit = Collisions.calculateTHit(A, p1, p2, direction);

                if (0 < tHit && tHit < min_tHit)
                {
                    pHit = Collisions.calculatePHit(A, direction, tHit);

                    boolean onLine = pHit.isBetween(p1, p2);

                    if (onLine)
                    {
                        min_tHit = tHit;
                        Vector2D v = p1.vectorBetweenPoints(p2);
                        n = v.getPerp();
                        hitObject = null;
                        bottomHit = (i == 3);
                    }
                }
            }

            // Check grid objects
            for (GridObject gridObject : GameManager.gridObjects)
            {
                points = gridObject.getPoints();

                for (int i = 0; i < 4; i++)
                {
                    int j = (i + 1) % 4;

                    Point2D p1 = points[i];
                    Point2D p2 = points[j];

                    float tHit = Collisions.calculateTHit(A, p1, p2, direction);

                    if (0 < tHit && tHit < min_tHit)
                    {
                        pHit = Collisions.calculatePHit(A, direction, tHit);

                        boolean onLine = pHit.isBetween(p1, p2);

                        if (onLine)
                        {
                            min_tHit = tHit;
                            Vector2D v = p1.vectorBetweenPoints(p2);
                            n = v.getPerp();
                            hitObject = gridObject;
                            bottomHit = false;
                        }
                    }
                }
            }

        }

        // If the collision happens in this frame
        if (min_tHit <= moveScalar)
        {
            // Check if a grid object was hit
            if (hitObject != null)
            {
                hitObject.hit();

                // Display particles at p_hit if block
                if (hitObject instanceof Block)
                {
                    GameManager.addHitBlockParticles(pHit, hitObject.getColor());
                }
                // Do nothing if ball up
                else if (hitObject instanceof BallUp)
                {
                    return;
                }
            }
            // Bounds hit
            else if (!bottomHit)
            {
                AudioManager.playWallHit();
                GameManager.addHitBlockParticles(pHit, Settings.AimerColor);
            }

            // Check if the bottom was hit
            if (bottomHit)
            {
                GameManager.ballDestroyed(ball.getPosition());
                ball.destroy();
                return;
            }

            // Move towards the collision (p hit)
            // Division is necessary in order to actually
            // collide with the line, just close enough to
            // not spasm out of control.
            ball.move(min_tHit * Settings.T_HitEpsilon);

            // Reflection vector
            direction = Collisions.calculateReflectionVector(direction, n);
            direction.normalize();
            ball.setDirection(direction);

            // What is left of the frame
            moveScalar -= min_tHit;
            ball.setMoveScalar(moveScalar);

            // Re-check collisions, now with
            // reduced frame time and reflection vector
            checkCollisions(ball);
        }
    }

    public static Vector2D calculateReflectionVector(Vector2D c, Vector2D n)
    {
        Vector2D newN = new Vector2D(n);

        newN.scale(2 * (c.dot(newN) / newN.dotSelf()));

        return new Vector2D(c.x - newN.x, c.y - newN.y);
    }

    public static float calculateTHit(Point2D A, Point2D B, Point2D otherPoint, Vector2D c)
    {
        Vector2D v = B.vectorBetweenPoints(otherPoint);
        Vector2D n = v.getPerp();

        return (n.dot(Vector2D.difference(B, A))) / n.dot(c);
    }

    public static Point2D calculatePHit(Point2D A, Vector2D c, float tHit)
    {
        Vector2D newC = new Vector2D(c);

        newC.scale(tHit);

        return Point2D.additionVector(A, newC);
    }
}
