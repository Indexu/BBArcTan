package com.ru.tgra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.ru.tgra.objects.Block;
import com.ru.tgra.objects.GameObject;
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

	@Override
	public void create ()
	{
		GraphicsEnvironment.setupGraphicsEnvironment();

        Color clearColor = new Color(0f, 0f, 0f, 1.0f);
		GraphicsEnvironment.setClearColor(clearColor);

		Color testColor = new Color(0.5f, 0f, 0, 1);

        block = new Block(new Point2D(250, 500), 5);
        block.setScale(new Vector2D(70, 70));
        block.setColor(testColor);

        gameObjects = new ArrayList<GameObject>();

        gameObjects.add(block);
	}

	private void update()
	{
	    float deltaTime = Gdx.graphics.getDeltaTime();

		if(Gdx.input.justTouched())
		{
			//do mouse/touch input stuff
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            gameObjects.add(new DestroyBlock(new Point2D(x, y)));
		}

		//do all updates to the game
        for(GameObject gameObject : gameObjects)
        {
            gameObject.update(deltaTime);
        }
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
}
