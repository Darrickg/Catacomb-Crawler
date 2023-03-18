package GameStates;

import javax.swing.*;

/**
 * game state manager with all fields as parameters create manager of game state
 */
public class GameStateManager extends JPanel {
    private GameState currentState;

    /**
     * state setter
     * @param state set state
     */
    public void setState(GameState state) {
        currentState = state;
        currentState.init();

    }

    /**
     * game state updater
     */
    public void update() {
        currentState.update();
    }

    /**
     * game state render
     */
    public void render() {
        currentState.render();
    }

    /**
     * game state getter
     * @return current state
     */
    public GameState getCurrentState() {
        return currentState;
    }

    /**
     * game state setter
     * @param currentState state = current state
     */
    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }
}

