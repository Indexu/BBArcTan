package com.ru.tgra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.ru.tgra.objects.*;
import com.ru.tgra.objects.particles.DestroyBallUp;
import com.ru.tgra.objects.particles.DestroyBlock;
import com.ru.tgra.objects.particles.HitBlock;
import com.ru.tgra.utilities.*;

import java.util.ArrayList;

public class BBArcTanGame extends ApplicationAdapter
{
	private float shotTimer;
	private int ballsShot;

	private Point2D mainMenuTitlePosition;
    private Point2D mainMenuClickPosition;
    private Point2D mainMenuSpacePosition;
    private Point2D mainMenuPausePosition;
    private Point2D mainMenuMusicPosition;
    private Point2D mainMenuStartPosition;

	@Override
	public void create ()
	{
		GraphicsEnvironment.setupGraphicsEnvironment();
        ModelMatrix.main = new ModelMatrix();
        AudioManager.initSoundManager();

		GraphicsEnvironment.setClearColor(Settings.BackgroundColor);

		GameManager.mainMenu = true;

        initGame();
        initMainMenu();
        AudioManager.playMusic();
	}

	private void update()
	{
        Gdx.graphics.setTitle("BB Arc Tan | FPS: " + Gdx.graphics.getFramesPerSecond());

	    float deltaTime = Gdx.graphics.getDeltaTime();

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        // Music
        if (Gdx.input.isKeyJustPressed(Input.Keys.M))
        {
            AudioManager.pauseResumeMusic();
        }

        // Main Menu
        if (GameManager.mainMenu)
        {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            {
                GameManager.mainMenu = false;
            }

            return;
        }

        // Restartable
        if (GameManager.restartable)
        {
            // Space bar
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            {
                initGame();
            }
        }

        // Paused
        if (GameManager.paused)
        {
            paused();
            return;
        }

        // Game Over
        if (GameManager.gameOver)
        {
            GameManager.gameOver(deltaTime);
            return;
        }

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
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
        {
            GameManager.endRound();
            ballsShot = 0;
        }

        // Escape
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            GameManager.pause();
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
            gameObject.update(deltaTime);
        }

        // Create particles
        for (Point2D point : GameManager.getDestroyBlockParticlesCoords())
        {
            GameManager.gameObjects.add(new DestroyBlock(point));
        }

        for (Point2D point : GameManager.getDestroyBallUpParticlesCoords())
        {
            GameManager.gameObjects.add(new DestroyBallUp(new Point2D(point)));
        }

        ArrayList<Point2D> hitParticleCoords = GameManager.getHitBlockParticlesCoords();
        ArrayList<Color> hitParticleColors = GameManager.getHitBlockParticlesColors();

        for (int i = 0; i < hitParticleCoords.size(); i++)
        {
            Point2D pos = hitParticleCoords.get(i);
            Color color = hitParticleColors.get(i);

            GameManager.gameObjects.add(new HitBlock(pos, color));
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

		// Main menu
		if (GameManager.mainMenu)
        {
            drawMainMenu();
            return;
        }

        // Screen shake
        if (GameManager.shaking)
        {
            GameManager.screenShake(Gdx.graphics.getDeltaTime());
        }

		// Draw game objects
        for(GameObject gameObject : GameManager.gameObjects)
        {
            gameObject.draw();
        }

        // Draw aimer and layout separately last
        // so that they are on top
        GameManager.aimer.draw();
        GameManager.layout.draw();

        if (GameManager.gameOver)
        {
            GraphicsEnvironment.drawText(GameManager.gameOverPausePosition, Settings.GameOverText, Settings.GameOverColor, 3);
            GraphicsEnvironment.drawText(mainMenuPausePosition, "Press SPACE to restart", Settings.GameOverColor, 2);
        }
        else if (GameManager.paused)
        {
            GraphicsEnvironment.drawText(GameManager.gameOverPausePosition, Settings.PauseText, Settings.PauseColor, 3);
        }
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

        if (Settings.TimeBetweenShots <= shotTimer)
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

    private void paused()
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            GameManager.pause();
        }
    }

    private void initGame()
    {
        GameManager.initGameManager();

        shotTimer = 0.0f;
        ballsShot = 0;

        for (int i = 0; i < Settings.InitialRows; i++)
        {
            GameManager.nextRound();
        }
    }

    private void initMainMenu()
    {
        float offsetY = Gdx.graphics.getHeight() / 6;

        mainMenuTitlePosition = new Point2D(GameManager.gameOverPausePosition);
        mainMenuTitlePosition.y += offsetY;

        mainMenuClickPosition = new Point2D(mainMenuTitlePosition);
        mainMenuClickPosition.y -= offsetY;

        mainMenuSpacePosition = new Point2D(mainMenuClickPosition);
        mainMenuSpacePosition.y -= offsetY / 2;

        mainMenuPausePosition = new Point2D(mainMenuSpacePosition);
        mainMenuPausePosition.y -= offsetY / 2;

        mainMenuMusicPosition = new Point2D(mainMenuPausePosition);
        mainMenuMusicPosition.y -= offsetY / 2;

        mainMenuStartPosition = new Point2D(mainMenuSpacePosition);
        mainMenuStartPosition.y -= offsetY * 1.5;
    }

    private void drawMainMenu()
    {
        GraphicsEnvironment.drawText(mainMenuTitlePosition, "BB Arc Tan", Settings.PauseColor, 3);
        GraphicsEnvironment.drawText(mainMenuClickPosition, "Click to shoot", Settings.PauseColor, 1);
        GraphicsEnvironment.drawText(mainMenuSpacePosition, "Space to end round immediately", Settings.PauseColor, 1);
        GraphicsEnvironment.drawText(mainMenuPausePosition, "Press ESC to pause", Settings.PauseColor, 1);
        GraphicsEnvironment.drawText(mainMenuMusicPosition, "Press M to toggle music", Settings.PauseColor, 1);
        GraphicsEnvironment.drawText(mainMenuStartPosition, "Press SPACE to start", Settings.PauseColor, 2);
    }
}
