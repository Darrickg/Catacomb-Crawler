package SoundTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.junit.jupiter.api.Test;

public class SoundTestEnemy {

    @Test
    public void testDamageSound() {
        try {
            AudioInputStream damage = AudioSystem.getAudioInputStream(new File("assets/audio/damage.wav"));
            Clip damageClip = AudioSystem.getClip();
            damageClip.open(damage);

            damageClip.start();

            Thread.sleep(damageClip.getMicrosecondLength() / 1000);

            damageClip.stop();

            assertTrue(true);
        } catch (Exception e) {
            fail("Error playing music: " + e.getMessage());
        }
    }
}
