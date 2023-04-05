package GameStates;
import static org.junit.Assert.*;



import GameStates.MainMenuState;
import GameStates.RunningState;
import GameStates.WinState;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class WinStateTest {
    private static WinState winState;

    @Before
    public void setUp() {
        winState = new WinState();
        winState.init();
        winState.render();
        winState.update();
    }

    @Test
    public void testRestartButtonNotNull() {
        assertNotNull(winState.restartButton);
    }

    @Test
    public void testExitButtonNotNull() {
        assertNotNull(winState.exitButton);
    }

    @Test
    public void testRestartButtonAction() {
        winState.restartButton.doClick();
        assertEquals(RunningState.class, winState.getStateManager().getCurrentState().getClass());
    }


    @Test
    public void testEndMusicClipStops() {
        winState.restartButton.doClick();
        assertFalse(winState.endMusicClip.isActive());
        winState.frame.dispose();
    }

    @After
    public void tearDown() {
        winState.frame.dispose();
    }
}
