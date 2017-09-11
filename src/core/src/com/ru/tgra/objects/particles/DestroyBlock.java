package com.ru.tgra.objects.particles;

import com.ru.tgra.Settings;
import com.ru.tgra.objects.GameObject;
import com.ru.tgra.utilities.Point2D;

public class DestroyBlock extends GameObject
{
    private GameObject[] particleBlocks;
    private float lifetime;

    public DestroyBlock(Point2D position)
    {
        super();

        particleBlocks = new GameObject[Settings.DestroyBlockNumberOfParticles];

        for(int i = 0; i < Settings.DestroyBlockNumberOfParticles; i++)
        {
            particleBlocks[i] = new SquareParticle(new Point2D(position));
        }
    }

    @Override
    public void draw()
    {
        if (particleBlocks != null)
        {
            for(int i = 0; i < Settings.DestroyBlockNumberOfParticles; i++)
            {
                particleBlocks[i].draw();
            }
        }
    }

    public void update(float deltaTime)
    {
        lifetime += deltaTime;

        if (lifetime < Settings.DestroyBlockLifespan)
        {
            for(int i = 0; i < Settings.DestroyBlockNumberOfParticles; i++)
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
