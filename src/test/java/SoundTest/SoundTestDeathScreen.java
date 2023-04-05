package SoundTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.junit.jupiter.api.Test;

public class SoundTestDeathScreen {
    
    @Test
    public void testDeathScreenMusic() {
        try {
            AudioInputStream endMusic = AudioSystem.getAudioInputStream(new File("assets/audio/gamelose.wav"));
            Clip endMusicClip = AudioSystem.getClip();
            endMusicClip.open(endMusic);

            endMusicClip.start();

            Thread.sleep(endMusicClip.getMicrosecondLength() / 1000);

            endMusicClip.stop();

            assertTrue(true);
        } catch (Exception e) {
            fail("Error playing music: " + e.getMessage());
        }
    }

    @Test
    public void testDeathScreenButtonSound() {
        try {
            AudioInputStream buttonSound = AudioSystem.getAudioInputStream(new File("assets/audio/select.wav"));
            Clip buttonSoundClip = AudioSystem.getClip();
            buttonSoundClip.open(buttonSound);

            buttonSoundClip.start();

            Thread.sleep(buttonSoundClip.getMicrosecondLength() / 1000);

            buttonSoundClip.stop();

            assertTrue(true);
        } catch (Exception e) {
            fail("Error playing music: " + e.getMessage());
        }
    }
}

