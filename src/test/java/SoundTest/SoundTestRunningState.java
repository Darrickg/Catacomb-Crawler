package SoundTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.junit.jupiter.api.Test;

public class SoundTestRunningState {
    
    @Test
    public void testRunningStateMusic() {
        try {
            AudioInputStream gameMusic = AudioSystem.getAudioInputStream(new File("assets/audio/gamemusic.wav"));
            Clip gameMusicClip = AudioSystem.getClip();
            gameMusicClip.open(gameMusic);

            gameMusicClip.start();

            Thread.sleep(3000);

            gameMusicClip.stop();

            assertTrue(true);
        } catch (Exception e) {
            fail("Error playing music: " + e.getMessage());
        }
    }

    @Test
    public void testDoorOpenSound() {
        try {
            AudioInputStream doorOpen = AudioSystem.getAudioInputStream(new File("assets/audio/dooropen.wav"));
            Clip doorOpenClip = AudioSystem.getClip();
            doorOpenClip.open(doorOpen);

            doorOpenClip.start();

            Thread.sleep(3000);

            doorOpenClip.stop();

            assertTrue(true);
        } catch (Exception e) {
            fail("Error playing music: " + e.getMessage());
        }
    }

    @Test
    public void testWinMusic() {
        try {
            AudioInputStream winMusic = AudioSystem.getAudioInputStream(new File("assets/audio/gamewin.wav"));
            Clip winMusicClip = AudioSystem.getClip();
            winMusicClip.open(winMusic);

            winMusicClip.start();

            Thread.sleep(3000);

            winMusicClip.stop();

            assertTrue(true);
        } catch (Exception e) {
            fail("Error playing music: " + e.getMessage());
        }
    }

    @Test
    public void testGameOverSound() {
        try {
            AudioInputStream gameOver = AudioSystem.getAudioInputStream(new File("assets/audio/gameover.wav"));
            Clip gameOverClip = AudioSystem.getClip();
            gameOverClip.open(gameOver);

            gameOverClip.start();

            Thread.sleep(3000);

            gameOverClip.stop();

            assertTrue(true);
        } catch (Exception e) {
            fail("Error playing music: " + e.getMessage());
        }
    }

    @Test
    public void testMenuScreenMusic() {
        try {
            AudioInputStream startMusic = AudioSystem.getAudioInputStream(new File("assets/audio/startmusic.wav"));
            Clip startMusicClip = AudioSystem.getClip();
            startMusicClip.open(startMusic);

            startMusicClip.start();

            Thread.sleep(3000);

            startMusicClip.stop();

            assertTrue(true);
        } catch (Exception e) {
            fail("Error playing music: " + e.getMessage());
        }
    }
}
