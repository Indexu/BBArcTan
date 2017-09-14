package com.ru.tgra.objects;

import com.ru.tgra.AudioManager;
import com.ru.tgra.GameManager;
import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.Settings;
import com.ru.tgra.shapes.CircleGraphic;
import com.ru.tgra.shapes.RectangleGraphic;
import com.ru.tgra.utilities.*;

public class Block extends GridObject
{
    private int health;
    private Vector2D smallScale;

    public Block(Point2D position, int health, int row, int col)
    {
        super();

        this.position = position;
        this.health = health;

        scale.x = Settings.BlockSize;
        scale.y = Settings.BlockSize;

        smallScale = new Vector2D(Settings.BlockSize / 1.2f, Settings.BlockSize / 1.2f);

        this.row = row;
        this.col = col;
    }

    @Override
    public void draw()
    {
        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position);
        ModelMatrix.main.addRotationZ(rotation);
        ModelMatrix.main.addScale(scale);
        ModelMatrix.main.setShaderMatrix(GraphicsEnvironment.getModelMatrixLoc());

        color = GameManager.getBlockColor(health);
        GraphicsEnvironment.setColor(color);

        RectangleGraphic.drawSolid();

        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position);
        ModelMatrix.main.addRotationZ(rotation);
        ModelMatrix.main.addScale(smallScale);
        ModelMatrix.main.setShaderMatrix(GraphicsEnvironment.getModelMatrixLoc());

        GraphicsEnvironment.setColor(Settings.BackgroundColor);

        RectangleGraphic.drawSolid();

        GraphicsEnvironment.drawText(position, Integer.toString(health), color, 1);

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
        health--;
        GameManager.increaseScore(Settings.ScoreHitBlock);
        AudioManager.playBlockHit();

        if (health == 0)
        {
            GameManager.addDestroyBlockParticles(position);
            GameManager.increaseScore(Settings.ScoreDestroyBlock);
            GameManager.shaking = true;
            AudioManager.playBlockDestroy();
            this.destroy();
        }
    }
}
