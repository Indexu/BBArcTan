package com.ru.tgra.utilities;

import java.util.Random;

public class RandomGenerator
{
    private static Random rand = new Random();

    public static float randomNumberInRange(float min, float max)
    {
        return rand.nextFloat() * (max - min) + min;
    }
}
