package com.ru.tgra;

import com.ru.tgra.utilities.Color;

public class Settings
{
    public static final Color BackgroundColor = new Color(0, 0, 0, 1);

    public static final Color AimerBaseColor = new Color(0.5f, 0, 0, 1);
    public static final Color AimerColor = new Color(0.5f, 0.5f, 0.5f, 1);

    public static final float MinAimerAngle = 275;
    public static final float MaxAimerAngle = 445;

    public static final int ScoreHitBlock= 1;
    public static final int ScoreDestroyBlock = 10;
    public static final int ScoreNextRound = 20;

    public static final int DestroyBlockNumberOfParticles = 50;
    public static final float DestroyBlockLifespan = 2f;
    public static final int DestroyBallUpNumberOfParticles = 10;
    public static final float DestroyBallUpLifespan = 0.7f;
    public static final int HitBlockNumberOfParticles = 10;
    public static final float HitBlockLifespan = 0.5f;

    public static final float ParticleGravity = 100f;

    public static final float ShakerTime = 0.3f;
    public static final float ShakerStrength = 2f;

    public static final int BallSize = 7;
    public static final int BallSpeed = 700;

    public static final float T_HitEpsilon = 0.99f;

    public static final float BlockSize = 60.0f;

    public static final float PowerUpSize = 40.0f;
    public static final Color BallUpColor = new Color(0.46f, 1, 0.1f, 1);

    public static final int Rows = 11;
    public static final int Cols = 7;
    public static final int MinimumNumberOfBlocksPerRow = 2;
    public static final int MaximumNumberOfBlocksPerRow = 5;
    public static final int InitialRows = 1;

    public static final float ChanceOfBallUp = 0.1f;
    public static final float ChanceOfBlock = 0.5f;

    public static final int InitialShots = 1;
    public static final int InitialScore = 0;
    public static final float TimeBetweenShots = 0.08f;

    public static final float GameOverTime = 2f;
    public static final Color GameOverColor = new Color(1, 1, 1, 0);
    public static final String GameOverText = "Game Over";

    public static final Color PauseColor = new Color(1, 1, 1, 1);
    public static final String PauseText = "PAUSED";

    // Block color numbers
    public static final Color BlockColor1 = new Color(1f, 0.92f, 0.23f, 1f);
    public static final Color BlockColor2 = new Color(1f, 0.75f, 0.2f, 1f);
    public static final Color BlockColor3 = new Color(1f, 0.59f, 0f, 1f);
    public static final Color BlockColor4 = new Color(1f, 0.34f, 0.13f, 1f);
    public static final Color BlockColor5 = new Color(0.95f, 0.26f, 0.21f, 1f);
    public static final Color BlockColor10 = new Color(0.72f, 0.11f, 0.11f, 1f);
    public static final Color BlockColor20 = new Color(0f, 0.73f, 0.83f, 1f);
    public static final Color BlockColor30 = new Color(0.1f, 0.66f, 0.95f, 1f);
    public static final Color BlockColor40 = new Color(0.13f, 0.59f, 0.95f, 1f);
    public static final Color BlockColor50 = new Color(0.24f, 0.31f, 0.71f, 1f);
    public static final Color BlockColor75 = new Color(0.61f, 0.15f, 0.69f, 1f);
    public static final Color BlockColor100 = new Color(0.4f, 0.22f, 0.71f, 1f);
}
