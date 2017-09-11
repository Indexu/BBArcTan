package com.ru.tgra;

public class GameState
{
    private static int shots;
    private static int score;

    public static void initGameState()
    {
        shots = 1;
        score = 0;
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
