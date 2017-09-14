package com.ru.tgra.objects.powerups;

import com.ru.tgra.AudioManager;
import com.ru.tgra.GameManager;
import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.Settings;
import com.ru.tgra.objects.GridObject;
import com.ru.tgra.shapes.RectangleGraphic;
import com.ru.tgra.utilities.Point2D;

public class BallUp extends GridObject
{
    public BallUp(Point2D position, int row, int col)
    {
        super();

        this.position = position;

        scale.x = Settings.PowerUpSize;
        scale.y = Settings.PowerUpSize;

        color = Settings.BallUpColor;

        this.row = row;
        this.col = col;
    }

    @Override
    public void draw()
    {
        super.draw();

        RectangleGraphic.drawOutline();

        GraphicsEnvironment.drawText(position, "+", color, 1);

        /*
        for (Point2D point : getPoints())
        {
            ModelMatrix.main.loadIdentityMatrix();
            ModelMatrix.main.addTranslation(point);
            ModelMatrix.main.addScale(new Vector2D(3, 3));
            ModelMatrix.main.setShaderMatrix(GraphicsEnvironment.getModelMatrixLoc());

            GraphicsEnvironment.setColor(new Color(RandomGenerator.randomNumberInRange(0, 1), RandomGenerator.randomNumberInRange(0, 1), RandomGenerator.randomNumberInRange(0, 1), 1));

            CircleGraphic.drawSolid();
        }
        */
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

    public void hit()
    {
        if (!destroyed)
        {
            GameManager.increaseShots();
            GameManager.addDestroyBallUpParticles(position);
            AudioManager.playBallUp();
            this.destroy();
        }
    }
}
