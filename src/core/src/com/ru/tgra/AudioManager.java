package com.ru.tgra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class AudioManager
{
    private static Sound blockHitSound;
    private static Sound blockDestroySound;
    private static Sound ballUpSound;

    public static void initSoundManager()
    {
        blockHitSound = Gdx.audio.newSound(Gdx.files.internal("audio/boop.wav"));
        blockDestroySound = Gdx.audio.newSound(Gdx.files.internal("audio/blockDestroy.mp3"));
        ballUpSound = Gdx.audio.newSound(Gdx.files.internal("audio/ballup.wav"));
    }

    public static void playBlockHit()
    {
        blockHitSound.play();
    }

    public static void playBlockDestroy()
    {
        blockDestroySound.play();
    }

    public static void playBallUp()
    {
        ballUpSound.play();
    }
}
