package GameStates;

import javax.swing.*;
import java.awt.*;

public class GameStateManager extends JPanel {
    private GameState currentState;

    public void setState(GameState state) {
        if (currentState != null) {
            currentState.close();
            remove((Component) currentState);
        }

        currentState = state;
        add((Component) currentState);
        currentState.init();

    }

    public void update() {
        currentState.update();
    }

    public void render() {
        currentState.render();
    }
}

