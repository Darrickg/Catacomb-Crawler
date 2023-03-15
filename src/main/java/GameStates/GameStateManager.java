package GameStates;

import javax.swing.*;
import java.awt.*;

public class GameStateManager extends JPanel {
    private GameState currentState;

    public void setState(GameState state) {
        currentState = state;
        currentState.init();

    }

    public void update() {
        currentState.update();
    }

    public void render() {
        currentState.render();
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }
}

