package com.ru.tgra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
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
	private int ballsShot;

	@Override
	public void create ()
	{
		GraphicsEnvironment.setupGraphicsEnvironment();
        ModelMatrix.main = new ModelMatrix();
        GameManager.initGameManager();
        AudioManager.initSoundManager();

        Color aimerColor = new Color(0.5f, 0.5f, 0.5f, 1);
        Point2D aimerPos = new Point2D(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 8);
        GameManager.aimer = new Aimer(aimerPos, aimerColor);

		GraphicsEnvironment.setClearColor(Settings.BackgroundColor);

        GameManager.layout = new Layout(new Point2D(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 24));

        shotTimer = 0.0f;
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

		if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            GameManager.endRound();
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

        GameManager.aimer.update(deltaTime);

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

        GameManager.aimer.draw();
        GameManager.layout.draw();
	}

	@Override
	public void render ()
	{
		update();
		display();
	}



    private void shoot(float mouseX, float mouseY)
    {
        GameManager.aimingInProgress = false;
        GameManager.shootingInProgress = true;
    }

    private void shootingInProgress(float deltatime)
    {
        shotTimer += deltatime;

        if (Settings.timeBetweenShots <= shotTimer)
        {
            GameManager.spawnBall();
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
