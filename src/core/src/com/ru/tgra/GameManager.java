package com.ru.tgra;

import com.badlogic.gdx.Gdx;
import com.ru.tgra.objects.*;
import com.ru.tgra.objects.powerups.BallUp;
import com.ru.tgra.utilities.*;

import java.util.ArrayList;
import java.util.Arrays;

public class GameManager
{
    public static ArrayList<GameObject> gameObjects;
    public static ArrayList<GridObject> gridObjects;
    public static boolean paused;
    public static boolean gameOver;
    public static boolean roundInProgress;
    public static boolean shootingInProgress;
    public static boolean aimingInProgress;
    public static boolean restartable;
    public static boolean mainMenu;
    public static GameObject aimer;
    public static GameObject layout;
    public static int shots;
    public static int score;
    public static int round;
    public static boolean firstDestroyedBall;
    public static Point2D shootOriginPoint;
    public static Point2D gameOverPausePosition;
    public static boolean shaking;
    public static float shakeOffsetX;
    public static float shakeOffsetY;

    private static int ballsInPlay;
    private static Point2D[][] grid;
    private static ArrayList<Point2D> destroyBlockParticlesCoords;
    private static ArrayList<Point2D> destroyBallUpParticlesCoords;
    private static ArrayList<Point2D> hitBlockParticlesCoords;
    private static ArrayList<Color> hitBlockParticlesColors;
    private static Vector2D shootDirection;
    private static float gameOverTimer;
    private static boolean gameOverDone;
    private static float shakerTimer;

    public static void initGameManager()
    {
        gameObjects = new ArrayList<>();
        gridObjects = new ArrayList<>();

        destroyBlockParticlesCoords = new ArrayList<>();
        destroyBallUpParticlesCoords = new ArrayList<>();
        hitBlockParticlesCoords = new ArrayList<>();
        hitBlockParticlesColors = new ArrayList<>();

        gameOver = false;
        roundInProgress = true;
        aimingInProgress = true;
        shootingInProgress = false;
        firstDestroyedBall = true;
        paused = false;
        restartable = false;
        shaking = false;

        shots = Settings.InitialShots;
        score = Settings.InitialScore;
        ballsInPlay = 0;
        round = 0;

        shakerTimer = 0;
        shakeOffsetX = 0;
        shakeOffsetY = 0;

        gameOverTimer = 0f;
        gameOverDone = false;
        gameOverPausePosition = new Point2D(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 3));
        Settings.GameOverColor.setAlpha(0);

        shootDirection = new Vector2D();
        shootOriginPoint = new Point2D();

        layout = new Layout(new Point2D(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 24));

        initGrid();
        initAimer();
    }

    public static void nextRound()
    {
        shiftRows();

        if (!gameOver)
        {
            round++;

            if (round != 1)
            {
                increaseScore(Settings.ScoreNextRound);
            }

            addRow();
        }
    }

    public static void pause()
    {
        paused = !paused;
    }

    public static void gameOver(float deltaTime)
    {
        gameOverTimer +=deltaTime;

        if (!gameOverDone && Settings.GameOverTime <= gameOverTimer)
        {
            gameOverTimer = Settings.GameOverTime;
            gameOverDone = true;
            restartable = true;
        }

        Settings.GameOverColor.setAlpha(gameOverTimer / Settings.GameOverTime);

        GraphicsEnvironment.drawText(gameOverPausePosition, "Game Over", Settings.GameOverColor, 3);
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

    public static void addDestroyBallUpParticles(Point2D position)
    {
        destroyBallUpParticlesCoords.add(position);
    }

    public static ArrayList<Point2D> getDestroyBlockParticlesCoords()
    {
        return destroyBlockParticlesCoords;
    }

    public static ArrayList<Point2D> getDestroyBallUpParticlesCoords()
    {
        return destroyBallUpParticlesCoords;
    }

    public static void addHitBlockParticles(Point2D position, Color color)
    {
        hitBlockParticlesCoords.add(position);
        hitBlockParticlesColors.add(color);
    }

    public static ArrayList<Point2D> getHitBlockParticlesCoords()
    {
        return hitBlockParticlesCoords;
    }

    public static ArrayList<Color> getHitBlockParticlesColors()
    {
        return hitBlockParticlesColors;
    }

    public static void emptyAllCoordsLists()
    {
        destroyBlockParticlesCoords.clear();
        destroyBallUpParticlesCoords.clear();

        hitBlockParticlesCoords.clear();
        hitBlockParticlesColors.clear();
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

    public static void screenShake(float deltaTime)
    {
        shakerTimer += deltaTime;

        if (shakerTimer <= Settings.ShakerTime)
        {
            shakeOffsetX = RandomGenerator.randomNumberInRange(-Settings.ShakerStrength, Settings.ShakerStrength);
            shakeOffsetY = RandomGenerator.randomNumberInRange(-Settings.ShakerStrength, Settings.ShakerStrength);

            GraphicsEnvironment.OrthographicProjection2D
            (
                -shakeOffsetX,
                Gdx.graphics.getWidth() - shakeOffsetX,
                -shakeOffsetY,
                Gdx.graphics.getHeight() - shakeOffsetY
            );
        }
        else
        {
            shakerTimer = 0f;
            shaking = false;

            GraphicsEnvironment.OrthographicProjection2D
            (
                0,
                Gdx.graphics.getWidth(),
                0,
                Gdx.graphics.getHeight()
            );
        }
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
                gameOverDone = !gameOver;
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
        GameObject[] objectsToAdd = new GameObject[Settings.Cols];

        do
        {
            blocksAdded = 0;
            ballUpAdded = false;
            Arrays.fill(objectsToAdd, null);

            for (int i = 0; i < Settings.Cols; i++)
            {
                int row = Settings.Rows - 1;

                Point2D position = grid[row][i];

                float rand = RandomGenerator.randomNumberInRange(0, 1);

                // Ball up
                if (!ballUpAdded && rand < Settings.ChanceOfBallUp)
                {
                    BallUp ballUp = new BallUp(position, row, i);

                    objectsToAdd[i] = ballUp;

                    ballUpAdded = true;
                }
                // Block
                else if (rand < Settings.ChanceOfBlock && blocksAdded != Settings.MaximumNumberOfBlocksPerRow)
                {
                    Block block = new Block(position, round, row, i);

                    objectsToAdd[i] = block;

                    blocksAdded++;
                }
            }

        } while(blocksAdded <= Settings.MinimumNumberOfBlocksPerRow || !ballUpAdded);

        for (int i = 0; i < Settings.Cols; i++)
        {
            if (objectsToAdd[i] != null)
            {
                gameObjects.add(objectsToAdd[i]);
                gridObjects.add((GridObject) objectsToAdd[i]);
            }
        }
    }

    private static void initGrid()
    {
        grid = new Point2D[Settings.Rows][Settings.Cols];

        float rowHeight = Gdx.graphics.getHeight() / Settings.Rows;
        float colWidth = Gdx.graphics.getWidth() / Settings.Cols;

        for (int i = 0; i < Settings.Rows; i++)
        {
            for (int j = 0; j < Settings.Cols; j++)
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
