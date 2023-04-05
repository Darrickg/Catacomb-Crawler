package Entity;

import HealthBar.HealthBar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tile.TileManager;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MovingEnemyTest {
    private Player player;
    private MovingEnemy enemy = new MovingEnemy(600, 500, 30,14, 1, 10000);
    private TileManager tileManager = new TileManager(null, 5, 5, 16);

    private HealthBar healthBar;

    @BeforeEach
    public void setUp() {
        player = new Player(400, 300, 20,25,3, tileManager.getMapTileNum());
    }
    @Test
    void testMoveTowardsPlayer() {
        // Set the player's position to the right of the enemy
        player.setPosition(99, 0);

        // Call the moveTowardsPlayer method with the player object
        enemy.moveTowardsPlayer(player);

        // Assert that the enemy has moved to the right
        //assertEquals(5, movingEnemy.getX());
        assertEquals(500, enemy.getY());

        // Set the player's position to below the enemy
        player.setPosition(0, 100);

        // Call the moveTowardsPlayer method with the player object
        enemy.moveTowardsPlayer(player);

        // Assert that the enemy has moved down
        assertEquals(600, enemy.getX());
        assertEquals(500, enemy.getY());

    }

    @Test
    public void testSearchPath() {
        int[][] map = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        tileManager.setMapTileNum(map);
        enemy.searchPath(2, 2);
        assertEquals(600, enemy.getX(), 0);
        assertEquals(499, enemy.getY(), 0);
    }

    @Test
    public void testMovingEnemy() {
        MovingEnemy enemy = new MovingEnemy(10, 20, 30, 40, 5, 15);
        assertEquals(10, enemy.getX(), 0);
        assertEquals(20, enemy.getY(), 0);
        assertEquals(30, enemy.getWidth(), 0);
        assertEquals(40, enemy.getHeight(), 0);
        assertEquals(15, enemy.damage, 0);
    }

    @Test
    public void testEnemy() {
        enemy.setX(100);
        enemy.setY(100);
        assertEquals(100, enemy.getX(), 0);
        assertEquals(100, enemy.getY(), 0);
        assertEquals(30, enemy.getWidth(), 0);
        assertEquals(14, enemy.getHeight(), 0);
        assertEquals(10000, enemy.getDamage(), 0);
        Rectangle HB = new Rectangle(10,10);
        enemy.setHitbox(HB);
        assertEquals(HB, enemy.getHitbox());

        player.setHitbox(HB);
        healthBar = new HealthBar();
        assertTrue(enemy.handleCollision(player,healthBar));
    }
}