package com.ru.tgra.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;

public class CircleGraphic
{
    private static FloatBuffer vertexBuffer;
    private static int vertexPointer;
    private static int verticesPerCircle = 32;

    public static void create(int vertexPointer)
    {
        CircleGraphic.vertexPointer = vertexPointer;

        float[] array = new float[2*verticesPerCircle];

        float step = (float) ((2*Math.PI) / verticesPerCircle);

        for (int i = 0; i < verticesPerCircle; i++)
        {
            float x = (float) (Math.cos(step * i));
            float y = (float) (Math.sin(step * i));

            array[2*i] = x;
            array[(2*i) + 1] = y;
        }

        vertexBuffer = BufferUtils.newFloatBuffer(2*verticesPerCircle);
        vertexBuffer.put(array);
        vertexBuffer.rewind();
    }

    public static void drawSolid()
    {
        Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT,
                false, 0, vertexBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 0, verticesPerCircle);
    }

    public static void drawOutline()
    {
        Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT,
                false, 0, vertexBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_LINE_LOOP, 0, verticesPerCircle);
    }
}
