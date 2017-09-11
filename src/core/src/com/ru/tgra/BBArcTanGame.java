package com.ru.tgra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.ru.tgra.objects.*;
import com.ru.tgra.objects.particles.DestroyBlock;
import com.ru.tgra.shapes.CircleGraphic;
import com.ru.tgra.shapes.RectangleGraphic;
import com.ru.tgra.utilities.Color;
import com.ru.tgra.utilities.Point2D;
import com.ru.tgra.utilities.Vector2D;

import java.util.ArrayList;

public class BBArcTanGame extends ApplicationAdapter
{
	private GameObject block;
	private ArrayList<GameObject> gameObjects;
	private GameObject aimer;

	@Override
	public void create ()
	{
		GraphicsEnvironment.setupGraphicsEnvironment();
		GameState.initGameState();
        gameObjects = new ArrayList<>();

        Color aimerColor = new Color(0.5f, 0.5f, 0.5f, 1);
        Point2D aimerPos = new Point2D(Gdx.graphics.getWidth(), 50);
        aimer = new Aimer(aimerPos, aimerColor);

        Color clearColor = new Color(0f, 0f, 0f, 1.0f);
		GraphicsEnvironment.setClearColor(clearColor);

		Color testColor = new Color(0.5f, 0f, 0, 1);

        block = new Block(new Point2D(250, 500), 5);
        block.setScale(new Vector2D(70, 70));
        block.setColor(testColor);

        gameObjects.add(block);

        GameObject layout = new Layout(new Point2D(Gdx.graphics.getWidth() / 2, 25));

        gameObjects.add(aimer);
        gameObjects.add(layout);
	}

	private void update()
	{
	    float deltaTime = Gdx.graphics.getDeltaTime();

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        setAimerRotation(mouseX, mouseY);

		if(Gdx.input.justTouched())
		{
			//do mouse/touch input stuff

            gameObjects.add(new DestroyBlock(new Point2D(mouseX, mouseY)));

            Vector2D direction = aimer.getPosition().vectorBetweenPoints(new Point2D(mouseX, mouseY));
            direction.normalize();

            GameObject ball = new Ball(new Point2D(aimer.getPosition()), direction, 600);

            gameObjects.add(ball);
		}

		//do all updates to the game
        for(GameObject gameObject : gameObjects)
        {
            gameObject.update(deltaTime);
        }

        // Remove destroyed game objects
        gameObjects.removeIf(GameObject::isDestroyed);
	}

	private void display()
	{
		GraphicsEnvironment.clear();

        for(GameObject gameObject : gameObjects)
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

        float rotation = (float) (270 - (Math.atan((y - mouseY) / (x - mouseX)) * (180 / Math.PI)));

        aimer.setRotation(-rotation);
    }
}
