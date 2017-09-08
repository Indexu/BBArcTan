package com.ru.tgra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.BufferUtils;
import com.ru.tgra.shapes.CircleGraphic;
import com.ru.tgra.shapes.RectangleGraphic;
import com.sun.xml.internal.messaging.saaj.soap.GifDataContentHandler;

import java.nio.FloatBuffer;

public class GraphicsEnvironment
{
    private static int renderingProgramID;
    private static int vertexShaderID;
    private static int fragmentShaderID;
    private static int vertexPointer;
    private static int colorLoc;

    private static FloatBuffer vertexBuffer;
    private static FloatBuffer modelMatrix;
    private static FloatBuffer projectionMatrix;

    private static int modelMatrixLoc;
    private static int projectionMatrixLoc;

    private static SpriteBatch batch;
    private static BitmapFont font12;

    private static boolean testing = true;

    public static void setupGraphicsEnvironment()
    {
        initProgram();
        initProjectionMatrix();
        initModelMatrix();
        initVertexBuffer();
        initColor();
        initFonts();
        initShapes();
    }

    public static void OrthographicProjection2D(float left, float right, float bottom, float top)
    {
        float[] pm = new float[16];

        pm[0] = 2.0f / (right - left); pm[4] = 0.0f; pm[8] = 0.0f; pm[12] = -(right + left) / (right - left);
        pm[1] = 0.0f; pm[5] = 2.0f / (top - bottom); pm[9] = 0.0f; pm[13] = -(top + bottom) / (top - bottom);
        pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = 1.0f; pm[14] = 0.0f;
        pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;

        projectionMatrix = BufferUtils.newFloatBuffer(16);
        projectionMatrix.put(pm);
        projectionMatrix.rewind();

        Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, projectionMatrix);
    }

    public static void clearModelMatrix()
    {
        modelMatrix.put(0, 1.0f);
        modelMatrix.put(1, 0.0f);
        modelMatrix.put(2, 0.0f);
        modelMatrix.put(3, 0.0f);
        modelMatrix.put(4, 0.0f);
        modelMatrix.put(5, 1.0f);
        modelMatrix.put(6, 0.0f);
        modelMatrix.put(7, 0.0f);
        modelMatrix.put(8, 0.0f);
        modelMatrix.put(9, 0.0f);
        modelMatrix.put(10, 1.0f);
        modelMatrix.put(11, 0.0f);
        modelMatrix.put(12, 0.0f);
        modelMatrix.put(13, 0.0f);
        modelMatrix.put(14, 0.0f);
        modelMatrix.put(15, 1.0f);

        Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);
    }

    public static void setModelMatrixTranslation(float xTranslate, float yTranslate)
    {
        modelMatrix.put(12, xTranslate);
        modelMatrix.put(13, yTranslate);

        Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);
    }

    public static void setModelMatrixScale(float xScale, float yScale)
    {
        /*
        float[] sm = new float[16];

        sm[0] = xScale; sm[4] = 0.0f; sm[8] = 0.0f; sm[12] = 0.0f;
        sm[1] = 0.0f; sm[5] = yScale; sm[9] = 0.0f; sm[13] = 0.0f;
        sm[2] = 0.0f; sm[6] = 0.0f; sm[10] = 1.0f; sm[14] = 0.0f;
        sm[3] = 0.0f; sm[7] = 0.0f; sm[11] = 0.0f; sm[15] = 1.0f;

        float[] newModelMatrix = multiplySquareMatrixAndBuffer(sm, modelMatrix, 4);

        if (testing)
        {
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    int index = (j * 4) + i;

                    System.out.print("|" + modelMatrix.get(index) + "|");
                }

                System.out.println();
            }

            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    int index = (j * 4) + i;

                    System.out.print("|" + newModelMatrix[index] + "|");
                }

                System.out.println();
            }

            testing = false;
        }
        */

        modelMatrix.put(0, xScale);
        modelMatrix.put(5, yScale);

        Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);
    }

    public static void setModelMatrixRotation(float theta)
    {
        float[] rm = new float[16];

        rm[0] = (float) Math.cos(theta * Math.PI / 180.0); rm[4] = (float) -Math.sin(theta * Math.PI / 180.0); rm[8] = 0.0f; rm[12] = 0.0f;
        rm[1] = (float) Math.sin(theta * Math.PI / 180.0); rm[5] = (float) Math.cos(theta * Math.PI / 180.0); rm[9] = 0.0f; rm[13] = 0.0f;
        rm[2] = 0.0f; rm[6] = 0.0f; rm[10] = 1.0f; rm[14] = 0.0f;
        rm[3] = 0.0f; rm[7] = 0.0f; rm[11] = 0.0f; rm[15] = 1.0f;

        float[] newModelMatrix = multiplySquareMatrixAndBuffer(rm, modelMatrix, 4);

        modelMatrix.put(newModelMatrix);
        modelMatrix.rewind();

        Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);
    }

    public static void setColor(float r, float g, float b, float a)
    {
        Gdx.gl.glUniform4f(colorLoc, r, g, b, a);
    }

    public static void setClearColor(float r, float g, float b, float a)
    {
        Gdx.gl.glClearColor(r, g, b, a);
    }

    public static void clear()
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public static FloatBuffer getVertexBuffer()
    {
        return vertexBuffer;
    }

    public static int getVertexPointer()
    {
        return vertexPointer;
    }

    public static void drawText(String text, float pos_x, float pos_y)
    {
        batch.begin();
        font12.draw(batch, text, pos_x, pos_y);
        batch.end();
    }

    /*
     * Private helpers
     */

    private static void initProgram()
    {
        String vertexShaderString;
        String fragmentShaderString;

        vertexShaderString = Gdx.files.internal("shaders/simple2D.vert").readString();
        fragmentShaderString =  Gdx.files.internal("shaders/simple2D.frag").readString();

        vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
        fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
        Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);

        Gdx.gl.glCompileShader(vertexShaderID);
        Gdx.gl.glCompileShader(fragmentShaderID);

        renderingProgramID = Gdx.gl.glCreateProgram();

        Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
        Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);

        Gdx.gl.glLinkProgram(renderingProgramID);
        Gdx.gl.glUseProgram(renderingProgramID);
    }

    private static void initProjectionMatrix()
    {
        projectionMatrixLoc	= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

        OrthographicProjection2D(0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight());
    }

    private static void initModelMatrix()
    {
        modelMatrixLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");

        float[] mm = new float[16];

        mm[0] = 1.0f; mm[4] = 0.0f; mm[8] = 0.0f; mm[12] = 0.0f;
        mm[1] = 0.0f; mm[5] = 1.0f; mm[9] = 0.0f; mm[13] = 0.0f;
        mm[2] = 0.0f; mm[6] = 0.0f; mm[10] = 1.0f; mm[14] = 0.0f;
        mm[3] = 0.0f; mm[7] = 0.0f; mm[11] = 0.0f; mm[15] = 1.0f;

        modelMatrix = BufferUtils.newFloatBuffer(16);
        modelMatrix.put(mm);
        modelMatrix.rewind();

        Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);
    }

    private static void initVertexBuffer()
    {
        vertexPointer = Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
        Gdx.gl.glEnableVertexAttribArray(vertexPointer);

        float[] array = {-0.5f, -0.5f,
                -0.5f, 0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f};

        vertexBuffer = BufferUtils.newFloatBuffer(8);
        vertexBuffer.put(array);
        vertexBuffer.rewind();
    }

    private static void initColor()
    {
        colorLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_color");
    }

    private static void initFonts()
    {
        batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/RobotoMono-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 12;

        font12 = generator.generateFont(parameter);
        generator.dispose();
    }

    private static void initShapes()
    {
        RectangleGraphic.create(vertexPointer);
        CircleGraphic.create(vertexPointer);
    }

    private static float[] multiplySquareMatrixAndBuffer(float[] arrayMatrix, FloatBuffer bufferMatrix, int rows)
    {
        float[] resultMatrix = new float[rows * rows];

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                float sum = 0.0f;

                for (int k = 0; k < rows; k++)
                {
                    sum = sum + modelMatrix.get(i * rows + k) * arrayMatrix[k * rows + j];
                }

                resultMatrix[i * rows + j] = sum;
            }
        }

        return resultMatrix;
    }
}
