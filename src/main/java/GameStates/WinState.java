package GameStates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class WinState extends JPanel implements GameState, ActionListener{

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private GameStateManager stateManager = new GameStateManager();
    JFrame frame = new JFrame("WIN");
    private JButton restartButton;
    private JButton exitButton;

    @Override
    public void init() {
        // Create the start button
        restartButton = new JButton("Restart Game");
        restartButton.setBounds(100, 100, 100, 50); // x, y, width, height
        restartButton.setFont(new Font("Arial", Font.BOLD, 20)); // font name, style, size
        restartButton.setBackground(Color.GREEN);
        restartButton.setForeground(Color.WHITE);
        restartButton.addActionListener(this);
        add(restartButton);

        // Create the exit button
        exitButton = new JButton("Exit Game");
        exitButton.addActionListener(this);
        exitButton.setBounds(250, 100, 100, 50); // x, y, width, height
        exitButton.setFont(new Font("Arial", Font.BOLD, 20)); // font name, style, size
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        add(exitButton);

        // Initialize the main menu state.
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        stateManager.setCurrentState(new WinState());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setResizable(false);
        frame.add(this);
        frame.setVisible(true);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }

    private GameStateManager getStateManager() {
        return stateManager;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restartButton) {
            if (stateManager.getCurrentState() != null) {
                stateManager.setCurrentState(new RunningState());
                System.out.println("Restarting game");
                frame.dispose();
            }
            getStateManager().setState(new RunningState());
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }

    }
}
