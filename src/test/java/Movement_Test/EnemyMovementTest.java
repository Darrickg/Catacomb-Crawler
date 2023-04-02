package Movement_Test;

import Entity.MovingEnemy;
import Entity.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tile.TileManager;

import static org.junit.jupiter.api.Assertions.*;


public class EnemyMovementTest {
    private Player player;
    private MovingEnemy movingEnemy = new MovingEnemy(600, 500, 30,14, 1, 10000);
    private TileManager tileManager = new TileManager(null, 25, 19, 32);
    @BeforeEach
    public void setUp() {

        player = new Player(400, 300, 20,25,3, tileManager.getMapTileNum());
    }

    @Test
    void testMoveTowardsPlayer() {
        // Set the player's position to the right of the enemy
        player.setPosition(99, 0);

        // Call the moveTowardsPlayer method with the player object
        movingEnemy.moveTowardsPlayer(player);

        // Assert that the enemy has moved to the right
        //assertEquals(5, movingEnemy.getX());
        assertEquals(500, movingEnemy.getY());

        // Set the player's position to below the enemy
        player.setPosition(0, 100);

        // Call the moveTowardsPlayer method with the player object
        movingEnemy.moveTowardsPlayer(player);

        // Assert that the enemy has moved down
        assertEquals(600, movingEnemy.getX());
        assertEquals(500, movingEnemy.getY());

    }
}