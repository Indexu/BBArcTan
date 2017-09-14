package com.ru.tgra.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;

public class TriangleGraphic
{
    private static FloatBuffer vertexBuffer;
    private static int vertexPointer;

    public static void create(int vertexPointer)
    {
        TriangleGraphic.vertexPointer = vertexPointer;

        float[] center = {-0.5f, -0.5f,
                -0.5f, 0.5f,
                0.5f, -0.5f};

        vertexBuffer = BufferUtils.newFloatBuffer(6);
        vertexBuffer.put(center);
        vertexBuffer.rewind();
    }

    public static void drawSolid()
    {
        Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT,
                false, 0, vertexBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 0, 3);
    }

    public static void drawOutline()
    {
        Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT,
                false, 0, vertexBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_LINE_LOOP, 0, 3);
    }
}
