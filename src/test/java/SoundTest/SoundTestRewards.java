package SoundTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.junit.jupiter.api.Test;

public class SoundTestRewards {
    @Test
    public void testOpenChestSound() {
        try {
            AudioInputStream openChest = AudioSystem.getAudioInputStream(new File("assets/audio/openchest.wav"));
            Clip openChestClip = AudioSystem.getClip();
            openChestClip.open(openChest);

            openChestClip.start();

            Thread.sleep(openChestClip.getMicrosecondLength() / 1000);

            openChestClip.stop();

            assertTrue(true);
        } catch (Exception e) {
            fail("Error playing music: " + e.getMessage());
        }
    }

    @Test
    public void testCoinSound() {
        try {
            AudioInputStream coinSound = AudioSystem.getAudioInputStream(new File("assets/audio/coinsound.wav"));
            Clip coinSoundClip = AudioSystem.getClip();
            coinSoundClip.open(coinSound);

            coinSoundClip.start();

            Thread.sleep(coinSoundClip.getMicrosecondLength() / 1000);

            coinSoundClip.stop();

            assertTrue(true);
        } catch (Exception e) {
            fail("Error playing music: " + e.getMessage());
        }
    }

    @Test
    public void testCoinSpawnSound() {
        try {
            AudioInputStream coinSpawn = AudioSystem.getAudioInputStream(new File("assets/audio/coinspawn.wav"));
            Clip coinSpawnClip = AudioSystem.getClip();
            coinSpawnClip.open(coinSpawn);

            coinSpawnClip.start();

            Thread.sleep(coinSpawnClip.getMicrosecondLength() / 1000);

            coinSpawnClip.stop();

            assertTrue(true);
        } catch (Exception e) {
            fail("Error playing music: " + e.getMessage());
        }
    }
}
