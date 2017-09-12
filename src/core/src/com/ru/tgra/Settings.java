package com.ru.tgra;

import com.ru.tgra.utilities.Color;

public class Settings
{
    public static final Color BackgroundColor = new Color(0, 0, 0, 1);

    public static final Color AimerBaseColor = new Color(0.5f, 0, 0, 1);

    public static final int DestroyBlockNumberOfParticles = 50;
    public static final float DestroyBlockLifespan = 2f;

    public static final int BallSize = 7;
    public static final int BallSpeed = 500;

    public static final float BlockSize = 60.0f;
    public static final float BlockTextOffset = 5.0f;

    public static final float PowerUpSize = 40.0f;
    public static final Color BallUpColor = new Color(1, 1, 0, 1);

    public static final int rows = 11;
    public static final int cols = 7;
    public static final int minimumNumberOfBlocksPerRow = 2;
    public static final int maximumNumberOfBlocksPerRow = 5;
    public static final int initialRows = 1;

    public static final float chanceOfBallUp = 0.1f;
    public static final float chanceOfEmpty = 0.4f;
    public static final float chanceOfBlock = 0.5f;

    public static final int initialShots = 1;
    public static final float timeBetweenShots = 0.08f;
}
