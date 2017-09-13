package com.ru.tgra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.ru.tgra.objects.*;
import com.ru.tgra.objects.particles.DestroyBlock;
import com.ru.tgra.utilities.*;

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

		GraphicsEnvironment.setClearColor(Settings.BackgroundColor);

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

        // Click
		if(Gdx.input.justTouched())
		{
            if (GameManager.aimingInProgress && !GameManager.gameOver)
            {
                shoot();
            }
		}

		// Space bar
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            GameManager.endRound();
        }

		if (GameManager.shootingInProgress)
        {
            shootingInProgress(deltaTime);
        }

        // Update aimer
        GameManager.aimer.update(deltaTime);

	    // Update all other game objects
        for(GameObject gameObject : GameManager.gameObjects)
        {
            // Check collisions for balls
            if (gameObject instanceof Ball)
            {
                Ball ball = (Ball) gameObject;

                ball.setMoveScalar(deltaTime * Settings.BallSpeed);
                Collisions.checkCollisions(ball);
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

        // Check if next round should be initiated
        GameManager.checkIfNextRound();
	}

	private void display()
	{
	    // Clear
		GraphicsEnvironment.clear();

		// Draw game objects
        for(GameObject gameObject : GameManager.gameObjects)
        {
            gameObject.draw();
        }

        // Draw aimer and layout separately last
        // so that they are on top
        GameManager.aimer.draw();
        GameManager.layout.draw();
	}

	@Override
	public void render ()
	{
		update();
		display();
	}

    private void shoot()
    {
        GameManager.shootOriginPoint.setPoint(GameManager.aimer.getPosition());

        GameManager.aimingInProgress = false;
        GameManager.shootingInProgress = true;
    }

    private void shootingInProgress(float deltaTime)
    {
        shotTimer += deltaTime;

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
