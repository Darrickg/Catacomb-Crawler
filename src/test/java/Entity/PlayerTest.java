package Entity;

import Entity.Player;
import Entity.MovingEnemy;
import tile.TileManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {
    private Player player;
    //    private int[][] testBoard;
    private TileManager tileManager = new TileManager(null, 25, 19, 32);
    @BeforeEach
    public void setUp() {

        player = new Player(400, 300, 20,25,3, tileManager.getMapTileNum());
    }

    @Test
    public void testScore() {
        player.setScore(100);
        assertEquals(100, player.getScore());

        player.addScore(50);
        assertEquals(150, player.getScore());

        player.decreaseScore(30);
        assertEquals(120, player.getScore());
    }

    @Test
    public void testLives() {
        player.setLives(5);
        assertEquals(5, player.getLives());

        player.addLives(2);
        assertEquals(7, player.getLives());
    }

    @Test
    public void testMovement() {
        player.setX(50);
        player.setY(50);
        player.setVx(5);
        player.setVy(5);

        assertEquals(50, player.getX());
        assertEquals(50, player.getY());
        assertEquals(5, player.getVx());
        assertEquals(5, player.getVy());

        player.moveRight();
        assertEquals(2, player.getVx());
        assertEquals(5, player.getVy());

        player.moveLeft();
        assertEquals(-2, player.getVx());
        assertEquals(5, player.getVy());

        player.moveUp();
        assertEquals(-2, player.getVx());
        assertEquals(-2, player.getVy());

        player.moveDown();
        assertEquals(-2, player.getVx());
        assertEquals(2, player.getVy());

        player.stopX();
        assertEquals(0, player.getVx());
        assertEquals(2, player.getVy());

        player.stopY();
        assertEquals(0, player.getVx());
        assertEquals(0, player.getVy());
    }

    @Test
    public void testTakeDamage() {
        player.setLives(5);
        player.takeDamage(2);
        assertEquals(3, player.getLives());

        // Ensure the player can't take damage immediately after taking damage
        player.takeDamage(1);
        assertEquals(2, player.getLives());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Ensure the player can take damage after waiting the durationBeforeDamage
        player.takeDamage(1);
        assertEquals(1, player.getLives());
    }

    @Test
    public void testDimensions() {
        player.setWidth(64);
        player.setHeight(64);

        assertEquals(64, player.getWidth());
        assertEquals(64, player.getHeight());
    }
}