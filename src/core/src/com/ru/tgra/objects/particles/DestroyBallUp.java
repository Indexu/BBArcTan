package com.ru.tgra.objects.particles;

import com.ru.tgra.Settings;
import com.ru.tgra.objects.GameObject;
import com.ru.tgra.utilities.Point2D;

public class DestroyBallUp extends GameObject
{
    private GameObject[] particleBlocks;
    private float lifetime;

    public DestroyBallUp(Point2D position)
    {
        super();

        particleBlocks = new GameObject[Settings.DestroyBallUpNumberOfParticles];

        for(int i = 0; i < Settings.DestroyBallUpNumberOfParticles; i++)
        {
            particleBlocks[i] = new SquareParticle(new Point2D(position), Settings.DestroyBallUpLifespan, Settings.BallUpColor, false, true);
        }
    }

    @Override
    public void draw()
    {
        if (particleBlocks != null)
        {
            for(int i = 0; i < Settings.DestroyBallUpNumberOfParticles; i++)
            {
                particleBlocks[i].draw();
            }
        }
    }

    public void update(float deltaTime)
    {
        lifetime += deltaTime;

        if (lifetime < Settings.DestroyBallUpLifespan)
        {
            for(int i = 0; i < Settings.DestroyBallUpNumberOfParticles; i++)
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
