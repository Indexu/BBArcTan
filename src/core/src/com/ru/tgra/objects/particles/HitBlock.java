package com.ru.tgra.objects.particles;

import com.ru.tgra.Settings;
import com.ru.tgra.objects.GameObject;
import com.ru.tgra.utilities.Color;
import com.ru.tgra.utilities.Point2D;

public class HitBlock extends GameObject
{
    private GameObject[] particleBlocks;
    private float lifetime;

    public HitBlock(Point2D position, Color color)
    {
        super();

        particleBlocks = new GameObject[Settings.HitBlockNumberOfParticles];

        for(int i = 0; i < Settings.HitBlockNumberOfParticles; i++)
        {
            particleBlocks[i] = new SquareParticle(new Point2D(position), Settings.HitBlockLifespan, color, true, false);
        }
    }

    @Override
    public void draw()
    {
        if (particleBlocks != null)
        {
            for(int i = 0; i < Settings.HitBlockNumberOfParticles; i++)
            {
                particleBlocks[i].draw();
            }
        }
    }

    public void update(float deltaTime)
    {
        lifetime += deltaTime;

        if (lifetime < Settings.HitBlockLifespan)
        {
            for(int i = 0; i < Settings.HitBlockNumberOfParticles; i++)
            {
                particleBlocks[i].update(deltaTime);
            }
        }
        else
        {
            particleBlocks = null;
            this.destroy();
        }
    }
}
