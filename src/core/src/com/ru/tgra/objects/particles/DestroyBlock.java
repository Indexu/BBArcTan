package com.ru.tgra.objects.particles;

import com.ru.tgra.objects.GameObject;
import com.ru.tgra.utilities.Point2D;

public class DestroyBlock extends GameObject
{
    private final int numberOfBlocks = 10;
    private final float lifespan = 3;

    private GameObject[] particleBlocks;
    private float lifetime;

    public DestroyBlock(Point2D position)
    {
        super();

        this.position = position;

        particleBlocks = new GameObject[numberOfBlocks];

        for(int i = 0; i < numberOfBlocks; i++)
        {
            particleBlocks[i] = new SquareParticle(position);
        }
    }

    @Override
    public void draw()
    {
        super.draw();

        for(int i = 0; i < numberOfBlocks; i++)
        {
            particleBlocks[i].draw();
        }
    }

    public void update(float deltaTime)
    {
        lifetime += deltaTime;

        if (lifetime < lifespan)
        {
            for(int i = 0; i < numberOfBlocks; i++)
            {
                particleBlocks[i].update(deltaTime);
            }
        }
        else
        {
            // TODO
        }
    }
}
