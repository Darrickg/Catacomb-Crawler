package UITest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import Entity.Player;
import Rewards.bonus;
import Rewards.regular;
import Rewards.rewards;
import tile.TileManager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Entity.Player;
import tile.TileManager;

public class bonusTest {
    public bonus bonus;

    @Before
    public void setUp() {
        TileManager tileManager = new TileManager(null, 5,5,16);
        tileManager.loadMap("src/test/assets/test_map.txt");
        bonus = new bonus(0, 0, 16, 16, 100, 5000, 10000, tileManager);
    }

    @Test
    public void testUpdate() {
        bonus.update();
        assertFalse(bonus.isRespawning());
    }

    @Test
    public void testStartRespawn() {
        bonus.startRespawn();
        assertTrue(bonus.isRespawning());
        assertEquals(10000, bonus.getTimeLeftToRespawn());
    }

    @Test
    public void testRespawn() {
        bonus.startRespawn();
        bonus.respawn();
        assertFalse(bonus.isRespawning());
        assertEquals(10000, bonus.getTimeLeftToRespawn());
        assertNotNull(bonus.getCurrentTile());
        assertNotNull(bonus.getHitbox());
    }

    @Test
    public void testFindRandomValidTile() {
        int[] position = bonus.findRandomValidTile();
        assertEquals(2, position.length);
        assertTrue(position[0] >= 0 && position[0] <= 58);
        assertTrue(position[1] >= 0 && position[1] <= 31);
    }


    @Test
    public void testPickUp() {
        assertFalse(bonus.isPickedUp());
        bonus.pickUp();
        assertTrue(bonus.isPickedUp());
    }

    @Test
    public void testSetCurrentFrame() {
        bonus.setCurrentFrame(1);
        assertEquals(1, bonus.getCurrentFrame());
    }

    @Test
    public void testSetPickedUp() {
        bonus.setPickedUp(true);
        assertTrue(bonus.isPickedUp());
    }

    @Test
    public void testIntegration() {
        bonus.startRespawn();
        while (bonus.isRespawning()) {
            bonus.update();
        }
        assertFalse(bonus.isRespawning());
        bonus.handleCollision(new Player(0, 0, 16, 16,1,null));
    }
}
