package com.ru.tgra.utilities;

public class ModelMatrix extends Matrix {

    public static ModelMatrix main;

    private float[] MMtmp;

    public ModelMatrix()
    {
        super();
        MMtmp = new float[16];
    }

    public Vector2D getA()
    {
        return new Vector2D(matrix.get(0), matrix.get(1));
    }

    public Vector2D getB()
    {
        return new Vector2D(matrix.get(4), matrix.get(5));
    }

    public Vector2D getC()
    {
        return new Vector2D(matrix.get(8), matrix.get(9));
    }

    public Point2D getOrigin()
    {
        return new Point2D(matrix.get(12), matrix.get(13));
    }

    public void addTranslationBaseCoords(float Tx, float Ty, float Tz)
    {
        matrix.put(12, matrix.get(12) + Tx);
        matrix.put(13, matrix.get(13) + Ty);
        matrix.put(14, matrix.get(14) + Tz);
    }

    public void addTranslation(Point2D translation)
    {
        matrix.put(12, matrix.get(0)*translation.x + matrix.get(4)*translation.y);
        matrix.put(13, matrix.get(1)*translation.x + matrix.get(5)*translation.y);
        matrix.put(14, matrix.get(2)*translation.x + matrix.get(6)*translation.y);
    }

    public void addScale(Vector2D scale)
    {
        matrix.put(0, scale.x * matrix.get(0));
        matrix.put(1, scale.x * matrix.get(1));

        matrix.put(4, scale.y * matrix.get(4));
        matrix.put(5, scale.y * matrix.get(5));
    }

    public void addRotationZ(float angle)
    {
        float c = (float)Math.cos((double)angle * Math.PI / 180.0);
        float s = (float)Math.sin((double)angle * Math.PI / 180.0);

        MMtmp[0] = c; MMtmp[4] = -s; MMtmp[8] = 0; MMtmp[12] = 0;
        MMtmp[1] = s; MMtmp[5] = c; MMtmp[9] = 0; MMtmp[13] = 0;
        MMtmp[2] = 0; MMtmp[6] = 0; MMtmp[10] = 1; MMtmp[14] = 0;
        MMtmp[3] = 0; MMtmp[7] = 0; MMtmp[11] = 0; MMtmp[15] = 1;

        this.addTransformation(MMtmp);
    }

    public void addRotationX(float angle)
    {
        float c = (float)Math.cos((double)angle * Math.PI / 180.0);
        float s = (float)Math.sin((double)angle * Math.PI / 180.0);

        MMtmp[0] = 1; MMtmp[4] = 0; MMtmp[8] = 0; MMtmp[12] = 0;
        MMtmp[1] = 0; MMtmp[5] = c; MMtmp[9] = -s; MMtmp[13] = 0;
        MMtmp[2] = 0; MMtmp[6] = s; MMtmp[10] = c; MMtmp[14] = 0;
        MMtmp[3] = 0; MMtmp[7] = 0; MMtmp[11] = 0; MMtmp[15] = 1;

        this.addTransformation(MMtmp);
    }

    public void addRotationY(float angle)
    {
        float c = (float)Math.cos((double)angle * Math.PI / 180.0);
        float s = (float)Math.sin((double)angle * Math.PI / 180.0);

        MMtmp[0] = c; MMtmp[4] = 0; MMtmp[8] = s; MMtmp[12] = 0;
        MMtmp[1] = 0; MMtmp[5] = 1; MMtmp[9] = 0; MMtmp[13] = 0;
        MMtmp[2] = -s; MMtmp[6] = 0; MMtmp[10] = c; MMtmp[14] = 0;
        MMtmp[3] = 0; MMtmp[7] = 0; MMtmp[11] = 0; MMtmp[15] = 1;

        this.addTransformation(MMtmp);
    }
}
