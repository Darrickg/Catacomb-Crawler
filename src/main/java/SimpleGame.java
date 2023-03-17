
import GameStates.GameStateManager;
import GameStates.MainMenuState;
import java.io.IOException;
import javax.swing.JPanel;

public class SimpleGame extends JPanel {
    private GameStateManager stateManager;

    public SimpleGame() {

        stateManager = new GameStateManager();
        stateManager.setState(new MainMenuState());
    }


    public static void main(String[] args) throws IOException {
       new SimpleGame();
    }
}