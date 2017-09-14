package com.ru.tgra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager
{
    private static Sound blockHitSound;
    private static Sound blockDestroySound;
    private static Sound ballUpSound;
    private static Sound wallHitSound;
    private static Music music;

    public static void initSoundManager()
    {
        blockHitSound = Gdx.audio.newSound(Gdx.files.internal("audio/boop.wav"));
        blockDestroySound = Gdx.audio.newSound(Gdx.files.internal("audio/blockDestroy.mp3"));
        ballUpSound = Gdx.audio.newSound(Gdx.files.internal("audio/ballup.wav"));
        wallHitSound = Gdx.audio.newSound(Gdx.files.internal("audio/tick.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("audio/NoCopyrightSounds-Music.mp3"));
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

    public static void playWallHit()
    {
        wallHitSound.play(0.25f);
    }

    public static void playMusic()
    {
        music.setLooping(true);
        music.play();
    }

    public static void pauseResumeMusic()
    {
        if (music.isPlaying())
        {
            music.pause();
        }
        else
        {
            music.play();
        }
    }
}
