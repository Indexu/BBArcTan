package com.ru.tgra.objects;

public abstract class GridObject extends GameObject
{
    protected int row;
    protected int col;

    public abstract void hit();

    public int getRow()
    {
        return row;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public int getCol()
    {
        return col;
    }

    public void setCol(int col)
    {
        this.col = col;
    }
}
