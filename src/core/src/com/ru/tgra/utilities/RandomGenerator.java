package com.ru.tgra.utilities;

import java.util.Random;

public class RandomGenerator
{
    private static Random rand = new Random();
    private static float[] corners = new float[] {0, 90, 180, 270};

    public static float randomNumberInRange(float min, float max)
    {
        return rand.nextFloat() * (max - min) + min;
    }

    public static boolean nextBool()
    {
        return rand.nextBoolean();
    }

    public static float randomCorner()
    {
        return corners[rand.nextInt(4)];
    }
}
