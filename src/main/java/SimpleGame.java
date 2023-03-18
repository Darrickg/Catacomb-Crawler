
import GameStates.GameStateManager;
import GameStates.MainMenuState;
import java.io.IOException;
import javax.swing.JPanel;

/**
 * simple game main class create game
 */
public class SimpleGame extends JPanel {
    private GameStateManager stateManager;

    /**
     * simple game constructor create a game thread
     */
    public SimpleGame() {
        stateManager = new GameStateManager();
        stateManager.setState(new MainMenuState());
    }

    /**
     * main class
     * @param args null
     * @throws IOException null
     */
    public static void main(String[] args) throws IOException {
       new SimpleGame();
    }
}