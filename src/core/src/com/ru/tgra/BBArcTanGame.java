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
	private float shotTimer;
	private Vector2D shootDirection;
	private int ballsShot;

	@Override
	public void create ()
	{
		GraphicsEnvironment.setupGraphicsEnvironment();
        ModelMatrix.main = new ModelMatrix();
        GameManager.initGameManager();

        Color aimerColor = new Color(0.5f, 0.5f, 0.5f, 1);
        Point2D aimerPos = new Point2D(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 8);
        GameManager.aimer = new Aimer(aimerPos, aimerColor);

        Color clearColor = new Color(0f, 0f, 0f, 1.0f);
		GraphicsEnvironment.setClearColor(clearColor);

        GameObject layout = new Layout(new Point2D(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 24));

        GameManager.gameObjects.add(GameManager.aimer);
        GameManager.gameObjects.add(layout);

        shotTimer = 0.0f;
        shootDirection = new Vector2D();
        ballsShot = 0;

        for (int i = 0; i < Settings.initialRows; i++)
        {
            GameManager.nextRound();
        }
	}

	private void update()
	{
	    float deltaTime = Gdx.graphics.getDeltaTime();

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

	    if (!GameManager.gameOver)
        {
            GameManager.setAimerRotation(mouseX, mouseY);
        }

        // Input
		if(Gdx.input.justTouched())
		{
            // GameManager.gameObjects.add(new DestroyBlock(new Point2D(mouseX, mouseY)));

            if (GameManager.aimingInProgress && !GameManager.gameOver)
            {
                shoot(mouseX, mouseY);
            }
		}

		if (GameManager.shootingInProgress)
        {
            shootingInProgress(deltaTime);
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

        GameManager.checkIfNextRound();
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



    private void shoot(float mouseX, float mouseY)
    {
        shootDirection = GameManager.aimer.getPosition().vectorBetweenPoints(new Point2D(mouseX, mouseY));
        shootDirection.normalize();

        GameManager.aimingInProgress = false;
        GameManager.shootingInProgress = true;
    }

    private void shootingInProgress(float deltatime)
    {
        shotTimer += deltatime;

        if (Settings.timeBetweenShots <= shotTimer)
        {
            GameManager.spawnBall(shootDirection);
            shotTimer = 0.0f;
            ballsShot++;

            if (ballsShot == GameManager.shots)
            {
                GameManager.shootingInProgress = false;
                ballsShot = 0;
            }
        }
    }
}
