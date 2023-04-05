package SoundTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.junit.jupiter.api.Test;

public class SoundTestMainMenu {
    
    @Test
    public void testMenuScreenMusic() {
        try {
            AudioInputStream startMusic = AudioSystem.getAudioInputStream(new File("assets/audio/startmusic.wav"));
            Clip startMusicClip = AudioSystem.getClip();
            startMusicClip.open(startMusic);

            startMusicClip.start();

            Thread.sleep(startMusicClip.getMicrosecondLength() / 1000);

            startMusicClip.stop();

            assertTrue(true);
        } catch (Exception e) {
            fail("Error playing music: " + e.getMessage());
        }
    }

    @Test
    public void testMenuButtonSound() {
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
