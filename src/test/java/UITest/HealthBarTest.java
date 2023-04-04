package UITest;

import HealthBar.HealthBar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
class HealthBarTest {
    private HealthBar healthBar;

    @BeforeEach
    void setUp() {
        healthBar = new HealthBar();
    }

    @Test
    void getImage() {
        File heartFullImage = new File("assets/objects/heart_full.png");
        File heartBlankImage = new File("assets/objects/heart_blank.png");

        assertTrue(heartFullImage.exists());
        assertTrue(heartBlankImage.exists());
    }

    @Test
    void decreaseHealth() {
        assertEquals(3, healthBar.getHealth(), "HealthBar initial value should be 3!");

        healthBar.decreaseHealth(1);
        assertEquals(2, healthBar.getHealth(), "HealthBar should be 2 after decrease by 1!");
    }

    @Test
    void isDead() {
        assertEquals(3, healthBar.getHealth(), "HealthBar initial value should be 3!");

        healthBar.decreaseHealth(1);
        assertFalse(healthBar.isDead(), "Character should not be dead when it's health is larger than 0!");

        healthBar.decreaseHealth(4);
        assertTrue(healthBar.isDead(), "Character should be dead when it's health bar is smaller than 0!");
    }

    @Test
    void setHealth() {
        healthBar.setHealth(4);
        assertEquals(4, healthBar.getHealth(), "HealthBar health setting failed!");

        healthBar.setHealth(-5);
        assertEquals(-5, healthBar.getHealth(), "HealthBar health setting failed!");
    }

    @Test
    void getHealthIcons() throws IOException {
        BufferedImage[] healthImages = new BufferedImage[2];
        healthImages[0] = ImageIO.read(new File("assets/objects/heart_full.png"));
        healthImages[1] = ImageIO.read(new File("assets/objects/heart_blank.png"));

        assertEquals(healthImages[0].getSources(), healthBar.getHealthIcons()[0].getSources());
        assertEquals(healthImages[1].getSources(), healthBar.getHealthIcons()[1].getSources());
    }
}