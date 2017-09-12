package com.ru.tgra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class AudioManager
{
    private static Sound blockHitSound;

    public static void initSoundManager()
    {
        blockHitSound = Gdx.audio.newSound(Gdx.files.internal("audio/boop.wav"));
    }

    public static void playBlockHit()
    {
        blockHitSound.play();
    }
}
