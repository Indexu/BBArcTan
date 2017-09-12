package com.ru.tgra;

import com.badlogic.gdx.Gdx;
import com.ru.tgra.objects.Block;
import com.ru.tgra.objects.GameObject;
import com.ru.tgra.objects.GridObject;
import com.ru.tgra.objects.particles.DestroyBlock;
import com.ru.tgra.utilities.Color;
import com.ru.tgra.utilities.Point2D;
import com.ru.tgra.utilities.RandomGenerator;

import java.util.ArrayList;
import java.util.Arrays;

public class GameManager
{
    public static ArrayList<GameObject> gameObjects;
    public static ArrayList<GridObject> gridObjects;
    public static boolean gameOver;

    private static Point2D[][] grid;
    private static ArrayList<Point2D> destroyBlockParticlesCoords;

    private static int shots;
    private static int score;

    public static void initGameManager()
    {
        gameObjects = new ArrayList<>();
        gridObjects = new ArrayList<>();
        destroyBlockParticlesCoords = new ArrayList<>();
        gameOver = false;

        shots = Settings.initialShots;
        score = 0;

        initGrid();
    }

    public static void nextRound()
    {
        shiftRows();

        if (gameOver)
        {
            gameOver();
        }
        else
        {
            addRow();
        }
    }

    public static void removeDestroyed()
    {
        gameObjects.removeIf(GameObject::isDestroyed);
        gridObjects.removeIf(GameObject::isDestroyed);
    }

    public static void addDestroyBlockParticles(Point2D position)
    {
        destroyBlockParticlesCoords.add(position);
    }

    public static ArrayList<Point2D> getDestroyBlockParticlesCoors()
    {
        return destroyBlockParticlesCoords;
    }

    public static void emptyAllCoordsLists()
    {
        destroyBlockParticlesCoords.clear();
    }

    private static void shiftRows()
    {
        for (GridObject gridObject : gridObjects)
        {
            int row = gridObject.getRow() - 1;

            if (!gameOver)
            {
                gameOver = (row == 0);
            }

            Point2D newPos = grid[row][gridObject.getCol()];

            gridObject.setPosition(newPos);
            gridObject.setRow(row);
        }
    }

    private static void addRow()
    {
        int blocksAdded;
        GameObject[] objectsToAdd = new GameObject[Settings.cols];

        do
        {
            blocksAdded = 0;
            Arrays.fill(objectsToAdd, null);

            for (int i = 0; i < Settings.cols; i++)
            {
                int row = Settings.rows - 1;

                Point2D position = grid[row][i];

                float rand = RandomGenerator.randomNumberInRange(0, 1);

                // Block
                if (Settings.chanceOfBlock < rand)
                {
                    Color testColor = new Color(0.5f, 0f, 0, 1);

                    Block block = new Block(position, 5, row, i);
                    block.setColor(testColor);

                    objectsToAdd[i] = block;

                    blocksAdded++;
                }
            }

        } while(blocksAdded < Settings.minimumNumberOfBlocksPerRow);

        for (int i = 0; i < Settings.cols; i++)
        {
            if (objectsToAdd[i] != null)
            {
                gameObjects.add(objectsToAdd[i]);
                gridObjects.add((GridObject) objectsToAdd[i]);
            }
        }
    }

    private static void gameOver()
    {
        // TODO
        System.out.println("GAME OVER");
    }

    private static void initGrid()
    {
        grid = new Point2D[Settings.rows][Settings.cols];

        float rowHeight = Gdx.graphics.getHeight() / Settings.rows;
        float colWidth = Gdx.graphics.getWidth() / Settings.cols;

        for (int i = 0; i < Settings.rows; i++)
        {
            for (int j = 0; j < Settings.cols; j++)
            {
                grid[i][j] = new Point2D((j * colWidth) + (colWidth / 2), (i * rowHeight) + (rowHeight / 2));
            }
        }
    }

    public static void increaseShots()
    {
        shots++;
    }

    public static void increaseScore()
    {
        score++;
    }
}
