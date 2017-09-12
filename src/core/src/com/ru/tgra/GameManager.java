package com.ru.tgra;

import com.badlogic.gdx.Gdx;
import com.ru.tgra.objects.Ball;
import com.ru.tgra.objects.Block;
import com.ru.tgra.objects.GameObject;
import com.ru.tgra.objects.GridObject;
import com.ru.tgra.objects.particles.DestroyBlock;
import com.ru.tgra.objects.powerups.BallUp;
import com.ru.tgra.utilities.*;

import java.util.ArrayList;
import java.util.Arrays;

public class GameManager
{
    public static ArrayList<GameObject> gameObjects;
    public static ArrayList<GridObject> gridObjects;
    public static boolean gameOver;
    public static boolean roundInProgress;
    public static boolean shootingInProgress;
    public static boolean aimingInProgress;
    public static GameObject aimer;
    public static int shots;
    public static int score;
    public static int round;
    public static boolean firstDestroyedBall;

    private static int ballsInPlay;
    private static Point2D[][] grid;
    private static ArrayList<Point2D> destroyBlockParticlesCoords;

    public static void initGameManager()
    {
        gameObjects = new ArrayList<>();
        gridObjects = new ArrayList<>();
        destroyBlockParticlesCoords = new ArrayList<>();
        gameOver = false;
        roundInProgress = true;
        aimingInProgress = true;
        shootingInProgress = false;
        firstDestroyedBall = true;

        shots = Settings.initialShots;
        score = 0;
        ballsInPlay = 0;
        round = 0;

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
            round++;
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

    public static void spawnBall(Vector2D direction)
    {
        GameObject ball = new Ball(new Point2D(aimer.getPosition()), direction, Settings.BallSpeed);

        gameObjects.add(ball);

        ballsInPlay++;
    }

    public static void setAimerRotation(float mouseX, float mouseY)
    {
        float x = aimer.getPosition().x;
        float y = aimer.getPosition().y;

        float rotation = (float) ( 270 - Math.atan2((double) (y - mouseY), (double)(x - mouseX)) * (180 / Math.PI));

        aimer.setRotation(-rotation);
    }

    public static void ballDestroyed(Point2D position)
    {
        ballsInPlay--;

        if (firstDestroyedBall)
        {
            float width = Gdx.graphics.getWidth();
            aimer.getPosition().x = MathUtils.clamp(position.x, width / 8, width - (width / 8));

            firstDestroyedBall = false;
        }

        if (ballsInPlay == 0)
        {
            roundInProgress = false;
        }
    }

    public static void checkIfNextRound()
    {
        if (ballsInPlay == 0 && !roundInProgress)
        {
            nextRound();
            roundInProgress = true;
            aimingInProgress = true;
            firstDestroyedBall = true;
        }
    }

    private static void shiftRows()
    {
        if (gameOver)
        {
            return;
        }

        for (GridObject gridObject : gridObjects)
        {
            int row = gridObject.getRow() - 1;

            if (!gameOver)
            {
                gameOver = (row == 1);
            }

            Point2D newPos = grid[row][gridObject.getCol()];

            gridObject.setPosition(newPos);
            gridObject.setRow(row);
        }
    }

    private static void addRow()
    {
        int blocksAdded;
        boolean ballUpAdded;
        GameObject[] objectsToAdd = new GameObject[Settings.cols];

        do
        {
            blocksAdded = 0;
            ballUpAdded = false;
            Arrays.fill(objectsToAdd, null);

            for (int i = 0; i < Settings.cols; i++)
            {
                int row = Settings.rows - 1;

                Point2D position = grid[row][i];

                float rand = RandomGenerator.randomNumberInRange(0, 1);

                // Ball up
                if (!ballUpAdded && Settings.chanceOfBallUp < rand)
                {
                    BallUp ballUp = new BallUp(position, row, i);

                    objectsToAdd[i] = ballUp;

                    ballUpAdded = true;
                }
                // Block
                else if (Settings.chanceOfBlock < rand && blocksAdded != Settings.maximumNumberOfBlocksPerRow)
                {
                    Color testColor = new Color(0.5f, 0f, 0, 1);

                    Block block = new Block(position, round, row, i);
                    block.setColor(testColor);

                    objectsToAdd[i] = block;

                    blocksAdded++;
                }
            }

        } while(blocksAdded <= Settings.minimumNumberOfBlocksPerRow || !ballUpAdded);

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
