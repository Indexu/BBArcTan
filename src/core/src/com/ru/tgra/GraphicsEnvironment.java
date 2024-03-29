package com.ru.tgra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.BufferUtils;
import com.ru.tgra.shapes.CircleGraphic;
import com.ru.tgra.shapes.RectangleGraphic;
import com.ru.tgra.shapes.TriangleGraphic;
import com.ru.tgra.utilities.Color;
import com.ru.tgra.utilities.Point2D;

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
    private static BitmapFont fontNormal;
    private static BitmapFont fontLarge;
    private static BitmapFont fontExtraLarge;
    private static GlyphLayout layout;

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

    public static void setColor(Color color)
    {
        Gdx.gl.glUniform4f(colorLoc, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static void setClearColor(Color color)
    {
        Gdx.gl.glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static void clear()
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public static void drawText(Point2D position, String text, Color color, int size)
    {
        BitmapFont fontToUse;

        switch (size)
        {
            default:
            case 1:
                fontToUse = fontNormal;
                break;

            case 2:
                fontToUse = fontLarge;
                break;

            case 3:
                fontToUse = fontExtraLarge;
                break;
        }

        layout = new GlyphLayout(fontToUse, text);

        float offsetX = layout.width /2;
        float offsetY = layout.height /2;

        float fontX = position.x - offsetX;
        float fontY = position.y + offsetY;

        if (GameManager.shaking)
        {
            fontX += GameManager.shakeOffsetX;
            fontY += GameManager.shakeOffsetY;
        }

        batch.begin();

        fontToUse.setColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        fontToUse.draw(batch, text, fontX, fontY);

        batch.end();

        Gdx.gl.glUseProgram(renderingProgramID);
        Gdx.gl.glEnableVertexAttribArray(vertexPointer);
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

        parameter.size = 18;
        fontNormal = generator.generateFont(parameter);

        parameter.size = 32;
        fontLarge = generator.generateFont(parameter);

        parameter.size = 46;
        fontExtraLarge = generator.generateFont(parameter);

        generator.dispose();
    }

    private static void initShapes()
    {
        RectangleGraphic.create(vertexPointer);
        CircleGraphic.create(vertexPointer);
        TriangleGraphic.create(vertexPointer);
    }

    public static int getModelMatrixLoc()
    {
        return modelMatrixLoc;
    }
}
