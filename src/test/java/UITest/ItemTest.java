package UITest;

import Entity.Player;
import Rewards.bonus;
import Rewards.regular;
import Rewards.rewards;
import tile.TileManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    private Player player;
    private rewards regular, bonus;
    private final TileManager tileManager = new TileManager(null, 25, 19, 32);

    @BeforeEach
    public void setUp(){
        // init player, regular reward and bonus reward
        player = new Player(400, 300, 20,25,3, tileManager.getMapTileNum());
        regular = new regular(736,544,18,15,75);
        bonus = new bonus(320,352,32,32,200,100,500,tileManager);
    }

    @Test
    public void testItemValues(){
        // testing the values of rewards
        assertEquals(75, regular.getValue());
        assertEquals(200, bonus.getValue());
    }

    @Test
    public void testItemPickUp(){
        // testing the rewards being initialized as not picked up
        assertFalse(regular.isPickedUp());
        assertFalse(bonus.isPickedUp());
        // testing the rewards being pickedup
        regular.pickUp();
        assertTrue(regular.isPickedUp());
        bonus.pickUp();
        assertTrue(bonus.isPickedUp());
    }

    @Test
    public void TestItemRemoved(){
        // testing the removal of rewards
        assertEquals(18,regular.getWidth());
        assertEquals(15,regular.getHeight());

        assertEquals(32,bonus.getWidth());
        assertEquals(32,bonus.getHeight());

        regular.remove();
        assertEquals(0, regular.getHeight());
        assertEquals(0, regular.getWidth());

        bonus.remove();
        assertEquals(0, bonus.getHeight());
        assertEquals(0, bonus.getWidth());
    }

    @Test // integration test with player
    public void TestPlayerCollision(){

        Rectangle hitbox = new Rectangle(100,100,100,100);
        player.setHitbox(hitbox);
        regular.setHitbox(hitbox);
        bonus.setHitbox(hitbox);

        regular.handleCollision(player);
        assertEquals(0, regular.getHeight());
        assertEquals(0, regular.getWidth());

        bonus.handleCollision(player);
        assertEquals(0, bonus.getHeight());
        assertEquals(0, bonus.getWidth());

    }

}
