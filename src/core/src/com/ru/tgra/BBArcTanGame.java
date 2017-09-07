package com.ru.tgra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;

import java.util.Random;

public class BBArcTanGame extends ApplicationAdapter
{
	private float position_x;
	private float position_y;
	private float speed;
	private float scale_x = 1f;
	private float scale_y = 1f;
	private float height = 768;
	private float width = 1024;
	private Random rand;

	@Override
	public void create ()
	{
		GraphicsEnvironment.setupGraphicsEnvironment();

		GraphicsEnvironment.setClearColor(0.4f, 0.6f, 1.0f, 1.0f);

		position_x = 300;
		position_y = 300;
		speed = 6f;

		rand = new Random();

		GraphicsEnvironment.setColor(0.3f, 0.2f, 0, 1);
	}

	private void update()
	{
		if(Gdx.input.justTouched())
		{
			//do mouse/touch input stuff
			position_x = Gdx.input.getX();
			position_y = height - Gdx.input.getY();
		}

		//do all updates to the game

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			position_x += speed;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			position_x -= speed;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.UP))
		{
			position_y += speed;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
		{
			position_y -= speed;
		}

		if (height < position_y + 50f)
		{
			position_y = height - 50f;
		}
		else if (position_y - 50f < 0)
		{
			position_y = 50f;
		}

		if (width < position_x + 50f)
		{
			position_x = width - 50f;
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
		//do all actual drawing and rendering here
		GraphicsEnvironment.clear();

		GraphicsEnvironment.clearModelMatrix();
		GraphicsEnvironment.setModelMatrixTranslation(position_x, position_y);
		GraphicsEnvironment.setModelMatrixScale(scale_x, scale_y);

		Gdx.gl.glVertexAttribPointer(GraphicsEnvironment.getPositionLoc(), 2, GL20.GL_FLOAT, false, 0,
				GraphicsEnvironment.getVertexBuffer());
		Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_STRIP, 0, 4);
	}

	@Override
	public void render ()
	{
		update();
		display();
	}
}
