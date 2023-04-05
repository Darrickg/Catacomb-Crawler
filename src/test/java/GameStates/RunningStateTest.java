package GameStates;

import HealthBar.HealthBar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RunningStateTest {
    private RunningState runState;
    @BeforeEach
    void setUp() {
        runState = new RunningState();
    }
    @Test
    void init() {
        runState.init();
        assertFalse(runState.isDoorOpen(), "When the game is initialized the door should be closed!");

        int[] expectedPlayerInitLoc = new int[2];
        expectedPlayerInitLoc[0] = 384;
        expectedPlayerInitLoc[1] = 320;
        assertArrayEquals(expectedPlayerInitLoc, runState.getPlayerLocation(),"Player should always start at the brith location [384, 320]!");

        assertEquals(3, runState.getHealthbarValue(), "Init value for healthbar should always be 3!");
    }

    @Test
    void update() {
        runState.init();
        runState.setNumRegularRewards(0);
        runState.update();
        assertTrue(runState.isDoorOpen(), "The door should open after user collects all the rewards!");

    }
}