package com.ru.tgra;

import com.badlogic.gdx.Gdx;
import com.ru.tgra.objects.*;
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
    public static GameObject layout;
    public static int shots;
    public static int score;
    public static int round;
    public static boolean firstDestroyedBall;
    public static Point2D shootOriginPoint;

    private static int ballsInPlay;
    private static Point2D[][] grid;
    private static ArrayList<Point2D> destroyBlockParticlesCoords;
    private static Vector2D shootDirection;

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
        score = Settings.initialScore;
        ballsInPlay = 0;
        round = 0;

        shootDirection = new Vector2D();
        shootOriginPoint = new Point2D();

        layout = new Layout(new Point2D(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 24));

        initGrid();
        initAimer();
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

            if (round != 1)
            {
                increaseScore(Settings.ScoreNextRound);
            }

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

    public static void spawnBall()
    {
        GameObject ball = new Ball(new Point2D(shootOriginPoint), shootDirection, Settings.BallSpeed);

        gameObjects.add(ball);

        ballsInPlay++;
    }

    public static void setAimerRotation(float mouseX, float mouseY)
    {
        float x = aimer.getPosition().x;
        float y = aimer.getPosition().y;

        float rotation = (float) ( 270 - Math.atan2((double) (y - mouseY), (double)(x - mouseX)) * (180 / Math.PI));

        if (MathUtils.isBetween(rotation, Settings.MinAimerAngle, Settings.MaxAimerAngle))
        {
            aimer.setRotation(-rotation);

            if (aimingInProgress)
            {
                shootDirection = aimer.getPosition().vectorBetweenPoints(new Point2D(mouseX, mouseY));
                shootDirection.normalize();
            }
        }
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

    public static void endRound()
    {
        if (!aimingInProgress)
        {
            gameObjects.removeIf(gameObject -> gameObject instanceof Ball);

            ballsInPlay = 0;
            roundInProgress = false;
            shootingInProgress = false;
        }
    }

    public static void increaseShots()
    {
        shots++;
    }

    public static void increaseScore(float amount)
    {
        score += amount;
    }

    public static Color getBlockColor(int number)
    {
        if (number == 1)
        {
            return Settings.BlockColor1;
        }
        else if (number == 2)
        {
            return Settings.BlockColor2;
        }
        else if (number == 3)
        {
            return Settings.BlockColor3;
        }
        else if (number == 4)
        {
            return Settings.BlockColor4;
        }
        else if (MathUtils.isBetween(number, 5, 9))
        {
            return Settings.BlockColor5;
        }
        else if (MathUtils.isBetween(number, 10, 19))
        {
            return Settings.BlockColor10;
        }
        else if (MathUtils.isBetween(number, 20, 29))
        {
            return Settings.BlockColor20;
        }
        else if (MathUtils.isBetween(number, 30, 39))
        {
            return Settings.BlockColor30;
        }
        else if (MathUtils.isBetween(number, 40, 49))
        {
            return Settings.BlockColor40;
        }
        else if (MathUtils.isBetween(number, 50, 74))
        {
            return Settings.BlockColor50;
        }
        else if (MathUtils.isBetween(number, 75, 99))
        {
            return Settings.BlockColor75;
        }
        else
        {
            return Settings.BlockColor100;
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
                if (!ballUpAdded && rand < Settings.chanceOfBallUp)
                {
                    BallUp ballUp = new BallUp(position, row, i);

                    objectsToAdd[i] = ballUp;

                    ballUpAdded = true;
                }
                // Block
                else if (rand < Settings.chanceOfBlock && blocksAdded != Settings.maximumNumberOfBlocksPerRow)
                {
                    Block block = new Block(position, round, row, i);

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

    private static void initAimer()
    {
        Point2D aimerPos = new Point2D(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 8);
        GameManager.aimer = new Aimer(aimerPos, Settings.AimerColor);
    }
}
