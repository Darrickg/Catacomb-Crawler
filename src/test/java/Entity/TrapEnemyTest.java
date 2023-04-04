package Entity;
import Entity.Player;
import  GameStates.RunningState;

import Entity.TrapEnemy;
import HealthBar.HealthBar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tile.TileManager;


import javax.swing.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class TrapEnemyTest {
    TrapEnemy trap;

    @BeforeEach
    void setUp() {
        trap = new TrapEnemy(160,40);
    }

    @Test
    void activate() {
        assertFalse(trap.isActivated(), "All the traps are default to be inactive!");

        trap.activate();
        assertTrue(trap.isActivated(), "Failed setting traps[0] to be active!");
    }

    @Test
    void handleCollision() {
        TileManager tileManager = new TileManager(null, 25, 19, 32);
        Player player = new Player(384, 320, 32,32,3, tileManager.getMapTileNum());
        HealthBar healthBar = new HealthBar();
        assertFalse(trap.handleCollision(player, healthBar), "The player are not supposed to run into the trap here!");

        TrapEnemy trap_collided = new TrapEnemy(384, 320);
        assertTrue(trap_collided.handleCollision(player, healthBar), "The player and the trap are on the same coordination, they are suppose to collide!");
    }
}