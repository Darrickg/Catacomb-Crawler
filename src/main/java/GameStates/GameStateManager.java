package GameStates;

public class GameStateManager {
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
}

