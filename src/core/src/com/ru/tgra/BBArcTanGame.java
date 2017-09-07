package com.ru.tgra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.ru.tgra.shapes.CircleGraphic;
import com.ru.tgra.shapes.RectangleGraphic;

import java.util.Random;

public class BBArcTanGame extends ApplicationAdapter
{
	private float position_x;
	private float position_y;
	private float speed;
	private float scale_x = 100f;
	private float scale_y = 100f;
	private Random rand;

	@Override
	public void create ()
	{
		GraphicsEnvironment.setupGraphicsEnvironment();

		GraphicsEnvironment.setClearColor(0.4f, 0.6f, 1.0f, 1.0f);

		position_x = 300;
		position_y = 300;
		speed = 300f;

		rand = new Random();

		GraphicsEnvironment.setColor(0.3f, 0.2f, 0, 1);
	}

	private void update()
	{
	    float deltaTime = Gdx.graphics.getDeltaTime();

		if(Gdx.input.justTouched())
		{
			//do mouse/touch input stuff
			position_x = Gdx.input.getX();
			position_y = Gdx.graphics.getHeight() - Gdx.input.getY();
		}

		//do all updates to the game

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			position_x += speed * deltaTime;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			position_x -= speed * deltaTime;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.UP))
		{
			position_y += speed * deltaTime;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
		{
			position_y -= speed * deltaTime;
		}

		if (Gdx.graphics.getHeight() < position_y + 50f)
		{
			position_y = Gdx.graphics.getHeight() - 50f;
		}
		else if (position_y - 50f < 0)
		{
			position_y = 50f;
		}

		if (Gdx.graphics.getWidth() < position_x + 50f)
		{
			position_x = Gdx.graphics.getWidth() - 50f;
		}
		else if (position_x - 50f < 0)
		{
			position_x = 50f;
		}

		scale_x += rand.nextFloat() * (0.05f) - 0.025f;
		scale_y += rand.nextFloat() * (0.05f) - 0.025f;
	}

	private void display()
	{
		GraphicsEnvironment.clear();

		GraphicsEnvironment.clearModelMatrix();
		GraphicsEnvironment.setModelMatrixTranslation(position_x, position_y);
		GraphicsEnvironment.setModelMatrixScale(scale_x, scale_y);

        RectangleGraphic.drawSolid();
        CircleGraphic.drawOutline();
	}

	@Override
	public void render ()
	{
		update();
		display();

		// GraphicsEnvironment.drawText("Hallo verden", 500, 400);
	}
}
