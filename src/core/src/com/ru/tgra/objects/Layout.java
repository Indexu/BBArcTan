package com.ru.tgra.objects;

import com.badlogic.gdx.Gdx;
import com.ru.tgra.GameManager;
import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.Settings;
import com.ru.tgra.shapes.RectangleGraphic;
import com.ru.tgra.utilities.Point2D;

public class Layout extends GameObject
{
    private Point2D[] points;

    public Layout(Point2D position)
    {
        super();

        this.position = position;

        scale.x = position.x * 2;
        scale.y = position.y * 2;

        Point2D topRight = new Point2D(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Point2D bottomRight = new Point2D(Gdx.graphics.getWidth(), position.y * 2);
        Point2D bottomLeft = new Point2D(0, position.y * 2);
        Point2D topLeft = new Point2D(0, Gdx.graphics.getHeight());

        points = new Point2D[] { bottomLeft, topLeft, topRight, bottomRight };
    }

    @Override
    public void draw()
    {
        super.draw();

        // Backdrop
        RectangleGraphic.drawSolid();

        // Round text
        GraphicsEnvironment.drawText(position, "Round: " + GameManager.round, Settings.BackgroundColor, 1);

        // Balls text
        float offsetX = position.x - (Gdx.graphics.getWidth() / 3);
        Point2D pos = new Point2D(offsetX, position.y);
        GraphicsEnvironment.drawText(pos, "Balls: " + GameManager.shots, Settings.BackgroundColor, 1);

        // Score text
        offsetX = position.x + (Gdx.graphics.getWidth() / 3);
        pos.x = offsetX;
        GraphicsEnvironment.drawText(pos, "Score: " + GameManager.score, Settings.BackgroundColor, 1);
    }

    public void update(float deltaTime)
    {
        // TODO
    }

    @Override
    public Point2D[] getPoints()
    {
        return points;
    }
}
