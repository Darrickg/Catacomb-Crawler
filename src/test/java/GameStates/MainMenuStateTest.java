package GameStates;
import org.junit.Test;
import static org.junit.Assert.*;
public class MainMenuStateTest {

    @Test
    public void testInit() {
        MainMenuState mainMenuState = new MainMenuState();
        mainMenuState.init();
        assertEquals("Main Menu", mainMenuState.frame.getTitle());
        assertEquals(1, mainMenuState.frame.getContentPane().getComponents().length);
        assertEquals("Start Game", mainMenuState.startButton.getText());
        assertEquals("Exit Game", mainMenuState.exitButton.getText());
    }

    @Test
    public void testIntegration() {
        GameStateManager gameStateManager = new GameStateManager();
        MainMenuState mainMenuState = new MainMenuState();
        mainMenuState.init();
        gameStateManager.setCurrentState(mainMenuState);

        // Verify that the current state of the game state manager is the main menu state
        assertEquals(mainMenuState, gameStateManager.getCurrentState());

        // Call update() and render() methods and verify that they don't throw any exceptions
        mainMenuState.update();
        mainMenuState.render();

        // Simulate user clicking the start button
        mainMenuState.startButton.doClick();

        // Verify that the state manager opens a new running state
        assertEquals(MainMenuState.class, gameStateManager.getCurrentState().getClass());
    }
}
