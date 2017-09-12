package com.ru.tgra.objects;

import com.ru.tgra.GameManager;
import com.ru.tgra.GraphicsEnvironment;
import com.ru.tgra.Settings;
import com.ru.tgra.shapes.CircleGraphic;
import com.ru.tgra.shapes.RectangleGraphic;
import com.ru.tgra.utilities.Color;
import com.ru.tgra.utilities.ModelMatrix;
import com.ru.tgra.utilities.Point2D;
import com.ru.tgra.utilities.Vector2D;

public class Aimer extends GameObject
{
    Vector2D baseScale;

    public Aimer(Point2D position, Color color)
    {
        super();

        this.position = position;
        this.color = color;

        scale.x = 5;
        scale.y = 850;

        baseScale = new Vector2D(15, 15);
    }

    @Override
    public void draw()
    {
        super.draw();

        if (GameManager.aimingInProgress && !GameManager.gameOver)
        {
            RectangleGraphic.setVertexBuffer(false);
            RectangleGraphic.drawSolid();
            RectangleGraphic.setVertexBuffer(true);
        }

        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position);
        ModelMatrix.main.addScale(baseScale);
        ModelMatrix.main.setShaderMatrix(GraphicsEnvironment.getModelMatrixLoc());

        GraphicsEnvironment.setColor(Settings.AimerBaseColor);

        CircleGraphic.drawSolid();
    }

    public void update(float deltaTime)
    {
        // TODO
    }
}
