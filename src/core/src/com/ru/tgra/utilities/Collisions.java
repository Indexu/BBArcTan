package com.ru.tgra.utilities;

public class Collisions
{
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

    public static boolean isPointOnLine(Point2D point, Point2D p1, Point2D p2)
    {
        Vector2D v = p1.vectorBetweenPoints(p2);

        return (v.y == 0
            ? (p1.y < p2.y && (p1.y <= point.y && point.y <= p2.y)) || (p2.y <= point.y && point.y <= p1.y)
            : (p1.x < p2.x && (p1.x <= point.x && point.x <= p2.x)) || (p2.x <= point.x && point.x <= p1.x));
    }
}
