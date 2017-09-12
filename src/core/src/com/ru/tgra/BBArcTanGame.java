package com.ru.tgra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.ru.tgra.objects.*;
import com.ru.tgra.objects.particles.DestroyBlock;
import com.ru.tgra.shapes.CircleGraphic;
import com.ru.tgra.shapes.RectangleGraphic;
import com.ru.tgra.utilities.*;

import java.util.ArrayList;

public class BBArcTanGame extends ApplicationAdapter
{
	private GameObject block;
	private GameObject aimer;

	@Override
	public void create ()
	{
		GraphicsEnvironment.setupGraphicsEnvironment();
        ModelMatrix.main = new ModelMatrix();
        GameManager.initGameManager();

        Color aimerColor = new Color(0.5f, 0.5f, 0.5f, 1);
        Point2D aimerPos = new Point2D(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 8);
        aimer = new Aimer(aimerPos, aimerColor);

        Color clearColor = new Color(0f, 0f, 0f, 1.0f);
		GraphicsEnvironment.setClearColor(clearColor);

        GameObject layout = new Layout(new Point2D(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 24));

        GameManager.gameObjects.add(aimer);
        GameManager.gameObjects.add(layout);
	}

	private void update()
	{
	    float deltaTime = Gdx.graphics.getDeltaTime();

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        setAimerRotation(mouseX, mouseY);

		if(Gdx.input.justTouched())
		{
            // GameManager.nextRound();

			//do mouse/touch input stuff

            // GameManager.gameObjects.add(new DestroyBlock(new Point2D(mouseX, mouseY)));

            shoot(mouseX, mouseY);
		}

		//do all updates to the game
        for(GameObject gameObject : GameManager.gameObjects)
        {
            if (gameObject instanceof Ball)
            {
                Ball ball = (Ball) gameObject;

                ball.setMoveScalar(deltaTime);
            }

            gameObject.update(deltaTime);
        }

        // Create particles
        for (Point2D point : GameManager.getDestroyBlockParticlesCoors())
        {
            GameManager.gameObjects.add(new DestroyBlock(new Point2D(point.x, point.y)));
        }

        // Empty the particle lists
        GameManager.emptyAllCoordsLists();

        // Remove destroyed game objects
        GameManager.removeDestroyed();
	}

	private void display()
	{
		GraphicsEnvironment.clear();

        for(GameObject gameObject : GameManager.gameObjects)
        {
            gameObject.draw();
        }
	}

	@Override
	public void render ()
	{
		update();
		display();
	}

	private void setAimerRotation(float mouseX, float mouseY)
    {
        float x = aimer.getPosition().x;
        float y = aimer.getPosition().y;

        float rotation = (float) ( 270 - Math.atan2((double) (y - mouseY), (double)(x - mouseX)) * (180 / Math.PI));

        aimer.setRotation(-rotation);
    }

    private void shoot(float mouseX, float mouseY)
    {
        Vector2D direction = aimer.getPosition().vectorBetweenPoints(new Point2D(mouseX, mouseY));
        direction.normalize();

        GameObject ball = new Ball(new Point2D(aimer.getPosition()), direction, Settings.BallSpeed);

        GameManager.gameObjects.add(ball);
    }
}
